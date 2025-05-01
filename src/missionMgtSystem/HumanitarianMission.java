package missionMgtSystem;


import java.util.Date;
import java.util.List;

public class HumanitarianMission extends Mission {
    public HumanitarianMission(String missionId, String missionName, Date missionStartDate, Date missionEndDate) {
        super(missionId, missionName, missionStartDate, missionEndDate);
    }

    @Override
    public void assignTask() {
        boolean hasLogistics = assignedPersonnel.stream()
                .anyMatch(p -> p.getPersonnelRole().equals("Logistics Officer"));
        if (!hasLogistics) {
            throw new IllegalStateException("HumanitarianMission requires at least one logistics officer.");
        }
        System.out.println("Assigning logistics, distribution, and medical aid tasks.");
    }

    @Override
    public void allocateResources(List<Resource> resources) {
        boolean hasFood = resources.stream()
                .anyMatch(r -> r.getResourceName().equals("Food Supplies") && r.getQuantity() > 0);
        if (!hasFood) {
            throw new IllegalStateException("No food supplies available for HumanitarianMission.");
        }
        System.out.println("Allocating food supplies, medical kits, and transportation.");
    }

    @Override
    public void trackMissionProgress() {
        System.out.println("Tracking aid distribution progress for " + missionName);
        status = "IN_PROGRESS";
    }

    @Override
    public String generateMissionReport() {
        return "HumanitarianMission Report: " + missionName + "\n" +
                "Status: " + status + "\n" +
                "Personnel: " + assignedPersonnel.size() + "\n" +
                "Tasks: Aid distribution in progress.";
    }
}