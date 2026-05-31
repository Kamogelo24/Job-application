import java.io.Serializable;

public class Applicant extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resumePath;
    private String phoneNumber;

    public Applicant(String userId, String name, String email, String resumePath, String phoneNumber) {
        super(userId, name, email);
        this.resumePath  = resumePath;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getResumePath()  { return resumePath; }
    public String getPhoneNumber() { return phoneNumber; }

    // Setters
    public void setResumePath(String resumePath)   { this.resumePath = resumePath; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public void displayInfo() {
        // Chain to parent so email / userId are also printed
        super.displayInfo();
        System.out.println("  Phone: " + phoneNumber);
        if (!resumePath.isEmpty()) {
            System.out.println("  Resume: " + resumePath);
        }
    }
}