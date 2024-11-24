import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Student {
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

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private java.util.List<Student> students;
    private final String fileName = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentsFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudentsToFile();
    }

    public boolean removeStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                students.remove(student);
                saveStudentsToFile();
                return true;
            }
        }
        return false;
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public java.util.List<Student> getAllStudents() {
        return students;
    }

    private void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student student : students) {
                writer.write(student.getRollNumber() + "," + student.getName() + "," + student.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }

    private void loadStudentsFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    int rollNumber = Integer.parseInt(data[0]);
                    String name = data[1];
                    String grade = data[2];
                    students.add(new Student(name, rollNumber, grade));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }
    }
}

public class StudentManagementGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static StudentManagementSystem sms = new StudentManagementSystem();

    private JTextField nameField, rollField, gradeField;
    private JTextArea displayArea;
    private JButton addButton, removeButton, searchButton, displayButton;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.setBackground(new Color(255, 255, 255)); // White background for the input panel

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Roll Number:"));
        rollField = new JTextField();
        inputPanel.add(rollField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        // Buttons
        addButton = new JButton("Add Student");
        removeButton = new JButton("Remove Student");
        searchButton = new JButton("Search Student");
        displayButton = new JButton("Display All Students");

        // Set background and text color for buttons
        addButton.setBackground(Color.GREEN);
        removeButton.setBackground(Color.RED);
        searchButton.setBackground(Color.YELLOW);
        displayButton.setBackground(Color.CYAN);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(255, 255, 255)); // White background for button panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(displayButton);

        // Display Area (Text Area)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(Color.WHITE); // Background color for the text area
        displayArea.setForeground(Color.BLACK); // Text color for the text area
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Adding components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStudent();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
            }
        });
    }

    private void addStudent() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid roll number. Please enter a valid number.");
            return;
        }

        String grade = gradeField.getText();
        if (grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Grade cannot be empty.");
            return;
        }

        if (sms.searchStudent(rollNumber) != null) {
            JOptionPane.showMessageDialog(this, "Student with this roll number already exists.");
            return;
        }

        Student student = new Student(name, rollNumber, grade);
        sms.addStudent(student);
        JOptionPane.showMessageDialog(this, "Student added successfully.");
        clearFields();
    }

    private void removeStudent() {
        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid roll number. Please enter a valid number.");
            return;
        }

        if (sms.removeStudent(rollNumber)) {
            JOptionPane.showMessageDialog(this, "Student removed successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.");
        }
    }

    private void searchStudent() {
        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid roll number. Please enter a valid number.");
            return;
        }

        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            displayArea.setText(student.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.");
        }
    }

   

    private void displayAllStudents() {
        java.util.List<Student> students = sms.getAllStudents();
        if (students.isEmpty()) {
            displayArea.setText("No students found.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Student student : students) {
                sb.append(student).append("\n");
            }
            displayArea.setText(sb.toString());
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        gradeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementGUI().setVisible(true);
            }
        });
    }
}