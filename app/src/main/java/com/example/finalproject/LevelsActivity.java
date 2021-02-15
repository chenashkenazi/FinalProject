package com.example.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LevelsActivity extends AppCompatActivity {

    final String TAG = "ActivityLifeCycle";

    public static final int SUB_LEVEL_SIZE = 16; //the amount of subLevels in one Level

    private ViewPager viewPager;
    private TabLayout tabLayout;

    ArrayList<Fragment> fragments;
    Level[] levels;

    SharedPreferences sharedPreferences;

    File file;

    private final String[] titles = new String[]{
            String.valueOf(R.string.level_four_colors),
            String.valueOf(R.string.level_six_colors),
            String.valueOf(R.string.level_eight_colors)};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_activity);

        Log.i(TAG,"on create");
        loadData();


        viewPager = findViewById(R.id.levels_view_pager);
        tabLayout = findViewById(R.id.levels_tab_layout);

        init();
    }

    private void init() {

        /*if(levels[0].getSubLevels()[0] == null) {
            levels = Levels.getLevels();
        }*/

        //levels = Levels.getLevels(); //לשים בהערה כשהגול פעיל
        fragments = new ArrayList<>(); //לשים בהערה כשהג'סון פעיל

        for (int i = 0; i < levels.length; i++) {
            SubLevel[] subLevel = new SubLevel[SUB_LEVEL_SIZE];
            for (int j=0 ; j<SUB_LEVEL_SIZE ; j++){
                subLevel[j] = new SubLevel(j);
            }
            levels[i].setSubLevels(subLevel);
            levels[i].setTitle(titles[i]);

            Level level = levels[i];
            LevelsFragment fragment = LevelsFragment.getInstance(level);
            fragments.add(fragment);
        }


        /*Level[] levels = Levels.getLevels();
        for (int i = 0; i < levels.length; i++) {
            SubLevel[] subLevel = new SubLevel[SUB_LEVEL_SIZE];
            for (int j=0 ; j<SUB_LEVEL_SIZE ; j++){
                subLevel[j] = new SubLevel(j);
                subLevel[j].setLevelNumber(levels[i].getColor());
            }
            levels[i].setSubLevels(subLevel);
            levels[i].setTitle(titles[i]);

            Level level = levels[i];
            LevelsFragment fragment = LevelsFragment.getInstance(level);
            fragments.add(fragment);
        }*/

        /*for (int i = 0; i < levels.length; i++) {
            Level level = levels[i];

            SubLevel[] subLevels = new SubLevel[subLevelSize];

            for (int j = 0; j< subLevelSize; j++)
                subLevels[i] = new SubLevel(j);

            levels[i].setSubLevels(subLevels);
            levels[i].setTitle(titles[i]);

            LevelsFragment levelsFragment = LevelsFragment.getInstance(level);
            fragments.add(levelsFragment);
        }*/

        LevelsPagerAdapter pagerAdapter = new LevelsPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"on destroy");
        saveData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"on pause");
        saveData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"on start");
        loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"on stop");
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"on resume");
        loadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"on restart");
        loadData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG,"on back press");
        saveData();
    }

    private void saveData(){
        Log.i(TAG,"on save data");

        //GOOL(1) - SharedPreference
        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        String arrayOfMoves = "";
        for(int i=0 ; i<levels.length; i++){
            editor.putInt("level's color number", levels[i].getColor());
            editor.putBoolean("level is open", levels[i].isOpen());
            editor.putString("level's title",levels[i].getTitle());
            for (int j=0 ; j<SUB_LEVEL_SIZE; j++){
                editor.putInt("subLevel's number", levels[i].getSubLevels()[j].getSubLevelNumber());
                editor.putInt("subLevel's stars",levels[i].getSubLevels()[j].getStars());
                editor.putBoolean("subLevel is complete",levels[i].getSubLevels()[j].isComplete());
                editor.putInt("subLevel's highScore",levels[i].getSubLevels()[j].getHighScore());
                editor.putString("subLevel's status",levels[i].getSubLevels()[j].getStatus().toString());
                for (int k=0 ; k<levels[i].getSubLevels()[j].getArrayOfMoves().length ; k++){
                    arrayOfMoves.concat(String.valueOf(levels[i].getSubLevels()[j].getArrayOfMoves()[k])).concat("$");
                }
                editor.putString("subLevel's array of moves",arrayOfMoves);
            }
        }
        editor.commit();*/

        //Gson
        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(fragments);
        editor.putString("fragment",json);
        editor.apply();
    }

    private void loadData(){
        Log.i(TAG,"on load data");

        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("fragment",null);
        Type type = new TypeToken<ArrayList<Fragment>>(){}.getType();
        fragments = gson.fromJson(json, type);

        if (fragments == null)
            fragments = new ArrayList<>();
    }

    public void MoveNext(View view){
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        //levels[viewPager.getCurrentItem()].setOpen(true); //open nextLevel
    }

    public void MovePrevious(View view){
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

}

