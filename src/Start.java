import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.student.manage.Student;
import com.student.manage.StudentDAO;

public class Start {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Welcome to the Student Management application");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                System.out.println();
                System.out.println("Press 1 to 'ADD' a new student.");
                System.out.println("Press 2 to 'DELETE' a given student.");
                System.out.println("Press 3 to 'UPDATE' a given student.");
                System.out.println("Press 4 to 'DISPLAY' student(s).");
                System.out.println("Press 5 to EXIT");
                System.out.println();

                int choice = -1;
                try {
                    choice = Integer.parseInt(bufferedReader.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        // add student
                        System.out.println("Enter the Name of the Student:");
                        String studentName = bufferedReader.readLine();

                        System.out.println("Enter the Phone Number of the Student:");
                        String studentPhone = bufferedReader.readLine();

                        System.out.println("Enter the City of Residence of the Student:");
                        String studentCity = bufferedReader.readLine();

                        Student student = new Student(studentName, studentPhone, studentCity);

                        if (StudentDAO.addStudentToDB(student)) {
                            System.out.println("Student added successfully!");
                        } else {
                            System.out.println("Something went wrong. Please try again!");
                        }
                        break;

                    case 2:
                        System.out.println("Enter the ID of the student you want to delete:");
                        int deleteId;
                        try {
                            deleteId = Integer.parseInt(bufferedReader.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID. Please enter a number.");
                            break;
                        }

                        if (StudentDAO.deleteStudentFromDB(deleteId)) {
                            System.out.println("Deleting...");
                            Thread.sleep(500);
                            System.out.println("Student deleted successfully!");
                        } else {
                            System.out.println("Something went wrong. Please try again!");
                        }
                        break;

                    case 3:
                        System.out.println("Enter the ID of the student you want to update:");
                        int updateId;
                        try {
                            updateId = Integer.parseInt(bufferedReader.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID. Please enter a number.");
                            break;
                        }

                        Student existingStudent = StudentDAO.getStudentById(updateId);

                        if (existingStudent != null) {
                            System.out.println("Leave blank to keep existing value.");

                            System.out.print("Updated Name [" + existingStudent.getStudentName() + "]: ");
                            String updatedName = bufferedReader.readLine();
                            if (updatedName.isEmpty()) updatedName = existingStudent.getStudentName();

                            System.out.print("Updated Phone [" + existingStudent.getStudentPhone() + "]: ");
                            String updatedPhone = bufferedReader.readLine();
                            if (updatedPhone.isEmpty()) updatedPhone = existingStudent.getStudentPhone();

                            System.out.print("Updated City [" + existingStudent.getStudentCity() + "]: ");
                            String updatedCity = bufferedReader.readLine();
                            if (updatedCity.isEmpty()) updatedCity = existingStudent.getStudentCity();

                            Student updatedStudent = new Student(updatedName, updatedPhone, updatedCity);
                            if (StudentDAO.updateStudentInDB(updateId, updatedStudent)) {
                                System.out.println("Updating...");
                                Thread.sleep(500);
                                System.out.println("Student updated successfully!");
                            } else {
                                System.out.println("Something went wrong. Please try again!");
                            }
                        } else {
                            System.out.println("No student found with ID: " + updateId);
                        }
                        break;

                    case 4:
                        System.out.println("Enter Student ID to display specific student or 0 to display all:");
                        int viewId;
                        try {
                            viewId = Integer.parseInt(bufferedReader.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID. Please enter a number.");
                            break;
                        }

                        if (viewId == 0) {
                            StudentDAO.showAllStudentsFromDB();
                        } else {
                            Student singleStudent = StudentDAO.getStudentById(viewId);
                            if (singleStudent != null) {
                                System.out.println(singleStudent);
                            } else {
                                System.out.println("Student not found!");
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        Thread.sleep(500);
                        System.out.println("Adios Amigo!");
                        return;

                    default:
                        System.out.println("Please enter a valid option!");
                }
            }
        } finally {
            bufferedReader.close();
        }
    }
}
