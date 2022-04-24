package edu.neu.earful;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class IntervalCard {
    private final Interval interval;

    public IntervalCard(Interval interval) {
        this.interval = interval;
    }

    public Interval getInterval() {
        return this.interval;
    }

    public static Map<Interval, String> intervalDisplayedText = new HashMap<Interval, String>() {{
        put(Interval.perfect1, "P1");
        put(Interval.minor2, "m2");
        put(Interval.major2, "M2");
        put(Interval.minor3, "m3");
        put(Interval.major3, "M3");
        put(Interval.perfect4, "P4");
        put(Interval.minor5, "m5");
        put(Interval.perfect5, "P5");
        put(Interval.minor6, "m6");
        put(Interval.major6, "M6");
        put(Interval.minor7, "m7");
        put(Interval.major7, "M7");
        put(Interval.perfect8, "P8");
    }
    };

    public static String getIntervalDisplayText(Interval interval) {
        return intervalDisplayedText.get(interval);
    }

    public static Interval getIntervalFromDisplayText(String text) {
        for (Map.Entry<Interval,String> entry : intervalDisplayedText.entrySet()) {
            if (text.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        Log.v("TAG", "Display text not associated with any given interval");
        return Interval.perfect1;
    }
}
