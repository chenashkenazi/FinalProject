package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SimonLevel1 extends AppCompatActivity {
    ImageView leftTop;
    ImageView leftBottom;
    ImageView rightTop;
    ImageView rightBottom;

    /*
    מגדיר את MAX לפי:
    MAX = (number_of_level + 2) * 3 + 1

    //max=10
    if number_of_moves < (MAX/3) - GAME OVER
    else if number_of_moves < (2MAX/3) - 1 star
    else if number_of_moves < (MAX) - 2 stars
    else - 3 stars

    */


    public int numberOfElmentsInMovesArray = 0, k = 0, numberOfClicksEachStage = 0, x;
    final int MAX_LENGTH = 100;
    int array_of_moves[] = new int[MAX_LENGTH];
    Random r = new Random();
    final Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simon_game);
        leftTop = findViewById(R.id.level1_button_top_left);
        leftBottom = findViewById(R.id.level1_button_bottom_left);
        rightTop = findViewById(R.id.level1_button_top_right);
        rightBottom = findViewById(R.id.level1_button_bottom_right);

        leftTop.setOnTouchListener(onTouch);
        leftBottom.setOnTouchListener(onTouch);
        rightBottom.setOnTouchListener(onTouch);
        rightTop.setOnTouchListener(onTouch);
    }

    ImageView.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                switch (v.getId()) {
                    case R.id.level1_button_top_left:
                        x = 1;
                        break;
                    case R.id.level1_button_top_right:
                        x = 2;
                        break;
                    case R.id.level1_button_bottom_left:
                        x = 3;
                        break;
                    case R.id.level1_button_bottom_right:
                        x = 4;
                        break;

                }
                if (array_of_moves[numberOfClicksEachStage] != x) { // on wrong click


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SimonLevel1.this);
                    alertDialogBuilder.setMessage("Game over, Do you want to play again?");
                    alertDialogBuilder.setPositiveButton("Play again",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //Start over
                                    clear();
                                    playGame();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("Back to level", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                        //..מחזיר את כמות הכוכבים



                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    return true;
                }
            }
            return true;
        }

    };

    public void playGame() {
        appendValueToArray();
        numberOfElmentsInMovesArray++;
        for (k = 0; k < numberOfElmentsInMovesArray; k++) {
            click(k);
        }
    }

    public void click(final int click_index) {
        //Function that clicks one place randomally on the view
        final Runnable r = new Runnable() {
            public void run() {
                if (array_of_moves[click_index] == 1) {
                    xorMyColor(leftTop);
                } else if (array_of_moves[click_index] == 2) {
                    xorMyColor(rightTop);
                } else if (array_of_moves[click_index] == 3) {
                    xorMyColor(leftBottom);
                } else {
                    xorMyColor(rightBottom);
                }
            }
        };

      //  handler.postDelayed(r, (2000 - 500 * hardness) * click_index);
    }
    private void xorMyColor(final View v) {
        //function that changes the background color and get it back after 500 milliseconds
        v.getBackground().setAlpha(51);
        final Runnable r = new Runnable() {
            public void run() {
                v.getBackground().setAlpha(255);
            }
        };
        handler.postDelayed(r, 300);
    }

    private int generateRandomNumber() {
        return r.nextInt(4) + 1; // generate random number between 1 and 4
    }

    private void appendValueToArray() {  // add random number to the first free position in the array
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (array_of_moves[i] == 0) {
                array_of_moves[i] = generateRandomNumber();
                break;
            }
        }
    }

    private void clear() {//reset the game to initial state
        for (int i = 0; i < MAX_LENGTH; i++) {
            array_of_moves[i] = 0;
        }
        numberOfClicksEachStage = 0;
        numberOfElmentsInMovesArray = 0;
    }

}