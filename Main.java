import model.Course;
import service.EnrollmentService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static EnrollmentService enrollmentService = new EnrollmentService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printWelcome();
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Select option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> handleAddCourse();
                case "2" -> handleDropCourse();
                case "3" -> handleSearchCourses();
                case "4" -> {
                    System.out.println("Exiting program. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printWelcome() {
        System.out.println("=== Student Course Enrollment System ===");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Course for Student");
        System.out.println("2. Drop Course for Student");
        System.out.println("3. Search Courses by Student ID");
        System.out.println("4. Exit");
    }

    private static void handleAddCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine().trim();

        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine().trim();

        Course course = new Course(courseCode, courseName);
        boolean added = enrollmentService.addCourse(studentId, course);

        if (added) {
            System.out.println("Course added successfully.");
        } else {
            System.out.println("Student already enrolled in this course.");
        }
    }

    private static void handleDropCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();

        System.out.print("Enter Course Code to drop: ");
        String courseCode = scanner.nextLine().trim();

        boolean dropped = enrollmentService.dropCourse(studentId, courseCode);
        if (dropped) {
            System.out.println("Course dropped successfully.");
        } else {
            System.out.println("Course not found for this student.");
        }
    }

    private static void handleSearchCourses() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();

        List<Course> courses = enrollmentService.searchCourses(studentId);
        if (courses == null || courses.isEmpty()) {
            System.out.println("No courses found for student ID: " + studentId);
        } else {
            System.out.println("Courses enrolled by " + studentId + ":");
            for (Course c : courses) {
                System.out.println("- " + c);
            }
        }
    }
}
