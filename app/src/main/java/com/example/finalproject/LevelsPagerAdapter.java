package com.example.finalproject;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class LevelsPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;// = new ArrayList<>();

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


    //..
    @Nullable
    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}
