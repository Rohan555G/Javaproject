package service;

import dao.EnrollmentDAO;
import model.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Service {
    private HashMap<String, List<Course>> enrollmentMap;
    private EnrollmentDAO dao;

    public EnrollmentService() {
        dao = new EnrollmentDAO();
        try {
            enrollmentMap = dao.loadData();
        } catch (IOException | ClassNotFoundException e) {
            enrollmentMap = new HashMap<>();
        }
    }

    public boolean addCourse(String studentId, Course course) {
        List<Course> courses = enrollmentMap.getOrDefault(studentId, new ArrayList<>());
        // Prevent duplicate courses
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                return false; // Already enrolled
            }
        }
        courses.add(course);
        enrollmentMap.put(studentId, courses);
        saveData();
        return true;
    }

    public boolean dropCourse(String studentId, String courseCode) {
        List<Course> courses = enrollmentMap.get(studentId);
        if (courses == null) return false;

        boolean removed = courses.removeIf(c -> c.getCourseCode().equalsIgnoreCase(courseCode));
        if (removed) {
            if (courses.isEmpty()) {
                enrollmentMap.remove(studentId);
            } else {
                enrollmentMap.put(studentId, courses);
            }
            saveData();
        }
        return removed;
    }

    public List<Course> searchCourses(String studentId) {
        return enrollmentMap.get(studentId);
    }

    private void saveData() {
        try {
            dao.saveData(enrollmentMap);
        } catch (IOException e) {
            System.err.println("Error saving enrollment data: " + e.getMessage());
        }
    }
}
