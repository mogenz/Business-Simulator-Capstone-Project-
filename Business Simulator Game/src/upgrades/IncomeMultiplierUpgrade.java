package upgrades;
import model.Job;

public class IncomeMultiplierUpgrade implements Upgrade {
    private String name;
    private Job affectedJob;
    private double multiplier;

    public IncomeMultiplierUpgrade(String name, Job affectedJob, double multiplier) {
        this.name = name;
        this.affectedJob = affectedJob;
        this.multiplier = multiplier;
    }

    @Override
    public void apply(Job job) {  // Changed
        job.upgrade();
    }

    @Override
    public boolean isAffordable(double balance) {
        return balance >= getCost();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCost() {
        return 100 * (affectedJob.getLevel() / 2.0);
    }

    @Override
    public Job getAffectedJob() {
        return this.affectedJob;
    }

}
