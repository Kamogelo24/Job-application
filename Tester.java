import java.time.LocalDate;
import java.util.List;

public class Tester {

    public static void main(String[] args) {
        System.out.println("=== Job Application Tracker — Test Suite ===\n");

        // ---- Setup ----
        Applicant applicant1 = new Applicant(
            "USR-001", "John Doe", "john@email.com", "resume.pdf", "123-456-7890"
        );
        Applicant applicant2 = new Applicant(
            "USR-002", "Jane Smith", "jane@email.com", "", "098-765-4321"
        );

        Company company1 = new Company("CMP-001", "Tech Corp", "New York");
        Company company2 = new Company("CMP-002", "Design Studio", "Cape Town");

        Application app1 = new Application(
            "APP-001", LocalDate.of(2026, 4, 21), Status.PENDING,
            applicant1, company1, "Software Engineer"
        );
        Application app2 = new Application(
            "APP-002", LocalDate.of(2026, 5, 1), Status.INTERVIEW_SCHEDULED,
            applicant2, company2, "UI Designer"
        );
        Application app3 = new Application(
            "APP-003", LocalDate.of(2026, 5, 10), Status.PENDING,
            applicant1, company2, "Backend Developer"
        );

        // ---- Test getters ----
        System.out.println("--- Getters ---");
        System.out.println("Name     : " + app1.getApplicant().getName());
        System.out.println("Company  : " + app1.getCompany().getName());
        System.out.println("Position : " + app1.getPosition());
        System.out.println("Status   : " + app1.getStatus().displayName());
        System.out.println("Date     : " + app1.getDateApplied());

        // ---- Test setters ----
        System.out.println("\n--- Setters ---");
        app1.setStatus(Status.REVIEWED);
        System.out.println("Updated status: " + app1.getStatus().displayName());

        // ---- Test displayDetails ----
        System.out.println("\n--- displayDetails() ---");
        app1.displayDetails();

        // ---- Test displayInfo (with super chain) ----
        System.out.println("\n--- displayInfo() (super chain) ---");
        applicant1.displayInfo();

        // ---- Test ApplicationManager ----
        System.out.println("\n--- ApplicationManager ---");
        ApplicationManager manager = new ApplicationManager();
        manager.addApplication(app1);
        manager.addApplication(app2);
        manager.addApplication(app3);

        System.out.println("Total applications: " + manager.getApplications().size());

        // ---- Test filterByStatus ----
        System.out.println("\n--- Filter by PENDING ---");
        List<Application> pending = manager.filterByStatus(Status.PENDING);
        manager.displayFiltered(pending);

        // ---- Test filterByCompany ----
        System.out.println("--- Filter by company: Design Studio ---");
        List<Application> byCompany = manager.filterByCompany("Design Studio");
        manager.displayFiltered(byCompany);

        // ---- Test updateStatus ----
        System.out.println("--- Update status of APP-003 to ACCEPTED ---");
        boolean updated = manager.updateStatus("APP-003", Status.ACCEPTED);
        System.out.println("Updated: " + updated);
        System.out.println("New status: " + manager.findById("APP-003").getStatus().displayName());

        // ---- Test deleteApplication ----
        System.out.println("\n--- Delete APP-002 ---");
        boolean deleted = manager.deleteApplication("APP-002");
        System.out.println("Deleted: " + deleted);
        System.out.println("Remaining: " + manager.getApplications().size());

        // ---- Test Company equals ----
        System.out.println("\n--- Company equals() ---");
        Company dupCompany = new Company("CMP-001", "Tech Corp", "New York");
        System.out.println("Same company ID equals: " + company1.equals(dupCompany));

        // ---- Test FileHandler (save + reload) ----
        System.out.println("\n--- FileHandler (save + reload) ---");
        FileHandler.saveToFile("test_output.dat", manager.getApplications());
        ApplicationManager reloaded = new ApplicationManager();
        reloaded.loadApplications(FileHandler.loadFromFile("test_output.dat"));
        System.out.println("Reloaded application count: " + reloaded.getApplications().size());
        reloaded.displayAllApplications();

        System.out.println("=== All tests complete ===");
    }
}