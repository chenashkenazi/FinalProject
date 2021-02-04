package com.example.finalproject.resourses;

import com.example.finalproject.Level;

/*this class will specifics of each individual level object*/
public class Levels {

    public static Level[] getLevels(){
        return LEVELS;
    }

    public static final Level FIRST_LEVEL = new Level("LEVEL 1");
    public static final Level SECOND_LEVEL = new Level("LEVEL 2");
    public static final Level THIRD_LEVEL = new Level("LEVEL 3");

    public static final Level[] LEVELS = {FIRST_LEVEL, SECOND_LEVEL, THIRD_LEVEL};
}
