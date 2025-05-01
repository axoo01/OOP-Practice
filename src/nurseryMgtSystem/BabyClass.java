package nurseryMgtSystem;

public class BabyClass extends NurseryClass {
    private static final int MIN_AGE = 2;
    private static final int MAX_AGE = 3;

    public BabyClass(String classId) {
        super(classId, "Baby Class", 15);
    }

    @Override
    protected void validateTeacherRole(Teacher teacher) {
        if (!teacher.getTeacherRole().equals("Early Childhood Educator")) {
            throw new IllegalArgumentException("Baby Class requires a teacher with role 'Early Childhood Educator'.");
        }
    }

    @Override
    public void enrollStudent(Student student) {
        if (student.getRegisteredClass() != null) {
            throw new IllegalStateException("Student " + student.getStudentName() + " is already enrolled in another class.");
        }
        if (students.size() >= maxCapacity) {
            throw new IllegalStateException("Baby Class has reached its maximum capacity of " + maxCapacity + " students.");
        }
        if (student.getAge() < MIN_AGE || student.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("Student age must be between " + MIN_AGE + " and " + MAX_AGE + " for Baby Class.");
        }
        students.add(student);
        student.setRegisteredClass(this);
    }

    @Override
    public void trackProgress() {
        progressNotes = "Baby Class progress: Students are improving motor skills through play-based learning.";
    }

    @Override
    public void conductActivity(String activityName) {
        activities.add(activityName);
        System.out.println("Baby Class conducted activity: " + activityName + " (focus on motor skills and play).");
    }

    @Override
    public String generateClassReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== Class Report for ").append(className).append(" =====\n");
        report.append("Class Name: ").append(className).append("\n");
        report.append("Assigned Teacher: ").append(assignedTeacher != null ? assignedTeacher.getTeacherName() : "None").append("\n");
        report.append("Number of Enrolled Students: ").append(students.size()).append("\n");
        report.append("Activities Conducted: ").append(activities.isEmpty() ? "None" : String.join(", ", activities)).append("\n");
        report.append("General Progress: ").append(progressNotes).append("\n");
        report.append("======================");
        return report.toString();
    }
}
