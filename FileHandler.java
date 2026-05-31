import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private FileHandler() {
        // Utility class — no instances needed
    }

    /**
     * Serializes the application list to a binary file.
     * All classes in the graph must implement Serializable (Application,
     * Applicant, Company, JobApplication, User, Status).
     */
    public static void saveToFile(String filename, List<Application> apps) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(new ArrayList<>(apps));
            System.out.println("Data saved successfully to \"" + filename + "\".");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Deserializes the application list from a binary file.
     * Returns an empty list if the file does not exist yet.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Application> loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();  // First run — no file yet
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Application>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}