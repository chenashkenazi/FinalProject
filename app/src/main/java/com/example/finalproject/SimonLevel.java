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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SimonLevel extends AppCompatActivity {

    //4 COLORS
    private ImageView leftTop;
    private ImageView leftBottom;
    private ImageView rightTop;
    private ImageView rightBottom;
    private TextView score;
    private TextView bestScore;

    private ImageButton pause;
    private ImageButton play;

    private FrameLayout pauseLayout;

    //6 COLORS
    //ImageView leftTop;
    private ImageView leftCenter;
    //ImageView leftBottom;
    //ImageView rightTop;
    private ImageView rightCenter;
    //ImageView rightBottom;

    //8 COLORS
    private ImageView leftTop1;
    private ImageView leftBottom1;
    private ImageView rightTop1;
    private ImageView rightBottom1;
    private ImageView leftTop2;
    private ImageView leftBottom2;
    private ImageView rightTop2;
    private ImageView rightBottom2;

    private int numberOfElementsInMovesArray = 0; //index of moves the user succeed to make
    private int numberOfClicksEachStage = 0; //index in array_of_moves
    private int currentScore = 0; //
    private int colorClicked = 0; //whether color has been touched
    private int amountOfImageView;

    private int maxLength = 0; //array's max length - sets differently for each subLevel
    public int[] array_of_moves; //the array of moves of the subLevel (created randomly in Simon's class)
    final Handler handler = new Handler();

    public SoundPool sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

    /*arguments from LevelsFragment*/
    private int number_of_level = 0;
    private int incomingStars = 0; //how many stars user has already
    private int incomingScore;

    ImageView wellDoneAnimation;
    Animation animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simon);

        /*getting the layout's ID*/
        LinearLayout linearLayout = findViewById(R.id.level1_layout);
        FrameLayout frameLayout = findViewById(R.id.level2_layout);
        RelativeLayout relativeLayout = findViewById(R.id.level3_layout);

        /*getting intent from LevelsFragment*/
        Intent incomingIntent = getIntent();
        number_of_level = incomingIntent.getIntExtra("subLevelNumber", 0) + 1; //number_of_level starts at 0
        incomingStars = incomingIntent.getIntExtra("numberOfStars", 0);
        amountOfImageView = incomingIntent.getIntExtra("levelNumber", 0);
        incomingScore= incomingIntent.getIntExtra("highScoreSimon", 0);
        int[] incomingArrayOfMoves = incomingIntent.getIntArrayExtra("putArrayOfMoves");

        wellDoneAnimation = findViewById(R.id.welldone_animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.welldone_anim);
        wellDoneAnimation.setVisibility(View.GONE);

        play = findViewById(R.id.backToPlay);
        pause = findViewById(R.id.pauseBtn);
        pauseLayout = findViewById(R.id.pauseLayout);
        pauseLayout.setVisibility(View.GONE);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfElementsInMovesArray--;
                pauseLayout.setVisibility(View.VISIBLE);

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseLayout.setVisibility(View.GONE);
                final Runnable r = new Runnable() {
                    public void run() {
                        playGame();
                    }
                };
                handler.postDelayed(r, 1000);
            }
        });

        score = findViewById(R.id.currentScore);
        bestScore = findViewById(R.id.bestScoreUpdate);
        bestScore.setText(String.valueOf(incomingScore));

        /*setting findViewById & setOnTouchListener to each ImageView*/
        switch (amountOfImageView) {
            case 4:
                linearLayout.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.INVISIBLE);

                leftTop = findViewById(R.id.level1_button_top_left);
                leftBottom = findViewById(R.id.level1_button_bottom_left);
                rightTop = findViewById(R.id.level1_button_top_right);
                rightBottom = findViewById(R.id.level1_button_bottom_right);

                leftTop.setOnTouchListener(onTouch);
                leftBottom.setOnTouchListener(onTouch);
                rightBottom.setOnTouchListener(onTouch);
                rightTop.setOnTouchListener(onTouch);
                break;
            case 6:
                linearLayout.setVisibility(View.INVISIBLE);
                frameLayout.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.INVISIBLE);

                leftTop = findViewById(R.id.level2_left_top);
                leftCenter = findViewById(R.id.level2_left_center);
                leftBottom = findViewById(R.id.level2_left_bottom);
                rightTop = findViewById(R.id.level2_right_top);
                rightCenter = findViewById(R.id.level2_right_center);
                rightBottom = findViewById(R.id.level2_right_bottom);

                leftTop.setOnTouchListener(onTouch);
                leftCenter.setOnTouchListener(onTouch);
                leftBottom.setOnTouchListener(onTouch);
                rightBottom.setOnTouchListener(onTouch);
                rightCenter.setOnTouchListener(onTouch);
                rightTop.setOnTouchListener(onTouch);
                break;
            case 8:
                linearLayout.setVisibility(View.INVISIBLE);
                frameLayout.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);

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
                break;
        }

        //maxLength = (number_of_level + 2) * 3 + 1; ////האמיתי!!! לא למחוק!!!
        maxLength = 1; ////בדיקה!!!!!

        Simon simon = new Simon(maxLength, amountOfImageView);

        array_of_moves = simon.getArrayOfMoves();

        //if there is an array already
        if (incomingArrayOfMoves != null) {
            simon.changeArrayOfMoves(incomingArrayOfMoves);
            array_of_moves = incomingArrayOfMoves;
        }

        final Runnable r = new Runnable() {
            public void run() {
                playGame();
            }
        };
        handler.postDelayed(r, 1000);

    }

    ImageView.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                /*which color the user touched*/
                switch (amountOfImageView) {
                    case 4:
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
                        break;
                    case 6:
                        switch (v.getId()) {
                            case R.id.level2_left_top:
                                colorClicked = 1;
                                break;
                            case R.id.level2_left_center:
                                colorClicked = 2;
                                break;
                            case R.id.level2_left_bottom:
                                colorClicked = 3;
                                break;
                            case R.id.level2_right_bottom:
                                colorClicked = 4;
                                break;
                            case R.id.level2_right_center:
                                colorClicked = 5;
                                break;
                            case R.id.level2_right_top:
                                colorClicked = 6;
                                break;
                        }
                        break;
                    case 8:
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
                                colorClicked = 8;
                                break;
                        }
                        break;
                }

                /*if the user clicked on the wrong color*/
                if (array_of_moves[numberOfClicksEachStage] != colorClicked) {

                    /*Dialog - restart or return to LevelsFragment*/
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SimonLevel.this);
                    alertDialogBuilder.setMessage(R.string.game_over);
                    alertDialogBuilder.setPositiveButton(R.string.replay,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //Start over
                                    clear();
                                    playGame();
                                }
                            });
                    alertDialogBuilder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            numberOfElementsInMovesArray--;
                            finishLevel();
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

                /*if the user clicked on the all the right color*/
                if (numberOfElementsInMovesArray == numberOfClicksEachStage) {

                    numberOfClicksEachStage = 0;

                    currentScore+=numberOfElementsInMovesArray;
                    score.setText(String.valueOf(currentScore));

                    if (currentScore > incomingScore) {
                        incomingScore = currentScore;
                        bestScore.setText(String.valueOf(incomingScore));
                    }

                    final Runnable r = new Runnable() {
                        public void run() {
                            playGame();
                        }
                    };
                    handler.postDelayed(r, 1000);
                }
            }
            return true;
        }
    };

    public void playGame() {
        if (numberOfElementsInMovesArray == maxLength) {
            finishLevel();
            //Toast.makeText(SimonLevel.this, numberOfElementsInMovesArray+"", Toast.LENGTH_SHORT).show();
        } else {
            numberOfElementsInMovesArray++;
            for (int k = 0; k < numberOfElementsInMovesArray; k++) {
                click(k);
            }
        }

    }

    /*Function that clicks one place randomly on the view*/
    public void click(final int click_index) {
        final Runnable r = new Runnable() {
            public void run() {
                switch (amountOfImageView) {
                    case 4:
                        switch (array_of_moves[click_index]) {
                            case 1:
                                playSound(R.id.level1_button_top_left);
                                xorMyColor(leftTop);
                                break;
                            case 2:
                                playSound(R.id.level1_button_top_right);
                                xorMyColor(rightTop);
                                break;
                            case 3:
                                playSound(R.id.level1_button_bottom_left);
                                xorMyColor(leftBottom);
                                break;
                            case 4:
                                playSound(R.id.level1_button_bottom_right);
                                xorMyColor(rightBottom);
                                break;
                        }
                        break;
                    case 6:
                        switch (array_of_moves[click_index]) {
                            case 1:
                                playSound(R.id.level2_left_top);
                                xorMyColor(leftTop);
                                break;
                            case 2:
                                playSound(R.id.level2_left_center);
                                xorMyColor(leftCenter);
                                break;
                            case 3:
                                playSound(R.id.level2_left_bottom);
                                xorMyColor(leftBottom);
                                break;
                            case 4:
                                playSound(R.id.level2_right_bottom);
                                xorMyColor(rightBottom);
                                break;
                            case 5:
                                playSound(R.id.level2_right_center);
                                xorMyColor(rightCenter);
                                break;
                            case 6:
                                playSound(R.id.level2_right_top);
                                xorMyColor(rightTop);
                                break;
                        }
                        break;
                    case 8:
                        switch (array_of_moves[click_index]) {
                            case 1:
                                playSound(R.id.level3_left_top1);
                                xorMyColor(leftTop1);
                                break;
                            case 2:
                                playSound(R.id.level3_left_top2);
                                xorMyColor(leftTop2);
                                break;
                            case 3:
                                playSound(R.id.level3_left_bottom1);
                                xorMyColor(leftBottom1);
                                break;
                            case 4:
                                playSound(R.id.level3_left_bottom2);
                                xorMyColor(leftBottom2);
                                break;
                            case 5:
                                playSound(R.id.level3_right_bottom2);
                                xorMyColor(rightBottom2);
                                break;
                            case 6:
                                playSound(R.id.level3_right_bottom1);
                                xorMyColor(rightBottom1);
                                break;
                            case 7:
                                playSound(R.id.level3_right_top2);
                                xorMyColor(rightTop2);
                                break;
                            case 8:
                                playSound(R.id.level3_right_top1);
                                xorMyColor(rightTop1);
                                break;
                        }
                        break;
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

    /*function that plays sound according to sound ID*/
    private void playSound(int id) {
        int audioRes = 0;
        switch (amountOfImageView) {
            case 4:
                switch (id) {
                    case R.id.level1_button_top_left:
                        audioRes = R.raw.sound2;
                        break;
                    case R.id.level1_button_top_right:
                        audioRes = R.raw.sound1;
                        break;
                    case R.id.level1_button_bottom_left:
                        audioRes = R.raw.sound3;
                        break;
                    case R.id.level1_button_bottom_right:
                        audioRes = R.raw.sound4;
                        break;

                }
                break;
            case 6:
                switch (id) {
                    case R.id.level2_left_top:
                        audioRes = R.raw.sound2;
                        break;
                    case R.id.level2_left_center:
                        audioRes = R.raw.sound1;
                        break;
                    case R.id.level2_left_bottom:
                        audioRes = R.raw.sound3;
                        break;
                    case R.id.level2_right_bottom:
                        audioRes = R.raw.sound4;
                        break;
                    case R.id.level2_right_center:
                        audioRes = R.raw.sound5;
                        break;
                    case R.id.level2_right_top:
                        audioRes = R.raw.sound6;
                        break;

                }
                break;
            case 8:
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
        if (numberOfElementsInMovesArray < (maxLength / 3))
            return 0;
        else if (numberOfElementsInMovesArray < (2 * maxLength / 3))
            return 1;
        else if (numberOfElementsInMovesArray < maxLength)
            return 2;
        else
            return 3;
    }

    /*finish Level and sending Intent To LevelFragment*/
    private void finishLevel() {

        wellDoneAnimation.setVisibility(View.VISIBLE);
        wellDoneAnimation.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent resultIntent = new Intent();

                //if need to update the subLevel in LevelsFragment
                if (numberOfElementsInMovesArray != 0) {
                    setResult(RESULT_OK, resultIntent);

                    //load extras
                    resultIntent.putExtra("subLevelSimon", number_of_level - 1); //subLevel stats at 0
                    resultIntent.putExtra("levelSimon", amountOfImageView);
                    resultIntent.putExtra("getArrayOfMoves", array_of_moves);
                    resultIntent.putExtra("starsSimon", stars());  //change only if user got higher score
                    resultIntent.putExtra("highScoreSimon", incomingScore); //subLevel stats at 0

                } else
                    setResult(RESULT_CANCELED, resultIntent);
                clear();
                finish();
            }
        }, 2000);
    }
}
