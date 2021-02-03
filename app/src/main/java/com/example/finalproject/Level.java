package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Level implements Parcelable {

    private String title;
    private SubLevel[] subLevels;

    public Level(String title) {
        this.title = title;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }
}
