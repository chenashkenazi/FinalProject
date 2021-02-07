package com.example.finalproject.resourses;

import com.example.finalproject.*;

/*this class will specifics of each individual level object*/
public class Levels {

    public static Level[] getLevels(){
        return LEVELS;
    }

    public static final Level FIRST_LEVEL = new Level("4 COLORS");
    public static final Level SECOND_LEVEL = new Level("6 COLORS");
    public static final Level THIRD_LEVEL = new Level("8 COLORS");

    public static final Level[] LEVELS = {FIRST_LEVEL, SECOND_LEVEL, THIRD_LEVEL};
}
