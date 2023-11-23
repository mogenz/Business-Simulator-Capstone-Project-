//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package upgrades;

import model.Job;

public interface Upgrade {
    void apply(Job var1);

    boolean isAffordable(double var1);

    String getName();

    double getCost();

    Job getAffectedJob();
}
