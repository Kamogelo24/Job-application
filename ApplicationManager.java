import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationManager {

    private List<Application> applications;

    public ApplicationManager() {
        applications = new ArrayList<>();
    }

    // --- Core CRUD ---

    public void addApplication(Application app) {
        applications.add(app);
    }

    /**
     * Updates the status of an application by its ID.
     * Returns true if the application was found and updated, false otherwise.
     */
    public boolean updateStatus(String applicationId, Status newStatus) {
        for (Application app : applications) {
            if (app.getApplicationId().equalsIgnoreCase(applicationId)) {
                app.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an application by its ID.
     * Returns true if removed, false if not found.
     */
    public boolean deleteApplication(String applicationId) {
        return applications.removeIf(
            app -> app.getApplicationId().equalsIgnoreCase(applicationId)
        );
    }

    // --- Search / Filter ---

    /** Returns all applications matching a given status. */
    public List<Application> filterByStatus(Status status) {
        return applications.stream()
            .filter(app -> app.getStatus() == status)
            .collect(Collectors.toList());
    }

    /** Returns all applications matching a company name (case-insensitive). */
    public List<Application> filterByCompany(String companyName) {
        return applications.stream()
            .filter(app -> app.getCompany().getName().equalsIgnoreCase(companyName))
            .collect(Collectors.toList());
    }

    /** Finds a single application by ID, returns null if not found. */
    public Application findById(String applicationId) {
        return applications.stream()
            .filter(app -> app.getApplicationId().equalsIgnoreCase(applicationId))
            .findFirst()
            .orElse(null);
    }

    // --- Display ---

    public void displayAllApplications() {
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }
        for (Application app : applications) {
            app.displayDetails();
            System.out.println("-------------------------------");
        }
    }

    public void displayFiltered(List<Application> list) {
        if (list.isEmpty()) {
            System.out.println("No matching applications found.");
            return;
        }
        for (Application app : list) {
            app.displayDetails();
            System.out.println("-------------------------------");
        }
    }

    // --- Accessor / Mutator ---

    /** Returns an unmodifiable view — callers cannot tamper with the internal list. */
    public List<Application> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    /**
     * Replaces the internal list entirely (used when loading from file).
     * Named clearly to signal its purpose.
     */
    public void loadApplications(List<Application> applications) {
        this.applications = new ArrayList<>(applications);
    }
}