package missionMgtSystem;

import java.util.Objects;

public class Personnel {
    private String personnelId;
    private String personnelName;
    private String personnelRole;
    private Mission assignedMission;

    public Personnel(String personnelId, String personnelName, String personnelRole) {
        this.personnelId = personnelId;
        this.personnelName = personnelName;
        this.personnelRole = personnelRole;
        this.assignedMission = null;
    }

    public String getPersonnelId() { return personnelId; }
    public String getPersonnelName() { return personnelName; }
    public String getPersonnelRole() { return personnelRole; }
    public Mission getAssignedMission() { return assignedMission; }
    public void setAssignedMission(Mission mission) { this.assignedMission = mission; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personnel personnel = (Personnel) o;
        return personnelId.equals(personnel.personnelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personnelId);
    }
}