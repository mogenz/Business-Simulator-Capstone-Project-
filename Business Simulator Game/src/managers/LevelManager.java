package managers;

import java.util.ArrayList;

public class LevelManager {
    private int currentLevel;
    private ArrayList<String> levelNames;
    private ArrayList<Double> levelUpCosts;

    public LevelManager() {
        this.currentLevel = 1;
        this.levelNames = new ArrayList<>();
        this.levelUpCosts = new ArrayList<>();

        // Initialize the level names
        levelNames.add("Home Office");
        levelNames.add("Startup");
        levelNames.add("Small Business");
        levelNames.add("Medium Business");
        levelNames.add("Large Business");
        levelNames.add("Enterprise");
        levelNames.add("Conglomerate (last level)");
        // ... add more levels

        // Initialize the level up costs
        levelUpCosts.add(1000.0);  // Cost to go from level 1 to 2
        levelUpCosts.add(3000.0);  // Cost to go from level 2 to 3
        levelUpCosts.add(5000.0); // Cost to go from level 3 to 4
        levelUpCosts.add(10000.0); // Cost to go from level 4 to 5
        levelUpCosts.add(20000.0); // Cost to go from level 5 to 6
        levelUpCosts.add(50000.0); // Cost to go from level 6 to 7
        levelUpCosts.add(100000.0); // Cost to go from level 7 to win
        levelUpCosts.add(1000000.0);
        // ... add more costs
    }

    // Get the name of the current level
    public String getCurrentLevelName() {
        return levelNames.get(currentLevel - 1);
    }

    // Get the cost for the next level
    public double getNextLevelCost() {
        if (currentLevel >= levelUpCosts.size()) {
            return -1;  // Max level reached
        }
        return levelUpCosts.get(currentLevel - 1);
    }

    // Check if you can level up based on the current balance
    public boolean canLevelUp(double balance) {
        double nextLevelCost = getNextLevelCost();
        return nextLevelCost != -1 && balance >= nextLevelCost;
    }

    // Level up to the next level
    public void levelUp() {
        if (currentLevel < levelUpCosts.size()) {
            currentLevel++;
        }
    }

    // Get the current level, used in the Game class
    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return levelUpCosts.size();
    }
}
