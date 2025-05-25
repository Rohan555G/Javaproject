package dao;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import model.Course;

public class EnrollmentDAO {

    private static final String FILE_NAME = "data/enrollment.dat";

    // Save HashMap to file
    public void saveData(HashMap<String, List<Course>> enrollmentMap) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(enrollmentMap);
        }
    }

    // Load HashMap from file
    @SuppressWarnings("unchecked")
    public HashMap<String, List<Course>> loadData() throws IOException, ClassNotFoundException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (HashMap<String, List<Course>>) ois.readObject();
        }
    }
}
