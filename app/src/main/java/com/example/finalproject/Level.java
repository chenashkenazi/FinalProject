package com.example.finalproject;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

public class Level implements Parcelable {

    private int color;
    private boolean isOpen;
    private String title;
    private SubLevel[] subLevels;

    public Level(int colors) {
        this(colors, false);
    }

    public Level(int colors, boolean isOpen) {
        setColor(colors);
        setOpen(isOpen);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SubLevel[] getSubLevels() {
        return subLevels;
    }

    public void setSubLevels(SubLevel[] subLevels) {
        this.subLevels = subLevels;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    protected Level(Parcel in) {
        title = in.readString();
    }

    public static final Creator<Level> CREATOR = new Creator<Level>() {
        @Override
        public Level createFromParcel(Parcel in) {
            return new Level(in);
        }

        @Override
        public Level[] newArray(int size) {
            return new Level[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }


    protected void clearAllLevel() {
        this.setOpen(false);
    }

}
