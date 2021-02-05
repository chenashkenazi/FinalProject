package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SimonLevel1 extends AppCompatActivity {
    ImageView leftTop;
    ImageView leftBottom;
    ImageView rightTop;
    ImageView rightBottom;

    private Simon simon;






    public int numberOfElmentsInMovesArray = 0, k = 0, numberOfClicksEachStage = 0, x;
    public int MAX_LENGTH, number_of_level, Amount_of_Image_view = 4;
    public int[] array_of_moves;
    final Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simon_game);
        leftTop = findViewById(R.id.level1_button_top_left);
        leftBottom = findViewById(R.id.level1_button_bottom_left);
        rightTop = findViewById(R.id.level1_button_top_right);
        rightBottom = findViewById(R.id.level1_button_bottom_right);

//צריך לקבל את MAX_LENGTH ו number_of_level מה LevelsFragment
//number_of_level=


        leftTop.setOnTouchListener(onTouch);
        leftBottom.setOnTouchListener(onTouch);
        rightBottom.setOnTouchListener(onTouch);
        rightTop.setOnTouchListener(onTouch);

        MAX_LENGTH= (number_of_level + 2) * 3 + 1;
        simon = new Simon(MAX_LENGTH, Amount_of_Image_view);
        array_of_moves = simon.getArray_of_moves(simon);


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
        //  array_of_moves= simon.getArray_of_moves(simon);
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


    private void clear() {//reset the game to initial state
        numberOfClicksEachStage = 0;
        numberOfElmentsInMovesArray = 0;
    }

    private int stars() {

        if (this.numberOfElmentsInMovesArray < (this.MAX_LENGTH / 3))
        {
            return 0;
        }
        else if (this.numberOfElmentsInMovesArray < (2 * this.MAX_LENGTH / 3))
        {
            return 1;
        }
        else if (this.numberOfElmentsInMovesArray < this.MAX_LENGTH)
        {
            return 2;
        }
        else
        {
            return 3;
        }


    }


}