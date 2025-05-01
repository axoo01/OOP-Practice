package missionMgtSystem;

import java.util.Date;
import java.util.List;

public class RescueMission extends Mission {
    public RescueMission(String missionId, String missionName, Date missionStartDate, Date missionEndDate) {
        super(missionId, missionName, missionStartDate, missionEndDate);
    }

    @Override
    public void assignTask() {
        boolean hasMedic = assignedPersonnel.stream()
                .anyMatch(p -> p.getPersonnelRole().equals("Medic"));
        if (!hasMedic) {
            throw new IllegalStateException("RescueMission requires at least one medic.");
        }
        System.out.println("Assigning rescue, medical, and logistics tasks.");
    }

    @Override
    public void allocateResources(List<Resource> resources) {
        boolean hasMedicalKit = resources.stream()
                .anyMatch(r -> r.getResourceName().equals("Medical Kit") && r.getQuantity() > 0);
        if (!hasMedicalKit) {
            throw new IllegalStateException("No medical kits available for RescueMission.");
        }
        System.out.println("Allocating medical kits, ambulances, and rescue equipment.");
    }

    @Override
    public void trackMissionProgress() {
        System.out.println("Tracking rescue progress for " + missionName);
        status = "IN_PROGRESS";
    }

    @Override
    public String generateMissionReport() {
        return "RescueMission Report: " + missionName + "\n" +
                "Status: " + status + "\n" +
                "Personnel: " + assignedPersonnel.size() + "\n" +
                "Tasks: Rescues and medical tasks in progress.";
    }
}