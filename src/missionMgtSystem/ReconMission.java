package missionMgtSystem;

import java.util.Date;
import java.util.List;

public class ReconMission extends Mission {
    public ReconMission(String missionId, String missionName, Date missionStartDate, Date missionEndDate) {
        super(missionId, missionName, missionStartDate, missionEndDate);
    }

    @Override
    public void assignTask() {
        if (assignedPersonnel.size() < 2) {
            throw new IllegalStateException("ReconMission requires at least 2 personnel.");
        }
        System.out.println("Assigning reconnaissance tasks (surveillance, intelligence gathering) to personnel.");
    }

    @Override
    public void allocateResources(List<Resource> resources) {
        boolean hasDrone = resources.stream()
                .anyMatch(r -> r.getResourceName().equals("Drone") && r.getQuantity() > 0);
        if (!hasDrone) {
            throw new IllegalStateException("No drones available for ReconMission.");
        }
        System.out.println("Allocating drones and communication tools.");
    }

    @Override
    public void trackMissionProgress() {
        System.out.println("Tracking intelligence-gathering progress for " + missionName);
        status = "IN_PROGRESS";
    }

    @Override
    public String generateMissionReport() {
        return "ReconMission Report: " + missionName + "\n" +
                "Status: " + status + "\n" +
                "Personnel: " + assignedPersonnel.size() + "\n" +
                "Tasks: Intelligence gathering in progress.";
    }
}
