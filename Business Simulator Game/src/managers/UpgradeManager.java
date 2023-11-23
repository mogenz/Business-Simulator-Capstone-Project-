package managers;

import upgrades.Upgrade;
import upgrades.IncomeMultiplierUpgrade;
import model.Job;
import java.util.ArrayList;

public class UpgradeManager {
    private ArrayList<Upgrade> upgrades;

    public UpgradeManager() {
        upgrades = new ArrayList<>();
    }

    public void addUpgrade(String name, Job affectedJob, double multiplier) {
        upgrades.add(new IncomeMultiplierUpgrade(name, affectedJob, multiplier));
    }

    public Upgrade findUpgradeByJob(Job job) {
        for (Upgrade upgrade : upgrades) {
            if (upgrade.getAffectedJob().equals(job)) {
                return upgrade;
            }
        }
        System.out.println("No upgrade available for this job.");  // New line
        return null;
    }

}
