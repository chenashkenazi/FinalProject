package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalproject.resourses.Levels;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    Level[] levels;
    private final static int SUB_LEVEL_SIZE = 16;
    private int numOfFinishedLevels = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playBtn = findViewById(R.id.play_btn);
        Button resetBtn = findViewById(R.id.reset_game_btn);
        ImageView simonAnimation = findViewById(R.id.simon_animation);

        AnimationDrawable animationDrawable = (AnimationDrawable)simonAnimation.getDrawable();
        animationDrawable.start();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        levels = Levels.getLevels();

        String[] titles = new String[]{
                getResources().getString(R.string.level_four_colors) + "",
                getResources().getString(R.string.level_six_colors) + "",
                getResources().getString(R.string.level_eight_colors) + ""};

        for (int i = 0; i < levels.length; i++) {
            Level level = levels[i];
            SubLevel[] subLevel = new SubLevel[SUB_LEVEL_SIZE];
            for (int j = 0; j < SUB_LEVEL_SIZE; j++) {
                subLevel[j] = new SubLevel(j);
            }
            level.setSubLevels(subLevel);
            level.setTitle(titles[i]);
        }

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelsActivity.class);
                if (levels != null){
                    intent.putExtra("numOfFinishedLevels", numOfFinishedLevels);
                    for (int i=0 ; i<levels.length; i++) {
                        intent.putExtra("levelColorNumber", levels[i].getColor());
                        intent.putExtra("levelIsOpen", levels[i].isOpen());
                        intent.putExtra("levelTitle", levels[i].getTitle());
                        for(int j=0;j<SUB_LEVEL_SIZE;j++) {
                            intent.putExtra("subLevelNumber", levels[i].getSubLevels()[j].getSubLevelNumber());
                            intent.putExtra("subLevelNumberOfStars", levels[i].getSubLevels()[j].getStars());
                            intent.putExtra("subLevelIsComplete", levels[i].getSubLevels()[j].isComplete());
                            intent.putExtra("subLevelHighScore", levels[i].getSubLevels()[j].getHighScore());
                            intent.putExtra("subLevelArrayOfMoves", levels[i].getSubLevels()[j].getArrayOfMoves());
                        }

                    }
                }
                startActivityForResult(intent, 2);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.confirm_restart).setMessage(R.string.confirm_restart_message)
                        .setPositiveButton(R.string.yes, new MyAlertDialogListener()).setNegativeButton(R.string.no, new MyAlertDialogListener()).setCancelable(false).show();
            }
        });
    }

    private class MyAlertDialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                Toast.makeText(MainActivity.this, R.string.game_restarted, Toast.LENGTH_SHORT).show();
            }
        }
    }

}