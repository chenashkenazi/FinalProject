package com.example.finalproject;

import java.util.Random;

public class Simon {
    private int MAX_LENGTH;
    private int Amount_of_Image_view;
    private   int[] array_of_moves;
    private Random r;

    public Simon(int MAX_LENGTH, int Amount_of_Image_view)
    {
        this.Amount_of_Image_view= Amount_of_Image_view;
        this.MAX_LENGTH= MAX_LENGTH;
        this.array_of_moves= new int[MAX_LENGTH];
        r= new Random();
    }
    public int getMAX_LENGTH()
    {
        return MAX_LENGTH;
    }
    public void setMAX_LENGTH(int MAX_LENGTH)
    {
        this.MAX_LENGTH= MAX_LENGTH;
    }
    public int getAmount_of_Image_view()
    {
        return this.Amount_of_Image_view;
    }
    public void setAmount_of_Image_view( int Amount_of_Image_view)
    {
        this.Amount_of_Image_view= Amount_of_Image_view;

    }
    public int[] getArray_of_moves(Simon simon)
    {
        this.array_of_moves= simon.appendValueToArray(simon);
        return this.array_of_moves;
    }

    public void setArray_of_moves(int[] array_of_moves) {
        this.array_of_moves = array_of_moves;
    }

    public int generateRandomNumber(Simon simon) {
        return r.nextInt(simon.Amount_of_Image_view) + 1; // generate random number between 1 and 4
    }

    public int[] appendValueToArray(Simon simon) {  // add random number to the first free position in the array
        for (int i = 0; i < simon.MAX_LENGTH; i++) {
            if (simon.array_of_moves[i] == 0) {
                simon.array_of_moves[i] = generateRandomNumber(simon);
                break;
            }
        }
        return simon.array_of_moves;

    }
}
