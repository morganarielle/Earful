package edu.neu.earful;

public class IntervalCard {
    private Interval interval;

    public IntervalCard(Interval interval) {
        this.interval = interval;
    }

    public Interval getInterval() {
        return this.interval;
    }

    public static String getIntervalDisplayText(Interval interval) {
        switch (interval) {
            case perfect1:
                return "P1";
            case minor2:
                return "m2";
            case major2:
                return "M2";
            case minor3:
                return "m3";
            case major3:
                return "M3";
            case perfect4:
                return "P4";
            case minor5:
                return "m5";
            case perfect5:
                return "P5";
            case minor6:
                return "m6";
            case major6:
                return "M6";
            case minor7:
                return "m7";
            case major7:
                return "M7";
            case perfect8:
                return "P8";
            default:
                return "?";
        }
    }
}
