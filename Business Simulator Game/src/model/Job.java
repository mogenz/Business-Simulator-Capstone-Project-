package model;

public class Job {
    private String name;
    private int level;
    private double incomePerSecond;
    private double cost;
    private int levelRequirement;
    private int id;


    public Job(String name, int level, double incomePerSecond, double cost, int levelRequirement, int id) {
        this.name = name;
        this.level = level;
        this.incomePerSecond = incomePerSecond;
        this.cost = cost;
        this.levelRequirement = levelRequirement;
        this.id = id;
    }

    public void upgrade() {
        this.level++;
        this.incomePerSecond *= 1.2; // This can be adjusted as needed
    }

    // Getters and Setters
    public String getName() { return name; }
    public int getLevel() { return level; }
    public double getIncomePerSecond() { return incomePerSecond; }
    public double getCost() { return cost; }
    public int getLevelRequirement() { return levelRequirement; }
    public int getId() { return id; }
}
