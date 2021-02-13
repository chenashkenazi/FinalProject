package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playBtn = findViewById(R.id.play_btn);
        Button resetBtn = findViewById(R.id.reset_game_btn);
        ImageView simonAnimation = findViewById(R.id.simon_animation);

        AnimationDrawable animationDrawable = (AnimationDrawable)simonAnimation.getDrawable();
        animationDrawable.start();

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelsActivity.class);
                startActivity(intent);
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