package edu.neu.earful;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public enum Interval {
    perfect1,
    minor2,
    major2,
    minor3,
    major3,
    perfect4,
    minor5,
    perfect5,
    minor6,
    major6,
    minor7,
    major7,
    perfect8;

    public static ArrayList<Interval> getIntervalsForDifficulty(DifficultyLevel difficulty) {
        switch(difficulty) {
            case LEVEL1:
                return new ArrayList<>(Arrays.asList(perfect1, perfect5, perfect8));
            case LEVEL2:
                return new ArrayList<>(Arrays.asList(perfect1, perfect5, perfect8, major2, major3));
            case LEVEL3:
                return new ArrayList<>(Arrays.asList(perfect1, perfect5, perfect8, major2, major3, perfect4, major6));
            case LEVEL4:
                return new ArrayList<>(Arrays.asList(perfect1, perfect5, perfect8, major2, major3, perfect4, major6, major7, minor2, minor3));
            case LEVEL5:
                return new ArrayList<>(Arrays.asList(perfect1, perfect5, perfect8, major2, major3, perfect4, major6, major7, minor2, minor3, minor5, minor6, minor7));
            default:
                Log.v("TAG", "Invalid difficulty given, could not get intervals");
                return new ArrayList<>();
        }
    }
}
