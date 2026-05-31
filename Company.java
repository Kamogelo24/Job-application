import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    private String companyId;
    private String name;
    private String location;

    public Company(String companyId, String name, String location) {
        this.companyId = companyId;
        this.name      = name;
        this.location  = location;
    }

    // Getters
    public String getCompanyId() { return companyId; }
    public String getName()      { return name; }
    public String getLocation()  { return location; }

    // Setters
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    public void setName(String name)           { this.name = name; }
    public void setLocation(String location)   { this.location = location; }

    // Prevents duplicate Company objects for the same companyId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company other = (Company) o;
        return Objects.equals(companyId, other.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId);
    }

    @Override
    public String toString() {
        return name + " (" + location + ")";
    }
}