package missionMgtSystem;

import java.util.Date;
import java.util.List;

public class CombatMission extends Mission {
    public CombatMission(String missionId, String missionName, Date missionStartDate, Date missionEndDate) {
        super(missionId, missionName, missionStartDate, missionEndDate);
    }

    @Override
    public void assignTask() {
        if (assignedPersonnel.size() < 3) {
            throw new IllegalStateException("CombatMission requires at least 3 personnel.");
        }
        System.out.println("Assigning combat tasks (defense, attack, strategy) to personnel.");
    }

    @Override
    public void allocateResources(List<Resource> resources) {
        boolean hasWeapon = resources.stream()
                .anyMatch(r -> r.getResourceName().equals("Weapon") && r.getQuantity() > 0);
        if (!hasWeapon) {
            throw new IllegalStateException("No weapons available for CombatMission.");
        }
        System.out.println("Allocating weapons and vehicles.");
    }

    @Override
    public void trackMissionProgress() {
        System.out.println("Tracking combat progress for " + missionName);
        status = "IN_PROGRESS";
    }

    @Override
    public String generateMissionReport() {
        return "CombatMission Report: " + missionName + "\n" +
                "Status: " + status + "\n" +
                "Personnel: " + assignedPersonnel.size() + "\n" +
                "Tasks: Combat operations in progress.";
    }
}
