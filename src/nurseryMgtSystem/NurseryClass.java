package nurseryMgtSystem;

import java.util.ArrayList;
import java.util.List;

public abstract class NurseryClass {
    protected String classId;
    protected String className;
    protected int maxCapacity;
    protected Teacher assignedTeacher;
    protected List<Student> students;
    protected List<String> activities;
    protected String progressNotes;

    public NurseryClass(String classId, String className, int maxCapacity) {
        this.classId = classId;
        this.className = className;
        this.maxCapacity = maxCapacity;
        this.students = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.progressNotes = "Class progress not yet tracked.";
    }

    public abstract void enrollStudent(Student student);
    public abstract void trackProgress();
    public abstract void conductActivity(String activityName);
    public abstract String generateClassReport();

    public String getClassId() { return classId; }
    public String getClassName() { return className; }
    public int getMaxCapacity() { return maxCapacity; }
    public Teacher getAssignedTeacher() { return assignedTeacher; }
    public List<Student> getStudents() { return students; }

    public void assignTeacher(Teacher teacher) {
        if (teacher.getAssignedClass() != null) {
            throw new IllegalStateException("Teacher " + teacher.getTeacherName() + " is already assigned to another class.");
        }
        validateTeacherRole(teacher);
        this.assignedTeacher = teacher;
        teacher.setAssignedClass(this);
    }

    protected abstract void validateTeacherRole(Teacher teacher);
}