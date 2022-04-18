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

    public final static Map<Integer, String> midiToFile = new HashMap<Integer, String>() {{
        put(48, "c3.mp3");
        put(49, "c#3.mp3");
        put(50, "d3.mp3");
        put(51, "d#3.mp3");
        put(52, "e3.mp3");
        put(53, "f3.mp3");
        put(54, "f#3.mp3");
        put(55, "g3.mp3");
        put(56, "g#3.mp3");
        put(57, "a3.mp3");
        put(58, "a#3.mp3");
        put(59, "b3.mp3");
        put(60, "c4.mp3");
        put(61, "c#4.mp3");
        put(62, "d4.mp3");
        put(63, "d#4.mp3");
        put(64, "e4.mp3");
        put(65, "f4.mp3");
        put(66, "f#4.mp3");
        put(67, "g4.mp3");
        put(68, "g#4.mp3");
        put(69, "a4.mp3");
        put(70, "a#4.mp3");
        put(71, "b4.mp3");
        put(72, "c5.mp3");
        put(73, "c#5.mp3");
        put(74, "d5.mp3");
        put(75, "d#5.mp3");
        put(76, "e5.mp3");
        put(77, "f5.mp3");
        put(78, "f#5.mp3");
        put(79, "g5.mp3");
        put(80, "g#5.mp3");
        put(81, "a5.mp3");
        put(82, "a#5.mp3");
        put(83, "b5.mp3");
        put(84, "c6.mp3");
    }
    };

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
