package com.example.finalproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.finalproject.resourses.Levels;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LevelsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_activity);

        viewPager = findViewById(R.id.levels_view_pager);
        tabLayout = findViewById(R.id.levels_tab_layout);
        //viewPager.setLayoutDirection();

        init();
    }

    private void init() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        Level[] levels = Levels.getLevels();

        for(Level level:levels){
            LevelsFragment fragment = LevelsFragment.getInstance(level);
            fragments.add(fragment);
        }

        LevelsPagerAdapter pagerAdapter = new LevelsPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);
    }
}
