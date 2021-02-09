package com.example.finalproject;

import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import static com.example.finalproject.resourses.Levels.FIRST_LEVEL;
import static com.example.finalproject.resourses.Levels.SECOND_LEVEL;
import static com.example.finalproject.resourses.Levels.THIRD_LEVEL;

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

    private final SubLevel[] subLevels = new SubLevel[16];

    //private int number_of_subLevels_completed = 0; // if %16==0 then switch to next level

    private int numOfLevelsCompleted = 0;
    private int numOfSubLevelsCompleted = 0;

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

        //creating new subLevels only at the beginning
        if (numOfSubLevelsCompleted == 0){
            for (int i=0 ; i<16 ; i++){
                subLevels[i] = new SubLevel(i);
            }
            level.setSubLevels(subLevels);
            switch (level.getColor()){
                case 4:
                    level.setTitle(getResources().getString(R.string.level_four_colors));
                    break;
                case 6:
                    level.setTitle(getResources().getString(R.string.level_six_colors));
                    break;
                case 8:
                    level.setTitle(getResources().getString(R.string.level_eight_colors));
                    break;

            }
        }
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

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //..
                    Toast.makeText(getActivity(), "!!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return subLevels.length;
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

            Button levelBtn = (Button)grid.findViewById(R.id.level_btn);
            ImageView star1Iv = (ImageView)grid.findViewById(R.id.first_star);
            ImageView star2Iv = (ImageView)grid.findViewById(R.id.second_star);
            ImageView star3Iv = (ImageView)grid.findViewById(R.id.third_star);

            levelBtn.setText(level_number);

            //setting subLevel's levelNumber
            subLevels[i].setLevelNumber(level.getColor());

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

            //setting the button-cell's drawable and un-clickable (from selector)
            switch (numOfLevelsCompleted){
                case 0:
                    if (level.getColor() == 4)
                        setButtonsInLevel(levelBtn,i,false);
                    else
                        levelBtn.setEnabled(false); //not clickable
                    break;
                case 1:
                    if (level.getColor() == 4)
                        setButtonsInLevel(levelBtn,i,true);
                    else if (level.getColor() == 6)
                        setButtonsInLevel(levelBtn,i,false);
                    else if (level.getColor() == 8)
                        levelBtn.setEnabled(false); //not clickable
                    break;
                case 2:
                    setButtonsInLevel(levelBtn,i,true);
                    break;
            }

            levelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //sentInstance(i);
                    //Toast.makeText(getActivity(), subLevels[i].getLevelNumber()+"", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), com.example.finalproject.SimonLevel.class);
                    intent.putExtra("levelNumber",subLevels[i].getLevelNumber());
                    intent.putExtra("subLevelNumber",subLevels[i].getSubLevelNumber());
                    intent.putExtra("numberOfStars",subLevels[i].getStars());
                    intent.putExtra("putArrayOfMoves",subLevels[i].getArrayOfMoves());
                    startActivityForResult(intent,1);
                }
            });
            return grid;
        }

        /*private void sentInstance(int i){
            Intent intent = new Intent(getActivity(), SimonLevel.class);
            intent.putExtra("levelNumber",subLevels[i].getLevelNumber());
            intent.putExtra("subLevelNumber",subLevels[i].getSubLevelNumber());
            intent.putExtra("numberOfStars",subLevels[i].getStars());
            intent.putExtra("putArrayOfMoves",subLevels[i].getArrayOfMoves());

            startActivityForResult(intent,1);

            *//*switch (level.getTitle()){
                case "4 COLORS":
                    intent = new Intent(LevelsFragment.this.getActivity(), SimonLevel1.class);
                    intent.putExtra("levelNumber",4);
                    intent.putExtra("subLevelNumber",subLevels[i].getSubLevelNumber());
                    intent.putExtra("numberOfStars",subLevels[i].getStars());
                    intent.putExtra("putArrayOfMoves",subLevels[i].getArrayOfMoves());
                    startActivityForResult(intent,1);
                    break;
                case "6 COLORS":
                    intent = new Intent(LevelsFragment.this.getActivity(), SimonLevel2.class);
                    intent.putExtra("levelNumber",6);
                    intent.putExtra("subLevelNumber",subLevels[i].getSubLevelNumber());
                    intent.putExtra("numberOfStars",subLevels[i].getStars());
                    intent.putExtra("putArrayOfMoves",subLevels[i].getArrayOfMoves());
                    startActivityForResult(intent,1);
                    break;
                case "8 COLORS":
                    intent = new Intent(LevelsFragment.this.getActivity(), SimonLevel3.class);
                    intent.putExtra("levelNumber",8);
                    intent.putExtra("subLevelNumber",subLevels[i].getSubLevelNumber());
                    intent.putExtra("numberOfStars",subLevels[i].getStars());
                    intent.putExtra("putArrayOfMoves",subLevels[i].getArrayOfMoves());
                    startActivityForResult(intent,1);
                    break;
            }*//*
        }*/

        private void setButtonsInLevel(Button levelBtn, int i, boolean completed){
            if (!completed) {
                if (i != 0)
                    levelBtn.setEnabled(false); //not clickable
                if (i == 0 && !subLevels[i].isComplete()) {
                    //אם נמצאים בשלב הראשון (הוא לא הושלם)
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
                    if (i==15){
                        numOfLevelsCompleted++;
                        //Toast.makeText(getActivity(), numOfLevelsCompleted+"", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                levelBtn.setActivated(false); //not next
                levelBtn.setSelected(true); //completed
                levelBtn.setEnabled(true);
            }
        }
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

                if (level.getColor() == receiveLevel){
                    numOfSubLevelsCompleted++;
                    subLevels[receiveSubLevel].setComplete(true);
                    subLevels[receiveSubLevel].setStars(receiveStars);
                    subLevels[receiveSubLevel].setArrayOfMoves(receiveArrayOfMoves);
                }

                init();
            }
        }
    }
}
