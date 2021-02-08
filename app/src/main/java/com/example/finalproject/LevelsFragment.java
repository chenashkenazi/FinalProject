package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.finalproject.resourses.Levels;

import java.util.Objects;

import static com.example.finalproject.resourses.Levels.FIRST_LEVEL;

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

    private int number_of_subLevels_completed = 0;

    private int receiveStars = 0, receiveNumber = 0;

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

        for (int i=0 ; i<16 ; i++){
            subLevels[i] = new SubLevel(i);
        }
        /*subLevels[0].setComplete(true);
        subLevels[0].setStars(1);
        subLevels[1].setComplete(true);
        subLevels[1].setStars(3);
        subLevels[2].setComplete(true);
        subLevels[2].setStars(2);*/

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //receiving data from SimonLevel1
        Intent incomingIntent = Objects.requireNonNull(getActivity()).getIntent();
        receiveStars = incomingIntent.getIntExtra("starsSimonLevel1",0);
        receiveNumber = incomingIntent.getIntExtra("numberSimonLevel1",0);

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
        //to set the different properties to the widgets
        if(level != null){

            levelNumTv.setText(level.getTitle());

            CustomAdapter customAdapter = new CustomAdapter();

            gridView.setAdapter(customAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //..
                    //לשלוח ל-SimonLevel1 את מספר השלב (INT) הנוכחי לפי ה-title
                    //מקבלת בחזרה את כמות הכוכבים
                    /*Intent intent;
                    switch (level.getTitle()){
                        case "LEVEL 1":
                            intent = new Intent(getActivity().getApplicationContext(),SimonLevel1.class);
                            intent.putExtra("subLevelNumber",level.getSubLevels()[i].getSubLevelNumber());
                            startActivity(intent);
                            break;
                        case "LEVEL 2":

                            break;
                        case "LEVEL 3":

                            break;
                    }
*/

                    //if subLevel completed:
                    /*subLevels[number_of_subLevels_completed].setStars(1); //1-3
                    level.setSubLevels(subLevels);*/
                    //number_of_subLevels_completed++;
                    //init();

                }
            });
        }
    }

    /*public Object getSystemService(String layoutInflaterService) {
        return getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    }*/

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 16;
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

            Button levelBtn = (Button)grid.findViewById(R.id.level_btn);
            ImageView star1Iv = (ImageView)grid.findViewById(R.id.first_star);
            ImageView star2Iv = (ImageView)grid.findViewById(R.id.second_star);
            ImageView star3Iv = (ImageView)grid.findViewById(R.id.third_star);

            int level_number = i+1;
            levelBtn.setText(level_number+"");

            DisplayMetrics displayMetrics = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            levelBtn.setWidth((displayMetrics.heightPixels)/9);
            levelBtn.setHeight((displayMetrics.heightPixels)/10);


            if (i == receiveNumber && receiveStars > 0){

                number_of_subLevels_completed++;
                subLevels[i].setComplete(true);
                subLevels[i].setStars(receiveStars);

                //activating stars
                switch (subLevels[i].getStars()){
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
            }

            if (subLevels[15].isComplete()){
                //level is complete
                //צריך לעשות שהתצוגה תעבור לפרגמנט הבא בתור
            }


            /*if (level.getTitle().equals("LEVEL 1")) {
                if (i > 0 && i < 15 && subLevels[i - 1].isComplete() && !subLevels[i + 1].isComplete()) {
                    levelBtn.setActivated(true);
                } else if (i == 0 && !subLevels[0].isComplete() && !subLevels[1].isComplete()) {
                    levelBtn.setActivated(true);
                } else if (i == 15 && subLevels[14].isComplete() && !subLevels[15].isComplete()) {
                    levelBtn.setActivated(true);
                }
                if (subLevels[i].isComplete()) {
                    levelBtn.setActivated(false);
                    levelBtn.setSelected(true);
                }
            }*/

            if (level == FIRST_LEVEL) {
                if (i > 0 && i < 15 && subLevels[i - 1].isComplete() && !subLevels[i + 1].isComplete()) {
                    levelBtn.setActivated(true);
                } else if (i == 0 && !subLevels[0].isComplete() && !subLevels[1].isComplete()) {
                    levelBtn.setActivated(true);
                } else if (i == 15 && subLevels[14].isComplete() && !subLevels[15].isComplete()) {
                    levelBtn.setActivated(true);
                }
                if (subLevels[i].isComplete()) {
                    levelBtn.setActivated(false);
                    levelBtn.setSelected(true);
                }
            }

            levelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sentInstance(i);
                }
            });

            return grid;
        }

        private void sentInstance(int i){
            Intent intent = new Intent(LevelsFragment.this.getActivity(),SimonLevel1.class);
            intent.putExtra("subLevelNumber",subLevels[i].getSubLevelNumber());
            intent.putExtra("numberOfStars",subLevels[i].getStars());
            startActivity(intent);
        }
    }


}
