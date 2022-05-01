package edu.neu.earful;

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

    public static DifficultyLevel highestLevel(int points) {
        DifficultyLevel maxLevel = LEVEL1;
        for (DifficultyLevel level: DifficultyLevel.values()) {
            int minPoints = pointsNeededToReachLevel(level);
            if (points >= minPoints) {
                maxLevel = level;
            }
        }
        return maxLevel;
    }
}
