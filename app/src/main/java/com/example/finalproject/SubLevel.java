package com.example.finalproject;

/*Here we're going to define the Sub-Level object*/

public class SubLevel {
    private int subLevelNumber; //exp: the first level in level1
    private int stars; //0=not passed the level, 1-3
    private boolean isComplete; //status

    public SubLevel(int subLevelNumber, int stars) {
        this.subLevelNumber = subLevelNumber;
        this.stars = stars;
        isComplete = false;
    }

    public int getSubLevelNumber() {
        return subLevelNumber;
    }

    public void setSubLevelNumber(int subLevelNumber) {
        this.subLevelNumber = subLevelNumber;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
