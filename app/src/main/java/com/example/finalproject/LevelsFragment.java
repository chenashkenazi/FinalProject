package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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

public class LevelsFragment extends Fragment{

    private TextView levelNumTv; //level's number (level1, level2, level3)
    private GridView gridView;
    private Level level;

    //private final SubLevel[] subLevels = new SubLevel[16];

    /*private int numOfLevelsCompleted = 0;
    private int numOfSubLevelsCompleted = 0;*/


    //creating a new fragment instance
    public static LevelsFragment getInstance(Level level){
        /*we'll taken the Level object that was passed through the constructor,
        we'll instantiate it a bundle object,
        we'l attached that Level object to the bundle,
        and we'll attached the bundle to the fragment so that when
        we'kk instantiate a new fragment - that new Level object will be attached to it
        and we'll get an access to it.*/

        LevelsFragment fragment = new LevelsFragment();

        if(level != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable("level", level); //level need to be Parcelable
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if we have something attached to the bundle
        if (getArguments() != null){
            //set our global Level object
            level = getArguments().getParcelable("level");
        }

        if (level.isOpen())
            level.getSubLevels()[0].setStatus(Status.NEXT);

        //creating new subLevels only at the beginning
        /*switch (numOfSubLevelsCompleted){
            case 0:
                for (int i=0 ; i<16 ; i++){
                    subLevels[i] = new SubLevel(i);
                }
                level.setSubLevels(subLevels);
                switch (level.getColor()){
                    case 4:
                        level.setTitle(getResources().getString(R.string.level_four_colors));
                        subLevels[0].setStatus(Status.NEXT);
                        break;
                    case 6:
                        level.setTitle(getResources().getString(R.string.level_six_colors));
                        break;
                    case 8:
                        level.setTitle(getResources().getString(R.string.level_eight_colors));
                        break;
                }
                break;
            case 1:
                if (level.getColor() == 6)
                    subLevels[0].setStatus(Status.NEXT);
            case 2:
                if (level.getColor() == 8)
                    subLevels[0].setStatus(Status.NEXT);
                break;

        }*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.levels_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        levelNumTv = view.findViewById(R.id.level_title);
        gridView = (GridView)view.findViewById(R.id.levels_grid_view);

        //getting measurements to spacing the gridView
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gridView.setVerticalSpacing((displayMetrics.heightPixels)/40);//???????

        init();
    }


    private void init() {
        //set the different properties to the widgets
        if(level != null){
            CustomAdapter customAdapter = new CustomAdapter(); //making each cell

            levelNumTv.setText(level.getTitle());

            gridView.setAdapter(customAdapter); //setting each cell in grid

            //setGridViewHeight(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //לחיצה על התא עצמו...
                    //Toast.makeText(getActivity(), "!!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return level.getSubLevels().length;
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
            String level_number = String.valueOf(i+1);

            Button levelBtn = (Button) grid.findViewById(R.id.level_btn);
            ImageView star1Iv = (ImageView)grid.findViewById(R.id.first_star);
            ImageView star2Iv = (ImageView)grid.findViewById(R.id.second_star);
            ImageView star3Iv = (ImageView)grid.findViewById(R.id.third_star);

            levelBtn.setText(level_number);

            //setting subLevel's levelNumber
            //subLevels[i].setLevelNumber(level.getColor());

            //set the grid's display
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            levelBtn.setWidth((displayMetrics.heightPixels)/9);
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
            //setButtonsInLevel(levelBtn,i);
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

    /*set button status*/
    private void setButtonStatus(int i, Button btn){
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

    /*setting the button-cell's drawable and un-clickable (from selector)*/
    /*private void setButtonsInLevel(Button levelBtn, int i){

        if (numOfLevelsCompleted == 1)
            Toast.makeText(getActivity(), numOfLevelsCompleted+"!!!", Toast.LENGTH_SHORT).show();

        switch (numOfLevelsCompleted){
            case 0:
                //only "4 COLORS" level is open
                if (level.getColor() == 4)
                    setButtonSelector(levelBtn,i,false);
                else
                    levelBtn.setEnabled(false); //not clickable
                break;
            case 1:
                //"4 COLORS" level is all blue, "6 COLORS" level is open
                if (level.getColor() == 4)
                    setButtonSelector(levelBtn,i,true);
                else if (level.getColor() == 6) {
                    Toast.makeText(getActivity(), "!!!", Toast.LENGTH_SHORT).show();
                    setButtonSelector(levelBtn, i, false);
                }
                else if (level.getColor() == 8)
                    levelBtn.setEnabled(false); //not clickable
                break;
            case 2:
                setButtonSelector(levelBtn,i,true);
                break;
        }


    }*/

    /*select drawable according to level*/
    /*private void setButtonSelector(Button levelBtn, int i, boolean completed){
        if (!completed) {
            if (i != 0)
                levelBtn.setEnabled(false); //not clickable
            if (i == 0 && !subLevels[i].isComplete()) {
                //אם נמצאים בשלב הראשון והוא לא הושלם - נהפוך אותו לירוק
                levelBtn.setEnabled(true); //clickable
                levelBtn.setActivated(true); //next
            } else if (i > 0 && i < 15 && subLevels[i - 1].isComplete() && !subLevels[i + 1].isComplete()) {
                //אם נמצאים בין השלבים האחרון והראשון כאשר מה שלפניהם הושלם ואחריהם לא
                levelBtn.setEnabled(true); //clickable
                levelBtn.setActivated(true); //next
            } else if (i == 15 && subLevels[14].isComplete() && !subLevels[15].isComplete()) {
                //אם נמצאים במצב האחרון (הוא הבא בתור)
                levelBtn.setEnabled(true); //clickable
                levelBtn.setActivated(true);
            }
            if (subLevels[i].isComplete()) {
                levelBtn.setActivated(false); //not next
                levelBtn.setSelected(true); //completed
            }
        }else{
            levelBtn.setActivated(false); //not next
            levelBtn.setSelected(true); //completed
            levelBtn.setEnabled(true);
        }
    }*/


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

        if (requestCode == 1){
            if (data != null && resultCode == -1){
                //receiving data from SimonLevel1
                int receiveStars = data.getIntExtra("starsSimon", 0);
                int receiveSubLevel = data.getIntExtra("subLevelSimon",0);
                int receiveLevel = data.getIntExtra("levelSimon",0);
                int[] receiveArrayOfMoves = data.getIntArrayExtra("getArrayOfMoves");
                int receiveHighScore = data.getIntExtra("highScoreSimon",0);

                if (level.getColor() == receiveLevel) {

                    /*if (!level.getSubLevels()[receiveSubLevel].isComplete())
                        numOfSubLevelsCompleted++;*/

                    /*subLevels[receiveSubLevel].setComplete(true);
                    subLevels[receiveSubLevel].setStars(receiveStars);
                    subLevels[receiveSubLevel].setArrayOfMoves(receiveArrayOfMoves);*/

                    level.getSubLevels()[receiveSubLevel].setComplete(true);
                    level.getSubLevels()[receiveSubLevel].setStars(receiveStars);
                    level.getSubLevels()[receiveSubLevel].setArrayOfMoves(receiveArrayOfMoves);
                    level.getSubLevels()[receiveSubLevel].setHighScore(receiveHighScore);

                    if (level.getSubLevels()[receiveSubLevel].getStatus() == Status.NEXT) {
                        level.getSubLevels()[receiveSubLevel].setStatus(Status.COMPLETE);
                        if (receiveSubLevel < 15)
                            level.getSubLevels()[receiveSubLevel + 1].setStatus(Status.NEXT);
                        else { // =15
                            //numOfLevelsCompleted++;
                            level.getSubLevels()[receiveSubLevel].setStatus(Status.COMPLETE);
                            ((LevelsActivity) Objects.requireNonNull(getActivity())).MoveNext(getView());
                        }
                    }

                }
                init();
            }
        }
    }

}
