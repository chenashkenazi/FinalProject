package com.example.finalproject.resourses;

import android.content.Context;
import android.content.res.Resources;

import com.example.finalproject.*;

/*this class will specifics of each individual level object*/
public class Levels {

    public static Level[] getLevels(){
        //FIRST_LEVEL.setOpen(true);
        return LEVELS;
    }

    public static final Level FIRST_LEVEL = new Level(4);
    public static final Level SECOND_LEVEL = new Level(6);
    public static final Level THIRD_LEVEL = new Level(8);


    public static final Level[] LEVELS = {FIRST_LEVEL, SECOND_LEVEL, THIRD_LEVEL};
}
