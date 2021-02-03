package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class LevelsPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragments;

    public LevelsPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        fragments = fragmentArrayList;
    }

    public LevelsPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> fragmentArrayList) {
        super(fm, behavior);
        fragments = fragmentArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
