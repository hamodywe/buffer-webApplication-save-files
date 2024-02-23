package controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import models.Student;
import models.School;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class HomeView implements Serializable {
    private List<Student> students = new ArrayList<>();
    private String name;
    private String age;

    @Inject
    private School school;

    // DataInitializer class should be a separate class, not inner class
    public static class DataInitializer {
        private static final String FILE_PATH = "C:\\Users\\S-W-T\\IdeaProjects\\demo7\\txt\\Student.txt";

        @Inject
        private School school;

        @PostConstruct
        public void loadDataFromTextFile() {
            createTextFileIfNotExists(FILE_PATH); // Create text file if not exists
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        school.getStudents().add(new Student(parts[0], parts[1]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Method to create a new text file if it doesn't exist
        private void createTextFileIfNotExists(String filePath) {
            File file = new File(filePath);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Getter and setter for students list
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for age
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    // Method to add a student to the list
    public void addStudent() {
        if (isValidInput(name) && isValidInput(age)) {
            students.add(new Student(name, age));
            name = "";
            age = "";
            saveToTextFile(); // Save to text file after adding a student
        } else {
            // إشعار بأن الإدخال غير صالح
        }
    }

    // Method to remove a student from the list
    public void removeStudent(Student student) {
        students.remove(student);
        saveToTextFile(); // Save to text file after removing a student
    }

    // Method to save the list of students to a text file
    public void saveToTextFile() {
        String filePath = "C:\\Users\\S-W-T\\IdeaProjects\\demo7\\txt\\Student.txt"; // تحديد المسار الكامل للملف النصي
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Student student : students) {
                writer.println(student.getName() + "," + student.getAge());
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    // تحقق من صحة الإدخال
    private boolean isValidInput(String input) {
        return (input != null && !input.trim().isEmpty());
    }

    // Method to edit a student
    public String editStudent(Student student) {
        // Add your logic here to handle editing a student
        return "editStudent"; // Redirect to the edit student page
    }

    // Method to view the text file content
    public String viewTextFileContent() {
        // Add your logic here to view the text file content
        return "viewTextFile"; // Redirect to the view text file page
    }

    // Method to read the text file content and load it into students list
    public void readTextFileContent() {
        students.clear(); // Clear existing student list
        String filePath = "C:\\Users\\S-W-T\\IdeaProjects\\demo7\\txt\\Student.txt"; // تحديد المسار الكامل للملف النصي
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    students.add(new Student(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
