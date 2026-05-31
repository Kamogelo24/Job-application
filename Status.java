import java.io.Serializable;

public enum Status implements Serializable {
    PENDING,
    REVIEWED,
    INTERVIEW_SCHEDULED,
    REJECTED,
    ACCEPTED;

    // Friendly display label used in menus and output
    public String displayName() {
        return name().charAt(0) + name().substring(1).toLowerCase().replace('_', ' ');
    }
}