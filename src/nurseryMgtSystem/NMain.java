package nurseryMgtSystem;

import java.util.*;

public class NMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<NurseryClass> classes = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final Set<String> studentIds = new HashSet<>();

    public static void main(String[] args) {
        initializeData();
        while (true) {
            System.out.println("\nNursery School Management System");
            System.out.println("1. Assign Teacher to Class");
            System.out.println("2. Enroll Student in Class");
            System.out.println("3. Conduct Activity");
            System.out.println("4. Track Progress");
            System.out.println("5. Generate Class Report");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    assignTeacherToClass();
                    break;
                case "2":
                    enrollStudentInClass();
                    break;
                case "3":
                    conductActivity();
                    break;
                case "4":
                    trackProgress();
                    break;
                case "5":
                    generateClassReport();
                    break;
                case "6":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1-6.");
            }
        }
    }

    private static void initializeData() {

        classes.add(new BabyClass("BC1"));
        classes.add(new MiddleClass("MC1"));
        classes.add(new TopClass("TC1"));

        teachers.add(new Teacher("T1", "Alice", "Early Childhood Educator"));
        teachers.add(new Teacher("T2", "Bob", "General Educator"));
        teachers.add(new Teacher("T3", "Clara", "Assistant"));

        students.add(new Student("S1", "Emma", 2, "Parent1"));
        students.add(new Student("S2", "Liam", 3, "Parent2"));
        students.add(new Student("S3", "Olivia", 4, "Parent3"));
        students.add(new Student("S4", "Noah", 5, "Parent4"));
        studentIds.add("S1");
        studentIds.add("S2");
        studentIds.add("S3");
        studentIds.add("S4");
    }

    private static void assignTeacherToClass() {
        NurseryClass nurseryClass = selectClass();
        if (nurseryClass == null) return;

        boolean teacherAssigned = false;
        while (!teacherAssigned) {
            System.out.println("\nAvailable Teachers:");
            boolean hasAvailableTeachers = false;
            for (int i = 0; i < teachers.size(); i++) {
                Teacher t = teachers.get(i);
                if (t.getAssignedClass() == null) {
                    System.out.println((i + 1) + ". " + t.getTeacherName() + " (" + t.getTeacherRole() + ")");
                    hasAvailableTeachers = true;
                }
            }
            if (!hasAvailableTeachers) {
                System.out.println("No available teachers to assign.");
                return;
            }

            System.out.print("Select a teacher (enter number, or 0 to cancel): ");
            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                System.out.println("Assignment cancelled.");
                return;
            }

            int teacherIndex;
            try {
                teacherIndex = Integer.parseInt(choice) - 1;
                if (teacherIndex < 0 || teacherIndex >= teachers.size() || teachers.get(teacherIndex).getAssignedClass() != null) {
                    System.out.println("Invalid teacher selection. Please try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            Teacher teacher = teachers.get(teacherIndex);
            try {
                nurseryClass.assignTeacher(teacher);
                System.out.println("Teacher " + teacher.getTeacherName() + " assigned to " + nurseryClass.getClassName() + ".");
                teacherAssigned = true;
            } catch (IllegalStateException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please select a different teacher.");
            }
        }
    }

    private static void enrollStudentInClass() {
        NurseryClass nurseryClass = selectClass();
        if (nurseryClass == null) return;

        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        if (studentIds.contains(studentId)) {
            System.out.println("Error: Student ID " + studentId + " already exists. Please use a unique ID.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();
        if (studentName.trim().isEmpty()) {
            System.out.println("Error: Student name cannot be empty.");
            return;
        }

        Integer age = null;
        while (age == null) {
            System.out.print("Enter Student Age: ");
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    System.out.println("Age must be greater than 0. Please try again.");
                    age = null;
                } else {
                    if (nurseryClass instanceof BabyClass && (age < 2 || age > 3)) {
                        System.out.println("Student age must be between 2 and 3 for Baby Class. Please try again.");
                        age = null;
                    } else if (nurseryClass instanceof MiddleClass && (age < 3 || age > 4)) {
                        System.out.println("Student age must be between 3 and 4 for Middle Class. Please try again.");
                        age = null;
                    } else if (nurseryClass instanceof TopClass && (age < 4 || age > 5)) {
                        System.out.println("Student age must be between 4 and 5 for Top Class. Please try again.");
                        age = null;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid integer.");
            }
        }

        System.out.print("Enter Guardian Name: ");
        String guardianName = scanner.nextLine();
        if (guardianName.trim().isEmpty()) {
            System.out.println("Error: Guardian name cannot be empty.");
            return;
        }

        Student student = new Student(studentId, studentName, age, guardianName);
        boolean enrolled = false;
        while (!enrolled) {
            try {
                nurseryClass.enrollStudent(student);
                studentIds.add(studentId);
                students.add(student);
                enrolled = true;
                System.out.println("Student " + studentName + " enrolled in " + nurseryClass.getClassName() + ".");
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
                if (e.getMessage().contains("reached its maximum capacity")) {
                    System.out.println("Please select a different class.");
                    nurseryClass = selectClass();
                    if (nurseryClass == null) return;
                } else if (e.getMessage().contains("already enrolled in another class")) {
                    return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Unexpected error: " + e.getMessage());
                return;
            }
        }
    }

    private static void conductActivity() {
        NurseryClass nurseryClass = selectClass();
        if (nurseryClass == null) return;

        System.out.print("Enter Activity Name (e.g., Singing, Painting, Storytelling): ");
        String activityName = scanner.nextLine();
        if (activityName.trim().isEmpty()) {
            System.out.println("Error: Activity name cannot be empty.");
            return;
        }

        nurseryClass.conductActivity(activityName);
    }

    private static void trackProgress() {
        NurseryClass nurseryClass = selectClass();
        if (nurseryClass == null) return;

        nurseryClass.trackProgress();
        System.out.println("Progress tracked for " + nurseryClass.getClassName() + ".");
    }

    private static void generateClassReport() {
        NurseryClass nurseryClass = selectClass();
        if (nurseryClass == null) return;

        System.out.println(nurseryClass.generateClassReport());
    }

    private static NurseryClass selectClass() {
        System.out.println("\nAvailable Classes:");
        for (int i = 0; i < classes.size(); i++) {
            NurseryClass c = classes.get(i);
            System.out.println((i + 1) + ". " + c.getClassName() + " (ID: " + c.getClassId() + ")");
        }
        System.out.print("Select a class (enter number, or 0 to cancel): ");
        String choice = scanner.nextLine();

        if (choice.equals("0")) {
            System.out.println("Selection cancelled.");
            return null;
        }

        int classIndex;
        try {
            classIndex = Integer.parseInt(choice) - 1;
            if (classIndex < 0 || classIndex >= classes.size()) {
                System.out.println("Invalid class selection.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return null;
        }

        return classes.get(classIndex);
    }
}