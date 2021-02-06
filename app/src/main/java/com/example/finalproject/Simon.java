package com.example.finalproject;

import java.util.Random;

public class Simon {
    private int maxLength; //array's size - how much moves in this subLevel
    private int amountOfImageView; // 4/6/8 parts in simon's circle
    private int[] arrayOfMoves; //the array of moves of the subLevel (created randomly according to amountOfImageView)
    private final Random r;

    public Simon(int maxLength, int amountOfImageView)
    {
        this.amountOfImageView = amountOfImageView;
        this.maxLength = maxLength;
        this.arrayOfMoves = new int[maxLength];
        setArrayOfMoves();
        r= new Random();
    }

    /*public int getMaxLength()
    {
        return maxLength;
    }
    public void setMaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }
    public int getAmountOfImageView()
    {
        return this.amountOfImageView;
    }
    public void setAmountOfImageView(int amountOfImageView)
    {
        this.amountOfImageView = amountOfImageView;
    }

    public int[] getArray_of_moves(Simon simon)
    {
        this.arrayOfMoves = simon.appendValueToArray(simon);
        return this.arrayOfMoves;
    }

    public void setArrayOfMoves(int[] arrayOfMoves) {
        this.arrayOfMoves = arrayOfMoves;
    }*/


    /*Getters & Setters*/
    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getAmountOfImageView() {
        return amountOfImageView;
    }

    public void setAmountOfImageView(int amountOfImageView) {
        this.amountOfImageView = amountOfImageView;
    }

    public int[] getArrayOfMoves() {

        return this.arrayOfMoves;
    }

    /*public void setArrayOfMoves(int[] arrayOfMoves) {
        this.arrayOfMoves = arrayOfMoves;
    }*/
    public void setArrayOfMoves() {
        this.arrayOfMoves = appendValueToArray();
    }

    /*generating random number between 1 and amountOfImageView*/
    public int generateRandomNumber() {
        return r.nextInt(this.amountOfImageView) + 1;
    }
    /*public int generateRandomNumber(Simon simon) {
        return r.nextInt(simon.amountOfImageView) + 1;
    }*/

    /*adding random number to the first free position in the array*/
    public int[] appendValueToArray() {  //
        for (int i = 0; i < this.maxLength; i++) {
                this.arrayOfMoves[i] = generateRandomNumber();
        }

        return this.arrayOfMoves;
    }
    /*public int[] appendValueToArray(Simon simon) {  //
        for (int i = 0; i < simon.maxLength; i++) {
            if (simon.arrayOfMoves[i] == 0) {
                simon.arrayOfMoves[i] = generateRandomNumber(simon);
                break;
            }
        }

        return simon.arrayOfMoves;
    }*/
}
