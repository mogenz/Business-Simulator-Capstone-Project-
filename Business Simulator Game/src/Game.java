import model.*;
import upgrades.*;
import managers.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {
    private double balance;
    private JobManager jobManager;
    private UpgradeManager upgradeManager;
    private LevelManager levelManager;

    public Game() {
        this.balance = 10;
        this.jobManager = new JobManager();
        this.upgradeManager = new UpgradeManager();
        this.levelManager = new LevelManager();

        // Level 1
        jobManager.addJob("Studenthelper", 1, 1, 10, 1, 1);

        // Level 2
        jobManager.addJob("PersonalAssistant", 1, 5, 75, 2, 2);
        jobManager.addJob("Intern", 1, 7, 150, 2, 3);
        jobManager.addJob("Janitor", 1, 2, 100, 2, 4);

        // Level 3
        jobManager.addJob("Secretary", 1, 10, 1000, 3, 5);
        jobManager.addJob("Accountant", 1, 12, 1250, 3, 6);
        jobManager.addJob("Programmer", 1, 15, 1500, 3, 7);

        // Level 4
        jobManager.addJob("Manager", 1, 20, 10000, 4, 8);
        jobManager.addJob("Lawyer", 1, 25, 15000, 4, 9);
        jobManager.addJob("PersonalDoctor", 1, 27, 18000, 4, 10);

        // Level 5
        jobManager.addJob("CEO", 1, 50, 100000, 5, 11);
        jobManager.addJob("InvestmentBanker", 1, 60, 150000, 5, 12);
        jobManager.addJob("Surgeon", 1, 40, 90000, 5, 13);

        // Level 6
        jobManager.addJob("Investor", 1, 100, 1000000, 6, 14);
        jobManager.addJob("CompanyBought!", 1, 1000000, 1000000006, 6, 15);


        for (Job job : jobManager.getOwnedJobs()) {
            upgradeManager.addUpgrade(job.getName(), job, 1.2);
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Check win condition
            if (levelManager.getCurrentLevel() == levelManager.getMaxLevel()) {
                // Clear console
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("You've won the game!");
                System.out.println("------------------------");
                System.out.println("Press enter to exit the game");
                scanner.nextLine();
                System.exit(0);
            }

            // Display level information
            System.out.println("\n\n---     lvl " + levelManager.getCurrentLevel() + "     ---");
            System.out.println("--- " + levelManager.getCurrentLevelName() + " ---");
            System.out.println("Next lvl: $" + levelManager.getNextLevelCost());

            // Display balance
            System.out.println("\nBalance: $" + String.format("%.2f", Math.max(0, this.balance)));


            // Display owned jobs
            System.out.println("\nOwned jobs:");
            for (Job job : jobManager.getOwnedJobs()) { // income per click max 2 decimals
                System.out.println(job.getName() + " - lvl " + job.getLevel() + " - Income: $" + String.format("%.2f", job.getIncomePerSecond()) + "/click");
            }

            // Menu
            System.out.println("\n1. Upgrade");
            System.out.println("2. Collect");
            System.out.println("3. Level Up");
            System.out.println("4. Hire");
            System.out.println("5. Exit");

            try { // Check for invalid input
                String rawChoice = scanner.nextLine().trim(); // Read the choice as a string

                // Check for special command (Cheat code)
                if (rawChoice.startsWith("balance(") && rawChoice.endsWith(")")) {
                    String amountStr = rawChoice.substring(8, rawChoice.length() - 1); // Extract the number from balance(1000) -> 1000
                    double amount = Double.parseDouble(amountStr); // Convert to double
                    this.balance += amount; // Add to balance
                    if (this.balance < 0) {
                        this.balance = 0;
                    }
                    // Clear console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    System.out.println("Balance updated with: " + amount + "$ - New balance: $" + this.balance);
                    continue; // Skip the rest and go to the next iteration
                }

                int choice = Integer.parseInt(rawChoice); // Convert the choice to integer for menu options

                switch (choice) {
                    case 1: // Upgrading
                        // Clear console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        List<String> uniqueJobNames = new ArrayList<>();
                        for (Job job : jobManager.getOwnedJobs()) {
                            if (!uniqueJobNames.contains(job.getName())) {
                                uniqueJobNames.add(job.getName());
                            }
                        }

                        // Display available upgrades for unique job types
                        System.out.println("Upgrades:");
                        for (int i = 0; i < uniqueJobNames.size(); i++) {
                            System.out.println((i + 1) + ": " + uniqueJobNames.get(i) + " - Cost: $" + String.format("%.2f", jobManager.getUpgradeCost(jobManager.findJobByName(uniqueJobNames.get(i)))));
                        }
                        System.out.println("Which job do you want to upgrade? (Enter the index)");
                        // Check for valid input
                        try {
                            int jobIndexToUpgrade = scanner.nextInt();
                            scanner.nextLine();  // consume the rest of the line

                            if (jobIndexToUpgrade <= 0 || jobIndexToUpgrade > uniqueJobNames.size()) {
                                System.out.println("This job does not exist");
                                break;
                            }

                            String jobNameToUpgrade = uniqueJobNames.get(jobIndexToUpgrade - 1);
                            Upgrade upgradeToBuy = upgradeManager.findUpgradeByJob(jobManager.findJobByName(jobNameToUpgrade));

                            if (upgradeToBuy == null) {
                                System.out.println("This upgrade does not exist");
                                break;
                            }

                            // Clear console
                            System.out.print("\033[H\033[2J");
                            System.out.flush();

                            // Store the upgrade cost before applying the upgrade
                            double upgradeCost = upgradeToBuy.getCost();

                            if (upgradeToBuy == null || !upgradeToBuy.isAffordable(this.balance)) {
                                System.out.println("You can't afford this upgrade\n");
                                break;
                            }

                            // Apply the upgrade to all jobs of this type
                            for (Job job : jobManager.getOwnedJobs()) {
                                if (job.getName().equals(jobNameToUpgrade)) {
                                    upgradeToBuy.apply(job);
                                }
                            }

                            // Deduct the stored upgrade cost from the balance
                            this.balance -= upgradeCost;

                        } catch (InputMismatchException e) {
                            scanner.nextLine();  // consume the incorrect input
                            System.out.println("Invalid input. Please enter a number.");
                        }
                        break;

                    case 2: // Collecting money
                        // Clear console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        double totalIncome = 0;

                        for (Job job : jobManager.getOwnedJobs()) {
                            this.balance += job.getIncomePerSecond();
                            totalIncome += job.getIncomePerSecond();
                        }

                        // Display the total amount of money collected from all the jobs
                        System.out.println("You collected $" + String.format("%.2f", totalIncome));
                        break;

                    case 3: // Leveling up
                        // Clear console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        if (levelManager.canLevelUp(this.balance)) {
                            this.balance -= levelManager.getNextLevelCost();
                            levelManager.levelUp();
                            System.out.println("You've leveled up! Now you're at level " + levelManager.getCurrentLevel());
                        } else {
                            System.out.println("You can't afford to level up yet.");
                        }
                        break;

                    case 4: // Hiring new employees
                        System.out.print("\033[H\033[2J");  // Clear the console
                        System.out.flush();
                        System.out.println("Jobs available for hire:");
                        for (Job job : jobManager.getJobsAvailableAtLevel(levelManager.getCurrentLevel())) {
                            System.out.println(job.getId() + ": " + job.getName() + " - Cost: $" + job.getCost());
                        }

                        System.out.println("Which job do you want to hire for? (type the name and press enter)");
                        String jobNameToHire = scanner.next();
                        System.out.println("How many do you want to hire?");
                        String amountStr = scanner.next();
                        int amountToHire;
                        try {
                            amountToHire = Integer.parseInt(amountStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            continue;
                        }

                        // Check if the job exists
                        if (jobManager.findJobByName(jobNameToHire) == null) {
                            System.out.println("This job does not exist");
                            break;
                        }

                        // Check if the player can afford to hire
                        if (this.balance >= jobManager.findJobByName(jobNameToHire).getCost() * amountToHire) {
                            jobManager.hireMore(jobNameToHire, amountToHire);
                            this.balance -= jobManager.findJobByName(jobNameToHire).getCost() * amountToHire;

                            // Add a new upgrade for the newly hired job type
                            for (int i = 0; i < amountToHire; i++) {
                                upgradeManager.addUpgrade(jobNameToHire, jobManager.findJobByName(jobNameToHire), 1.2);
                            }
                        } else {
                            System.out.println("You can't afford to hire this many.");
                        }
                        break;

                    case 5: // Exit
                        System.exit(0);
                        break;

                    default:
                        // Clear console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("This option does not exist\n");
                        break;
                }
            } catch (NumberFormatException e) {
                // Clear console
                System.out.print("\033[H\033[2J");
                System.out.flush();
                // Handle invalid input
                System.out.println("Invalid input. Please enter a number or a valid command.\n");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
