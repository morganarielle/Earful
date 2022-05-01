package edu.neu.earful.training;

public enum DifficultyLevel {
    LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5;

    public static int pointsNeededToReachLevel(DifficultyLevel level) {
        switch(level) {
            case LEVEL2:
                return 100;
            case LEVEL3:
                return 300;
            case LEVEL4:
                return 600;
            case LEVEL5:
                return 1000;
            default:
                return 0;
        }
    }
}
