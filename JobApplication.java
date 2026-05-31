import java.io.Serializable;
import java.time.LocalDate;

public abstract class JobApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    private String     applicationId;
    private LocalDate  dateApplied;   // was String — now properly typed
    private Status     status;

    public JobApplication(String applicationId, LocalDate dateApplied, Status status) {
        this.applicationId = applicationId;
        this.dateApplied   = dateApplied;
        this.status        = status;
    }

    // Getters
    public String    getApplicationId() { return applicationId; }
    public LocalDate getDateApplied()   { return dateApplied; }
    public Status    getStatus()        { return status; }

    // Setters
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
    public void setDateApplied(LocalDate dateApplied)  { this.dateApplied = dateApplied; }
    public void setStatus(Status status)               { this.status = status; }

    // Must be implemented by every concrete subclass
    public abstract void displayDetails();
}