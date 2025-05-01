package nurseryMgtSystem;

public class MiddleClass extends NurseryClass {
    private static final int MIN_AGE = 3;
    private static final int MAX_AGE = 4;

    public MiddleClass(String classId) {
        super(classId, "Middle Class", 20);
    }

    @Override
    protected void validateTeacherRole(Teacher teacher) {

    }

    @Override
    public void enrollStudent(Student student) {
        if (student.getRegisteredClass() != null) {
            throw new IllegalStateException("Student " + student.getStudentName() + " is already enrolled in another class.");
        }
        if (students.size() >= maxCapacity) {
            throw new IllegalStateException("Middle Class has reached its maximum capacity of " + maxCapacity + " students.");
        }
        if (student.getAge() < MIN_AGE || student.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("Student age must be between " + MIN_AGE + " and " + MAX_AGE + " for Middle Class.");
        }
        students.add(student);
        student.setRegisteredClass(this);
    }

    @Override
    public void trackProgress() {
        progressNotes = "Middle Class progress: Students are developing language skills and basic counting through storytelling.";
    }

    @Override
    public void conductActivity(String activityName) {
        activities.add(activityName);
        System.out.println("Middle Class conducted activity: " + activityName + " (focus on language and counting).");
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
