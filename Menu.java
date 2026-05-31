import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Menu {

    private static final String DATA_FILE = "applications.dat";

    private final ApplicationManager manager;
    private final Scanner scanner;

    public Menu() {
        manager = new ApplicationManager();
        scanner = new Scanner(System.in);

        // Load any previously saved data on startup
        manager.loadApplications(FileHandler.loadFromFile(DATA_FILE));
    }

    public void start() {
        int choice = -1;

        do {
            printMainMenu();
            choice = readInt();

            switch (choice) {
                case 1: addApplication();   break;
                case 2: viewAll();          break;
                case 3: updateStatus();     break;
                case 4: deleteApplication(); break;
                case 5: filterMenu();       break;
                case 6:
                    FileHandler.saveToFile(DATA_FILE, manager.getApplications());
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1–6.");
            }

        } while (choice != 6);

        scanner.close();
    }

    // ------------------------------------------------------------------ //
    //  Menu screens                                                        //
    // ------------------------------------------------------------------ //

    private void printMainMenu() {
        System.out.println("\n=================================");
        System.out.println("  Job Application Tracker");
        System.out.println("=================================");
        System.out.println("1. Add application");
        System.out.println("2. View all applications");
        System.out.println("3. Update application status");
        System.out.println("4. Delete application");
        System.out.println("5. Filter applications");
        System.out.println("6. Save & Exit");
        System.out.print("Choice: ");
    }

    // ------------------------------------------------------------------ //
    //  Feature implementations                                             //
    // ------------------------------------------------------------------ //

    private void addApplication() {
        System.out.println("\n--- Add New Application ---");

        System.out.print("Your name       : ");
        String name = scanner.nextLine().trim();

        System.out.print("Your email      : ");
        String email = scanner.nextLine().trim();

        System.out.print("Your phone      : ");
        String phone = scanner.nextLine().trim();

        System.out.print("Resume path     : ");
        String resume = scanner.nextLine().trim();

        System.out.print("Company name    : ");
        String companyName = scanner.nextLine().trim();

        System.out.print("Company location: ");
        String location = scanner.nextLine().trim();

        System.out.print("Position applied: ");
        String position = scanner.nextLine().trim();

        // Auto-generate IDs so users never have to type them
        String appId     = "APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String applicantId = "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String companyId   = "CMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Applicant applicant = new Applicant(applicantId, name, email, resume, phone);
        Company   company   = new Company(companyId, companyName, location);
        Application app     = new Application(appId, LocalDate.now(), Status.PENDING,
                                              applicant, company, position);

        manager.addApplication(app);

        System.out.println("\nApplication added successfully!");
        System.out.println("  ID     : " + appId);
        System.out.println("  Status : " + Status.PENDING.displayName());
        System.out.println("  Date   : " + LocalDate.now());
    }

    private void viewAll() {
        System.out.println("\n--- All Applications ---");
        manager.displayAllApplications();
    }

    private void updateStatus() {
        System.out.println("\n--- Update Application Status ---");
        System.out.print("Enter application ID: ");
        String id = scanner.nextLine().trim();

        Application app = manager.findById(id);
        if (app == null) {
            System.out.println("Application not found.");
            return;
        }

        System.out.println("Current status: " + app.getStatus().displayName());
        System.out.println("Choose new status:");

        Status[] statuses = Status.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println("  " + (i + 1) + ". " + statuses[i].displayName());
        }
        System.out.print("Choice: ");
        int choice = readInt();

        if (choice < 1 || choice > statuses.length) {
            System.out.println("Invalid choice.");
            return;
        }

        Status newStatus = statuses[choice - 1];
        manager.updateStatus(id, newStatus);
        System.out.println("Status updated to: " + newStatus.displayName());
    }

    private void deleteApplication() {
        System.out.println("\n--- Delete Application ---");
        System.out.print("Enter application ID to delete: ");
        String id = scanner.nextLine().trim();

        boolean removed = manager.deleteApplication(id);
        System.out.println(removed
            ? "Application " + id + " deleted."
            : "Application not found.");
    }

    private void filterMenu() {
        System.out.println("\n--- Filter Applications ---");
        System.out.println("1. Filter by status");
        System.out.println("2. Filter by company");
        System.out.print("Choice: ");
        int choice = readInt();

        switch (choice) {
            case 1:
                filterByStatus();
                break;
            case 2:
                filterByCompany();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void filterByStatus() {
        System.out.println("Choose status:");
        Status[] statuses = Status.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println("  " + (i + 1) + ". " + statuses[i].displayName());
        }
        System.out.print("Choice: ");
        int choice = readInt();

        if (choice < 1 || choice > statuses.length) {
            System.out.println("Invalid choice.");
            return;
        }

        Status selected = statuses[choice - 1];
        List<Application> results = manager.filterByStatus(selected);
        System.out.println("\n--- Applications with status: " + selected.displayName() + " ---");
        manager.displayFiltered(results);
    }

    private void filterByCompany() {
        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine().trim();
        List<Application> results = manager.filterByCompany(companyName);
        System.out.println("\n--- Applications for company: " + companyName + " ---");
        manager.displayFiltered(results);
    }

    // ------------------------------------------------------------------ //
    //  Input helper — prevents crash on non-integer input                 //
    // ------------------------------------------------------------------ //

    private int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}