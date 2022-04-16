package edu.neu.earful;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private final static Map<Interval, Integer> intervalHalfSteps = new HashMap<Interval, Integer>() {{
        put(perfect1, 0);
        put(minor2, 1);
        put(major2, 2);
        put(minor3, 3);
        put(major3, 4);
        put(perfect4, 5);
        put(minor5, 6);
        put(perfect5, 7);
        put(minor6, 8);
        put(major6, 9);
        put(minor7, 10);
        put(major7, 11);
        put(perfect8, 12);
    }};

    public static ArrayList<Interval> getIntervalsForDifficulty(DifficultyLevel difficulty) {
        ArrayList<Interval> intervals = new ArrayList<>();
        switch(difficulty) {
            case LEVEL5:
                addIntervalInPlace(intervals, minor5);
                addIntervalInPlace(intervals, minor6);
                addIntervalInPlace(intervals, minor7);
            case LEVEL4:
                addIntervalInPlace(intervals, minor3);
                addIntervalInPlace(intervals, major6);
            case LEVEL3:
                addIntervalInPlace(intervals, minor2);
                addIntervalInPlace(intervals, major7);
            case LEVEL2:
                addIntervalInPlace(intervals, major3);
                addIntervalInPlace(intervals, perfect4);
            case LEVEL1:
                addIntervalInPlace(intervals, perfect1);
                addIntervalInPlace(intervals, major2);
                addIntervalInPlace(intervals, perfect5);
                addIntervalInPlace(intervals, perfect8);
                break;
            default:
                Log.v("TAG", "Invalid difficulty given, could not get intervals");
                break;
        }
        return intervals;
    }

    private static void addIntervalInPlace(ArrayList<Interval> intervals, Interval newInterval) {
        final int newHalfStepVal = intervalHalfSteps.get(newInterval);
        int prevHalfStepVal = -1;
        int currHalfStepVal;
        // list is empty, just add without comparing anything
        if (intervals.size() == 0) {
            intervals.add(newInterval);
        }
        // list is not empty, add new interval in place
        else {
            for (Interval currInterval : intervals) {
                // half-step val of current list element
                currHalfStepVal = intervalHalfSteps.get(currInterval);
                // this is NOT the first element of the list
                if (prevHalfStepVal != -1) {
                    // new interval value is between prev & current
                    if (prevHalfStepVal < newHalfStepVal && currHalfStepVal > newHalfStepVal) {
                        intervals.add(intervals.indexOf(currInterval), newInterval);
                        break;
                    }
                }
                // this IS the first element of the list
                else {
                    // new interval comes before current
                    if (newHalfStepVal < currHalfStepVal) {
                        intervals.add(intervals.indexOf(currInterval), newInterval);
                        break;
                    }
                }
                // this is the last element in the list and the new interval comes after it
                if (newHalfStepVal > currHalfStepVal && intervals.indexOf(currInterval) == intervals.size() - 1) {
                    intervals.add(newInterval);
                    break;
                }
                prevHalfStepVal = currHalfStepVal;
            }
        }
    }
}
