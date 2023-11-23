package model;

import model.Job;
import java.util.ArrayList;

public class Level {
    private String name;
    private double nextLevelCost;
    private ArrayList<Job> availableJobs;

    public Level(String name, double nextLevelCost) {
        this.name = name;
        this.nextLevelCost = nextLevelCost;
        this.availableJobs = new ArrayList<>();
    }
}
