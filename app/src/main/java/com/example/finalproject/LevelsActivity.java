package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
/*public class LevelsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load setting fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new LevelsFragment()).commit();
    }

    public static class LevelsFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.levels_fragment);
        }
    }

    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if (preference instanceof )
            return false;
        }
    };
}*/
public class LevelsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Level[] levels;
    ArrayList<Fragment> fragments;

    private final static int SUB_LEVEL_SIZE = 16;

    private int numOfFinishedLevels = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_activity);

        viewPager = findViewById(R.id.levels_view_pager);
        tabLayout = findViewById(R.id.levels_tab_layout);

        init();
    }

    private void init() {
        fragments = new ArrayList<>();
        levels = Levels.getLevels();

        for (int i = 0; i < levels.length; i++) {

            LevelsFragment fragment = LevelsFragment.getInstance(levels[i]);
            fragments.add(fragment);
        }
        levels[0].setOpen(true);

        LevelsPagerAdapter pagerAdapter = new LevelsPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);

        for (int i=0; i<numOfFinishedLevels; i++) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            levels[viewPager.getCurrentItem()].setOpen(true); //open nextLevel
            levels[viewPager.getCurrentItem()].getSubLevels()[0].setStatus(Status.NEXT); //open nextLevel
        }

    }


    public void FinishLevel(View view){
        numOfFinishedLevels++;
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            numOfFinishedLevels = data.getIntExtra("numOfFinishedLevels",0);
            for (int i = 0; i < levels.length; i++) {
                levels[i].setColor(data.getIntExtra("levelColorNumber", 0));
                levels[i].setOpen(data.getBooleanExtra("levelIsOpen", false));
                levels[i].setTitle(data.getStringExtra("levelTitle"));
                for (int j = 0; j < SUB_LEVEL_SIZE; j++) {
                    levels[i].getSubLevels()[j].setSubLevelNumber(data.getIntExtra("subLevelNumber", 0));
                    levels[i].getSubLevels()[j].setStars(data.getIntExtra("subLevelNumberOfStars", 0));
                    levels[i].getSubLevels()[j].setComplete(data.getBooleanExtra("subLevelIsComplete", false));
                    levels[i].getSubLevels()[j].setHighScore(data.getIntExtra("subLevelHighScore", 0));
                    levels[i].getSubLevels()[j].setArrayOfMoves(data.getIntArrayExtra("subLevelArrayOfMoves"));
                }
            }
        }
    }
}

