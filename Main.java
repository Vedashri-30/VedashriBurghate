import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Student class
class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

// StudentManagementSystem class
class StudentManagementSystem {
    private ArrayList<Student> students;
    private final String filename = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentsFromFile();
    }

    // Method to add a student
    public void addStudent(Student student) {
        students.add(student);
        saveStudentsToFile();
        System.out.println("Student added successfully.");
    }

    // Method to remove a student
    public void removeStudent(int rollNumber) {
        Student studentToRemove = searchStudent(rollNumber);
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            saveStudentsToFile();
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Method to search for a student
    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    // Method to display all students
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Method to save student data to a file
    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students to file.");
        }
    }

    // Method to load student data from a file
    private void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved student data found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data from file.");
        }
    }
}

// Main method with user interface
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManagementSystem sms = new StudentManagementSystem();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    // Method to add a student
    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter roll number: ");
        int rollNumber = getIntInput();

        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();

        Student student = new Student(name, rollNumber, grade);
        sms.addStudent(student);
    }

    // Method to remove a student
    private static void removeStudent() {
        System.out.print("Enter roll number of the student to remove: ");
        int rollNumber = getIntInput();
        sms.removeStudent(rollNumber);
    }

    // Method to search for a student
    private static void searchStudent() {
        System.out.print("Enter roll number of the student to search: ");
        int rollNumber = getIntInput();
        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    // Method to handle integer input with validation
    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}