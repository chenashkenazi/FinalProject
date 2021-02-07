package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SimonLevel3 extends AppCompatActivity {
    ImageView leftTop1;
    ImageView leftBottom1;
    ImageView rightTop1;
    ImageView rightBottom1;
    ImageView leftTop2;
    ImageView leftBottom2;
    ImageView rightTop2;
    ImageView rightBottom2;

    private Simon simon;

    public int numberOfElementsInMovesArray = 0; //index of moves the user succeed to make
    private int numberOfClicksEachStage = 0; //index in array_of_moves
    private int highScore = 0; //
    private int colorClicked; //whether color has been touched

    private int maxLength; //array's max length - sets differently for each subLevel
    public int[] array_of_moves; //the array of moves of the subLevel (created randomly in Simon's class)
    final Handler handler = new Handler();

    //NEWWW
    public SoundPool sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simon_level3);

        int amountOfImageView = 8;
        int number_of_level; //from LevelsFragment

        leftTop1 = findViewById(R.id.level3_left_top1);
        leftBottom1 = findViewById(R.id.level3_left_bottom1);
        rightTop1 = findViewById(R.id.level3_right_top1);
        rightBottom1 = findViewById(R.id.level3_right_bottom1);
        leftTop2 = findViewById(R.id.level3_left_top2);
        leftBottom2 = findViewById(R.id.level3_left_bottom2);
        rightTop2 = findViewById(R.id.level3_right_top2);
        rightBottom2 = findViewById(R.id.level3_right_bottom2);


        leftTop1.setOnTouchListener(onTouch);
        leftBottom1.setOnTouchListener(onTouch);
        rightBottom1.setOnTouchListener(onTouch);
        rightTop1.setOnTouchListener(onTouch);
        leftTop2.setOnTouchListener(onTouch);
        leftBottom2.setOnTouchListener(onTouch);
        rightBottom2.setOnTouchListener(onTouch);
        rightTop2.setOnTouchListener(onTouch);

        //getting intent from LevelsFragment
        Intent incomingIntent = getIntent();
        number_of_level = incomingIntent.getIntExtra("subLevelNumber",0);

        maxLength = (number_of_level + 2) * 3 + 1;

        simon = new Simon(maxLength, amountOfImageView);
        System.out.println("max length: " + maxLength);
        //array_of_moves = simon.getArray_of_moves(simon);
        array_of_moves = simon.getArrayOfMoves();
        for (int i = 0; i < maxLength; i++) {
            System.out.println(array_of_moves[i]);
        }
        final Runnable r = new Runnable() {
            public void run() {
                playGame();
            }
        };
        handler.postDelayed(r, 2000);
    }



    ImageView.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                /*which color the user touched*/
                switch (v.getId()) {
                    case R.id.level3_left_top1:
                        colorClicked = 1;
                        break;
                    case R.id.level3_left_top2:
                        colorClicked = 2;
                        break;
                    case R.id.level3_left_bottom1:
                        colorClicked = 3;
                        break;
                    case R.id.level3_left_bottom2:
                        colorClicked = 4;
                        break;
                    case R.id.level3_right_bottom2:
                        colorClicked = 5;
                        break;
                    case R.id.level3_right_bottom1:
                        colorClicked = 6;
                        break;
                    case R.id.level3_right_top2:
                        colorClicked = 7;
                        break;
                    case R.id.level3_right_top1:
                        colorClicked = 48;
                        break;

                    }

                /*if the user clicked on the wrong color*/
                if (array_of_moves[numberOfClicksEachStage] != colorClicked) {

                    /*Dialog - restart or return to LevelsFragment*/
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SimonLevel3.this);
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
                            Intent intent = new Intent(SimonLevel3.this, LevelsFragment.class);
                            intent.putExtra("stars", stars());
                            startActivity(intent);

                            finish();
                        }

                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    return true;
                }

                playSound(v.getId());
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
                    handler.postDelayed(r, 3000);
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
                    playSound(R.id.level3_left_top1);
                    xorMyColor(leftTop1);
                } else if (array_of_moves[click_index] == 2) {
                    playSound(R.id.level3_left_top2);
                    xorMyColor(leftTop2);
                } else if (array_of_moves[click_index] == 3) {
                    playSound(R.id.level3_left_bottom1);
                    xorMyColor(leftBottom1);
                }else if (array_of_moves[click_index] == 4) {
                    playSound(R.id.level3_left_bottom2);
                    xorMyColor(leftBottom2);
                }else if (array_of_moves[click_index] == 5) {
                    playSound(R.id.level3_right_bottom2);
                    xorMyColor(rightBottom2);
                }else if (array_of_moves[click_index] == 6) {
                    playSound(R.id.level3_right_bottom1);
                    xorMyColor(rightBottom1);
                }else if (array_of_moves[click_index] == 7) {
                    playSound(R.id.level3_right_top2);
                    xorMyColor(rightTop2);
                } else {
                    playSound(R.id.level3_right_top1);
                    xorMyColor(rightTop1);
                }
            }
        };

        handler.postDelayed(r, 1000 * click_index);
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

    private void playSound(int id) { //function that plays sound according to sound ID
        int audioRes = 0;
        switch (id) {
            case R.id.level3_left_top1:
                audioRes = R.raw.sound2;
                break;
            case R.id.level3_left_top2:
                audioRes = R.raw.sound1;
                break;
            case R.id.level3_left_bottom1:
                audioRes = R.raw.sound3;
                break;
            case R.id.level3_left_bottom2:
                audioRes = R.raw.sound4;
                break;
            case R.id.level3_right_bottom2:
                audioRes = R.raw.sound5;
                break;
            case R.id.level3_right_bottom1:
                audioRes = R.raw.sound6;
                break;
            case R.id.level3_right_top2:
                audioRes = R.raw.sound7;
                break;
            case R.id.level3_right_top1:
                audioRes = R.raw.sound8;
                break;
        }

        MediaPlayer p = MediaPlayer.create(this, audioRes);
        p.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        p.start();
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