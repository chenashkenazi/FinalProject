package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalproject.resourses.Levels;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

/*Here we're going to create the fragment for each level.
 each level will contains:
   1. TextView - for the title of the level (level 1, level 2, exc...) based on an array
   2. GridLayout - for the display of levels. each cell will contain a levels_cell
      each cell will contain:
          a. The button that will link to the level
          b. The stars according to the array of Level object:
                  * 0 - The level not yet passed
                  * 1 - The level passed with a score between 0-99
                  * 2 - The level passed with a score between 100-199
                  * 3 - The level passed with a score 200 or above it
   3. An array of 16 levels (based on the Level object we created).
      we'll put each level cell inside the right place in the GridLayout.
   4. Button of "Play" - will link directly to the game activity of the top of the copied list of level
      (same as note #5)

  Notes:
  1. All the levels will start in status "not complete"
  2. All the "not complete" levels will be block to press
  3. The first level in the list (the top of the list of levels) won't be blocked but will be with 0 stars
  4. We will create a copy of the list of level for dynamic changes in game
  5. We will create an instance for the top of the copied list of levels's game activity
     (instance for result of score)
  6. When level is complete (returns from the game activity instance):
        * the number of stars changes according to score we received
        * the status of the level will change to "complete"
        * afterwords we pop the level from the copied list of levels
        * then change the top level on the copied list of levels so it will be able to pressed on
  7. We keep the option to press a level after finished it

  */

public class LevelsFragment extends Fragment {

    private TextView levelNumTv;
    private GridView gridView;
    private Level level;

    //Context context;
    //Communicator communicator;

    private final static int SUB_LEVEL_SIZE = 16;


    public static LevelsFragment getInstance(Level level){
        LevelsFragment fragment = new LevelsFragment();
        if(level != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("level", level);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PreferenceManager.getDefaultSharedPreferences(context);


        if (getArguments() != null){
            level = getArguments().getParcelable("level");

            /*SubLevel[] subLevel = new SubLevel[SUB_LEVEL_SIZE];
            for (int j = 0; j < SUB_LEVEL_SIZE; j++) {
                subLevel[j] = new SubLevel(j);
            }
            level.setSubLevels(subLevel);*/
        }

        //PreferenceFragment.
    }

    /*@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (C)
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.levels_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        levelNumTv = view.findViewById(R.id.level_title);
        gridView = (GridView)view.findViewById(R.id.levels_grid_view);

        //getting measurements to spacing the gridView
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gridView.setVerticalSpacing((displayMetrics.heightPixels)/40);//???????

        init();
    }

    private void init() {
        if(level != null){
            levelNumTv.setText(level.getTitle());

            CustomAdapter customAdapter = new CustomAdapter();
            gridView.setAdapter(customAdapter);

            gridView.setGravity(View.TEXT_ALIGNMENT_CENTER);

            if (level.isOpen() && !level.getSubLevels()[0].isComplete())
                level.getSubLevels()[0].setStatus(Status.NEXT);

        }
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return SUB_LEVEL_SIZE;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View grid = getLayoutInflater().inflate(R.layout.levels_cell,null);
            //String level_number = String.valueOf(i+1);

            Button levelBtn = (Button) grid.findViewById(R.id.level_btn);
            ImageView star1Iv = (ImageView)grid.findViewById(R.id.first_star);
            ImageView star2Iv = (ImageView)grid.findViewById(R.id.second_star);
            ImageView star3Iv = (ImageView)grid.findViewById(R.id.third_star);

            levelBtn.setText(String.valueOf(i+1));

            //set the grid's display
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            levelBtn.setWidth((displayMetrics.widthPixels)/10);
            levelBtn.setHeight((displayMetrics.heightPixels)/10);


            //setting stars
            switch (level.getSubLevels()[i].getStars()){
                case 1:
                    star1Iv.setActivated(true);
                    break;
                case 2:
                    star1Iv.setActivated(true);
                    star2Iv.setActivated(true);
                    break;
                case 3:
                    star1Iv.setActivated(true);
                    star2Iv.setActivated(true);
                    star3Iv.setActivated(true);
            }

            //setting buttons according to their status
            setButtonStatus(i, levelBtn);


            levelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), com.example.finalproject.SimonLevel.class);
                    intent.putExtra("levelNumber",level.getColor());
                    intent.putExtra("subLevelNumber",level.getSubLevels()[i].getSubLevelNumber());
                    intent.putExtra("numberOfStars",level.getSubLevels()[i].getStars());
                    intent.putExtra("putArrayOfMoves",level.getSubLevels()[i].getArrayOfMoves());
                    intent.putExtra("highScore",level.getSubLevels()[i].getHighScore());
                    startActivityForResult(intent,1);
                }
            });

            return grid;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("level", level);
    }

    /*set button status*/
    private void setButtonStatus(int i, Button btn){
        //Log.i(TAG,"setButtonStatus()");

        switch (level.getSubLevels()[i].getStatus()){
            case LOCK:
                setLockedButton(btn);
                break;
            case NEXT:
                setNextButton(btn);
                break;
            case COMPLETE:
                setCompletedButton(btn);
                break;
        }
    }

    /*set next button*/
    private void setNextButton(Button btn){
        btn.setEnabled(true); //clickable
        btn.setActivated(true); //next
    }

    /*set completed button*/
    private void setCompletedButton(Button btn){
        btn.setActivated(false); //not next
        btn.setSelected(true); //completed
    }

    /*set locked button*/
    private void setLockedButton(Button btn){
        btn.setEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            int receiveStars = data.getIntExtra("starsSimon", 0);
            int receiveSubLevel = data.getIntExtra("subLevelSimon", 0);
            int receiveLevel = data.getIntExtra("levelSimon", 0);
            int[] receiveArrayOfMoves = data.getIntArrayExtra("getArrayOfMoves");
            int receiveHighScore = data.getIntExtra("highScoreSimon", 0);
            level.getSubLevels()[receiveSubLevel].setArrayOfMoves(receiveArrayOfMoves);


            if (requestCode == 1) {
                if (resultCode == -1) {
                    //receiving data from SimonLevel1

                    if (level.getColor() == receiveLevel) {
                        level.getSubLevels()[receiveSubLevel].setComplete(true);
                        level.getSubLevels()[receiveSubLevel].setStars(receiveStars);
                        level.getSubLevels()[receiveSubLevel].setHighScore(receiveHighScore);

                        if (level.getSubLevels()[receiveSubLevel].getStatus() == Status.NEXT) {
                            level.getSubLevels()[receiveSubLevel].setStatus(Status.COMPLETE);
                            if (receiveSubLevel < 15)
                                level.getSubLevels()[receiveSubLevel + 1].setStatus(Status.NEXT);
                            else { // =15
                                level.getSubLevels()[receiveSubLevel].setStatus(Status.COMPLETE);
                                ((LevelsActivity) Objects.requireNonNull(getActivity())).FinishLevel(getView());
                            }
                        }

                    }

                }
            }
        }
        init();
    }

}
