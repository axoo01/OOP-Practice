package missionMgtSystem;

import missionMgtSystem.Personnel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Mission {
    protected String missionId;
    protected String missionName;
    protected Date missionStartDate;
    protected Date missionEndDate;
    protected String status; // PLANNED, IN_PROGRESS, COMPLETED
    protected List<Personnel> assignedPersonnel;

    public Mission(String missionId, String missionName, Date missionStartDate, Date missionEndDate) {
        this.missionId = missionId;
        this.missionName = missionName;
        this.missionStartDate = missionStartDate;
        this.missionEndDate = missionEndDate;
        this.status = "PLANNED";
        this.assignedPersonnel = new ArrayList<>();
        validateDates();
    }

    private void validateDates() {
        if (missionStartDate.after(missionEndDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }

    public void assignPersonnel(Personnel personnel) {
        if (personnel.getAssignedMission() != null) {
            throw new IllegalStateException("Personnel already assigned to another mission.");
        }
        if (!assignedPersonnel.contains(personnel)) {
            assignedPersonnel.add(personnel);
            personnel.setAssignedMission(this);
        } else {
            throw new IllegalArgumentException("Duplicate personnel assignment.");
        }
    }

    public String getMissionId() { return missionId; }
    public String getMissionName() { return missionName; }
    public Date getMissionStartDate() { return missionStartDate; }
    public Date getMissionEndDate() { return missionEndDate; }
    public String getStatus() { return status; }
    public List<Personnel> getAssignedPersonnel() { return assignedPersonnel; }

    public abstract void assignTask();
    public abstract void allocateResources(List<Resource> resources);
    public abstract void trackMissionProgress();
    public abstract String generateMissionReport();
}