package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    public int numberOfElementsInMovesArray = 0; //index of moves the user succeed to make
    private int numberOfClicksEachStage = 0; //index in array_of_moves
    private int highScore = 0; //
    private int colorClicked; //whether color has been touched

    private int maxLength; //array's max length - sets differently for each subLevel
    public int[] array_of_moves; //the array of moves of the subLevel (created randomly in Simon's class)
    final Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simon_game);

        int amountOfImageView = 4;
        int number_of_level; //from LevelsFragment

        leftTop = findViewById(R.id.level1_button_top_left);
        leftBottom = findViewById(R.id.level1_button_bottom_left);
        rightTop = findViewById(R.id.level1_button_top_right);
        rightBottom = findViewById(R.id.level1_button_bottom_right);

        leftTop.setOnTouchListener(onTouch);
        leftBottom.setOnTouchListener(onTouch);
        rightBottom.setOnTouchListener(onTouch);
        rightTop.setOnTouchListener(onTouch);

        //getting intent from LevelsFragment
        Intent incomingIntent = getIntent();
        number_of_level = incomingIntent.getIntExtra("subLevelNumber",0);
        System.out.println("number of level: " + number_of_level);

        maxLength = (number_of_level + 2) * 3 + 1;

        simon = new Simon(maxLength, amountOfImageView);
        System.out.println("max length: " + maxLength);
        //array_of_moves = simon.getArray_of_moves(simon);
        array_of_moves = simon.getArrayOfMoves();
        final Runnable r = new Runnable() {
            public void run() {
                playGame();
            }
        };
        handler.postDelayed(r, 3000);
    }



    ImageView.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                /*which color the user touched*/
                switch (v.getId()) {
                    case R.id.level1_button_top_left:
                        colorClicked = 1;
                        break;
                    case R.id.level1_button_top_right:
                        colorClicked = 2;
                        break;
                    case R.id.level1_button_bottom_left:
                        colorClicked = 3;
                        break;
                    case R.id.level1_button_bottom_right:
                        colorClicked = 4;
                        break;

                }

                /*if the user clicked on the wrong color*/
                if (array_of_moves[numberOfClicksEachStage] != colorClicked) {

                    /*Dialog - restart or return to LevelsFragment*/
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
                            //finishing level and return to LevelsFragment with the amount of stars collected
                            Intent intent = new Intent(SimonLevel1.this, LevelsFragment.class);
                            intent.putExtra("stars", stars());
                            startActivity(intent);

                            finish();
                        }

                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    return true;
                }

                xorMyColor(v);


                numberOfClicksEachStage++;
                /*if the user clicked on the right color*/
                if (numberOfElementsInMovesArray == numberOfClicksEachStage) {
                    //if 4 boxes shown, then activate function
                    //playGame only after 4 clicks have been made by the user

                    numberOfClicksEachStage = 0;
                    if (numberOfElementsInMovesArray > highScore) {
                        highScore = numberOfElementsInMovesArray;
                    }

                    final Runnable r = new Runnable() {
                        public void run() {
                            playGame();
                        }
                    };
                    //handler.postDelayed(r, 2000 - 500 * hardness);
                }

            }
            return true;
        }

    };


    public void playGame() {
        numberOfElementsInMovesArray++;
        for (int k = 0; k < numberOfElementsInMovesArray; k++) {
            click(k);
        }
    }

    /*Function that clicks one place randomly on the view*/
    public void click(final int click_index) {
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

    /*function that changes the background color and get it back after 500 milliseconds*/
    private void xorMyColor(final View v) {

        v.setAlpha(0.5f);
            final Runnable r = new Runnable() {
            public void run() {
                v.setAlpha(1.0f);
            }
        };
        handler.postDelayed(r, 300);
    }

    /*reset the game to initial state*/
    private void clear() {
        numberOfClicksEachStage = 0;
        numberOfElementsInMovesArray = 0;
    }

    /*defining how many stats user should get*/
    private int stars() {
        if (this.numberOfElementsInMovesArray < (this.maxLength / 3))
            return 0;
        else if (this.numberOfElementsInMovesArray < (2 * this.maxLength / 3))
            return 1;
        else if (this.numberOfElementsInMovesArray < this.maxLength)
            return 2;
        else
            return 3;
    }


}