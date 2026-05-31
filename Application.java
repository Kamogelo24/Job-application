import java.io.Serializable;
import java.time.LocalDate;

public class Application extends JobApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Applicant applicant; // identity fields are final — they should never change
    private final Company   company;
    private String          position;

    public Application(String applicationId, LocalDate dateApplied, Status status,
                       Applicant applicant, Company company, String position) {
        super(applicationId, dateApplied, status);
        this.applicant = applicant;
        this.company   = company;
        this.position  = position;
    }

    // Getters
    public Applicant getApplicant() { return applicant; }
    public Company   getCompany()   { return company; }
    public String    getPosition()  { return position; }

    // Only mutable fields get setters (status is in parent, position can change)
    public void setPosition(String position) { this.position = position; }

    @Override
    public void displayDetails() {
        System.out.println("Application ID : " + getApplicationId());
        System.out.println("Position       : " + position);
        System.out.println("Applicant      : " + applicant.getName()
                                               + " | " + applicant.getPhoneNumber()
                                               + " | " + applicant.getEmail());
        System.out.println("Company        : " + company.getName()
                                               + " | " + company.getLocation());
        System.out.println("Status         : " + getStatus().displayName());
        System.out.println("Date Applied   : " + getDateApplied());
    }
}