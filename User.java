import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String name;
    private String email;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName()   { return name; }
    public String getEmail()  { return email; }

    // Setters
    public void setUserId(String userId) { this.userId = userId; }
    public void setName(String name)     { this.name = name; }
    public void setEmail(String email)   { this.email = email; }

    public void displayInfo() {
        System.out.println("User: " + name + " (" + email + ")");
    }
}