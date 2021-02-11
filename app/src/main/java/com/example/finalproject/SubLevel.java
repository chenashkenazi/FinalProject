package com.example.finalproject;

enum Status{LOCK, NEXT, COMPLETE}

/*Here we're going to define the Sub-Level object*/

public class SubLevel{
    //Parcelable - to package our objects for our custom class and attach that to a bundle

    private int subLevelNumber; //the index in Level[]. exp: the first level in level1
    private int stars; //0=not passed the level, 1-3
    private boolean isComplete; //status
    private int highScore;
    private Status status;
    private int levelNumber;

    private int[] arrayOfMoves;

    public SubLevel(int subLevelNumber) {
        setSubLevelNumber(subLevelNumber);
        setStars(0);
        setComplete(false);
        setHighScore(0);
        setStatus(Status.LOCK);
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

    public int[] getArrayOfMoves() {
        return arrayOfMoves;
    }

    public void setArrayOfMoves(int[] arrayOfMoves) {
        this.arrayOfMoves = arrayOfMoves;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
