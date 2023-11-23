package managers;

import model.Job;
import java.util.ArrayList;
import java.util.List;

public class JobManager {
    // Private ArrayList of Jobs called availableJobs
    private ArrayList<Job> availableJobs;
    private ArrayList<Job> ownedJobs;

    // Constructor for JobManager, initializes the availableJobs and ownedJobs lists
    public JobManager() {
        availableJobs = new ArrayList<>();
        ownedJobs = new ArrayList<>();
    }

    // Adds a new job to the availableJobs list
    public void addJob(String name, int level, double incomePerSecond, double cost, int levelRequirement, int id) {
        availableJobs.add(new Job(name, level, incomePerSecond, cost, levelRequirement, id));
    }

    // Returns the list of jobs that the user currently owns
    public ArrayList<Job> getOwnedJobs() {
        return ownedJobs;
    }

    // Returns a list of jobs that are available at the current level
    public List<Job> getJobsAvailableAtLevel(int currentLevel) {
        List<Job> jobsAtThisLevel = new ArrayList<>();
        for (Job job : availableJobs) {
            if (job.getLevelRequirement() <= currentLevel) {
                jobsAtThisLevel.add(job);
            }
        }
        return jobsAtThisLevel;
    }

    // Finds a job in the availableJobs list by its name
    public Job findJobByName(String jobName) {
        for (Job job : availableJobs) {
            if (job.getName().equals(jobName)) {
                return job;
            }
        }
        return null;
    }

    // Hires more employees for a specific job and adds them to the ownedJobs list
    public void hireMore(String jobName, int amount) {
        for (int i = 0; i < amount; i++) {
            Job existingJob = findJobByName(jobName);
            if (existingJob != null) {
                Job newJob = new Job(existingJob.getName(), existingJob.getLevel(), existingJob.getIncomePerSecond(), existingJob.getCost(), existingJob.getLevelRequirement(), existingJob.getId());
                ownedJobs.add(newJob);
            }
        }
    }

    // Calculates the cost to upgrade a job
    public double getUpgradeCost(Job job) {
        return job.getCost() * 3;
    }
}