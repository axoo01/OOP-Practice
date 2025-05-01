package missionMgtSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Personnel> availablePersonnel = new ArrayList<>();
    private static final List<Resource> availableResources = new ArrayList<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        initializeData();
        while (true) {
            System.out.println("\nMission Management System");
            System.out.println("1. Create Mission");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("2")) {
                System.out.println("Exiting...");
                break;
            } else if (choice.equals("1")) {
                createMission();
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private static void initializeData() {
        availablePersonnel.add(new Personnel("P1", "Alice", "Medic"));
        availablePersonnel.add(new Personnel("P2", "Bob", "Soldier"));
        availablePersonnel.add(new Personnel("P3", "Charlie", "Logistics Officer"));
        availablePersonnel.add(new Personnel("P4", "David", "Soldier"));

        availableResources.add(new Resource("R1", "Drone", 2, "Equipment"));
        availableResources.add(new Resource("R2", "Medical Kit", 5, "Medical Supplies"));
        availableResources.add(new Resource("R3", "Ambulance", 1, "Equipment"));
        availableResources.add(new Resource("R4", "Food Supplies", 10, "Supplies"));
        availableResources.add(new Resource("R5", "Weapon", 3, "Equipment"));
    }

    private static void createMission() {
        String missionType;
        do {
            System.out.println("\nSelect a Mission Type:");
            System.out.println("1. Recon Mission");
            System.out.println("2. Rescue Mission");
            System.out.println("3. Combat Mission");
            System.out.println("4. Humanitarian Mission");
            System.out.print("Choose an option: ");
            missionType = scanner.nextLine();
            if (!missionType.matches("[1-4]")) {
                System.out.println("Invalid mission type. Please choose 1, 2, 3, or 4.");
            }
        } while (!missionType.matches("[1-4]"));

        System.out.print("Enter Mission ID: ");
        String missionId = scanner.nextLine();

        System.out.print("Enter Mission Name: ");
        String missionName = scanner.nextLine();

        Date startDate = null;
        while (startDate == null) {
            System.out.print("Enter Start Date (yyyy-MM-dd): ");
            try {
                startDate = dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd (e.g., 2025-04-27).");
            }
        }

        Date endDate = null;
        while (endDate == null) {
            System.out.print("Enter End Date (yyyy-MM-dd): ");
            try {
                endDate = dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd (e.g., 2025-04-30).");
            }
        }

        Mission mission = null;
        while (mission == null) {
            try {
                switch (missionType) {
                    case "1":
                        mission = new ReconMission(missionId, missionName, startDate, endDate);
                        break;
                    case "2":
                        mission = new RescueMission(missionId, missionName, startDate, endDate);
                        break;
                    case "3":
                        mission = new CombatMission(missionId, missionName, startDate, endDate);
                        break;
                    case "4":
                        mission = new HumanitarianMission(missionId, missionName, startDate, endDate);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please re-enter the dates.");
                startDate = null;
                endDate = null;
                while (startDate == null) {
                    System.out.print("Enter Start Date (yyyy-MM-dd): ");
                    try {
                        startDate = dateFormat.parse(scanner.nextLine());
                    } catch (ParseException e2) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                }
                while (endDate == null) {
                    System.out.print("Enter End Date (yyyy-MM-dd): ");
                    try {
                        endDate = dateFormat.parse(scanner.nextLine());
                    } catch (ParseException e2) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                }
            }
        }

        boolean personnelAssigned = false;
        while (!personnelAssigned) {
            try {
                assignPersonnelToMission(mission);
                mission.assignTask();
                personnelAssigned = true;
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please reassign personnel.");
                // Reset personnel assignments
                for (Personnel p : mission.getAssignedPersonnel()) {
                    p.setAssignedMission(null); // Reset personnel's mission
                }
                mission.getAssignedPersonnel().clear();
            }
        }

        boolean resourcesAllocated = false;
        while (!resourcesAllocated) {
            try {
                allocateResourcesToMission(mission);
                resourcesAllocated = true;
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please reselect resources.");
            }
        }

        mission.trackMissionProgress();
        System.out.println("\nMission Report:\n" + mission.generateMissionReport());
    }

    private static void assignPersonnelToMission(Mission mission) {
        System.out.println("\nAvailable Personnel:");
        for (int i = 0; i < availablePersonnel.size(); i++) {
            Personnel p = availablePersonnel.get(i);
            if (p.getAssignedMission() == null) {
                System.out.println((i + 1) + ". " + p.getPersonnelName() + " (" + p.getPersonnelRole() + ")");
            }
        }
        System.out.print("Enter personnel numbers to assign (space-separated, e.g., 1 2, or 0 to finish): ");
        String[] personnelChoices = scanner.nextLine().trim().split("\\s+");

        for (String choice : personnelChoices) {
            try {
                if (choice.equals("0")) break;
                int index = Integer.parseInt(choice) - 1;
                if (index >= 0 && index < availablePersonnel.size()) {
                    Personnel p = availablePersonnel.get(index);
                    mission.assignPersonnel(p);
                    System.out.println(p.getPersonnelName() + " assigned.");
                } else {
                    System.out.println("Invalid personnel number: " + choice);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + choice);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void allocateResourcesToMission(Mission mission) {
        System.out.println("\nAvailable Resources:");
        for (int i = 0; i < availableResources.size(); i++) {
            Resource r = availableResources.get(i);
            System.out.println((i + 1) + ". " + r.getResourceName() + " (Qty: " + r.getQuantity() + ")");
        }
        System.out.print("Enter resource numbers to allocate (space-separated, e.g., 1 2, or 0 to finish): ");
        String[] resourceChoices = scanner.nextLine().trim().split("\\s+");

        List<Resource> selectedResources = new ArrayList<>();
        for (String choice : resourceChoices) {
            try {
                if (choice.equals("0")) break;
                int index = Integer.parseInt(choice) - 1;
                if (index >= 0 && index < availableResources.size()) {
                    selectedResources.add(availableResources.get(index));
                } else {
                    System.out.println("Invalid resource number: " + choice);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + choice);
            }
        }

        mission.allocateResources(selectedResources);
        System.out.println("Resources allocated successfully.");
    }
}