package com.example.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.finalproject.resourses.Levels;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LevelsActivity extends AppCompatActivity {

    public static final int SUB_LEVEL_SIZE = 16; //the amount of subLevels in one Level

    private ViewPager viewPager;
    private TabLayout tabLayout;

    Level[] levels;

    ArrayList<Fragment> fragments;

    //SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

    private final String[] titles = new String[]{
            String.valueOf(R.string.level_four_colors),
            String.valueOf(R.string.level_six_colors),
            String.valueOf(R.string.level_eight_colors)};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_activity);

        loadData();

        viewPager = findViewById(R.id.levels_view_pager);
        tabLayout = findViewById(R.id.levels_tab_layout);

        init();
    }

    private void init() {

        //fragments = new ArrayList<>();

        /*if(levels[0].getSubLevels()[0] == null) {
            levels = Levels.getLevels();
        }*/


        levels = Levels.getLevels();

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
        }

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
        saveData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(fragments);
        editor.putString("levels",json);
        editor.commit();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("levels",null);
        Type type = new TypeToken<ArrayList<Fragment>>(){}.getType();
        fragments = gson.fromJson(json, type);

        if (fragments == null){
            fragments = new ArrayList<>();
        }
    }

    public void MoveNext(View view){
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        //levels[viewPager.getCurrentItem()].setOpen(true); //open nextLevel
    }

    public void MovePrevious(View view){
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

}
