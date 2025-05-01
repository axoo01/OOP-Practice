package landMgtSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LandMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final LandRegistry registry = new LandRegistry();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nLand Management System");
            System.out.println("1. Register New Land");
            System.out.println("2. Search by Type");
            System.out.println("3. Search by Owner");
            System.out.println("4. Search by Location");
            System.out.println("5. Display All Lands");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerNewLand();
                    break;
                case "2":
                    searchByType();
                    break;
                case "3":
                    searchByOwner();
                    break;
                case "4":
                    searchByLocation();
                    break;
                case "5":
                    registry.displayAllLands();
                    break;
                case "6":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1-6.");
            }
        }
    }

    private static void registerNewLand() {
        String landType;
        do {
            System.out.println("\nSelect Land Type:");
            System.out.println("1. Agricultural Land");
            System.out.println("2. Residential Land");
            System.out.println("3. Commercial Land");
            System.out.println("4. Industrial Land");
            System.out.print("Choose an option: ");
            landType = scanner.nextLine();
            if (!landType.matches("[1-4]")) {
                System.out.println("Invalid land type. Please choose 1, 2, 3, or 4.");
            }
        } while (!landType.matches("[1-4]"));

        System.out.print("Enter Land ID: ");
        String landId = scanner.nextLine();

        String ownerName = null;
        while (ownerName == null) {
            System.out.print("Enter Owner Name: ");
            ownerName = scanner.nextLine();
            if (ownerName.trim().isEmpty()) {
                System.out.println("Owner name cannot be empty. Please try again.");
                ownerName = null;
            }
        }

        String locationPrompt;
        switch (landType) {
            case "1":
                locationPrompt = "Enter Location (e.g., Rural Farm, Countryside): ";
                break;
            case "2":
                locationPrompt = "Enter Location (e.g., Residential Area, Housing Estate): ";
                break;
            case "3":
                locationPrompt = "Enter Location (e.g., Commercial District, Business Park): ";
                break;
            case "4":
                locationPrompt = "Enter Location (e.g., Industrial Zone, Factory Area): ";
                break;
            default:
                locationPrompt = "Enter Location: ";
        }
        System.out.print(locationPrompt);
        String location = scanner.nextLine();

        Double sizeInAcres = null;
        while (sizeInAcres == null) {
            System.out.print("Enter Size in Acres" + (landType.equals("1") ? " (must be at least 1 acre for Agricultural Land)" : "") + ": ");
            try {
                sizeInAcres = Double.parseDouble(scanner.nextLine());
                if (sizeInAcres <= 0) {
                    System.out.println("Size must be greater than 0. Please try again.");
                    sizeInAcres = null;
                } else if (landType.equals("1") && sizeInAcres < 1) {
                    System.out.println("Agricultural land must be at least 1 acre. Please try again.");
                    sizeInAcres = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid number (e.g., 2.5).");
            }
        }

        Date registrationDate = null;
        while (registrationDate == null) {
            System.out.print("Enter Registration Date (yyyy-MM-dd): ");
            try {
                registrationDate = dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd (e.g., 2025-04-27).");
            }
        }

        Boolean hasEnvironmentalClearance = null;
        if (landType.equals("4")) {
            while (hasEnvironmentalClearance == null) {
                System.out.print("Has Environmental Clearance? (yes/no): ");
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("yes")) {
                    hasEnvironmentalClearance = true;
                } else if (input.equals("no")) {
                    hasEnvironmentalClearance = false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }
        }

        Land land = null;
        while (land == null) {
            try {
                switch (landType) {
                    case "1":
                        land = new AgriculturalLand(landId, ownerName, location, sizeInAcres, registrationDate);
                        break;
                    case "2":
                        land = new ResidentialLand(landId, ownerName, location, sizeInAcres, registrationDate);
                        break;
                    case "3":
                        land = new CommercialLand(landId, ownerName, location, sizeInAcres, registrationDate);
                        break;
                    case "4":
                        land = new IndustrialLand(landId, ownerName, location, sizeInAcres, registrationDate, hasEnvironmentalClearance);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                if (e.getMessage().contains("Owner")) {
                    ownerName = null;
                    while (ownerName == null) {
                        System.out.print("Enter Owner Name: ");
                        ownerName = scanner.nextLine();
                        if (ownerName.trim().isEmpty()) {
                            System.out.println("Owner name cannot be empty. Please try again.");
                            ownerName = null;
                        }
                    }
                }
            }
        }

        String landUseStatus = null;
        while (landUseStatus == null) {
            System.out.println("Select Land Use Status:");
            System.out.println("1. Vacant");
            System.out.println("2. In Use");
            System.out.println("3. Under Development");
            System.out.print("Choose an option: ");
            String statusChoice = scanner.nextLine();
            switch (statusChoice) {
                case "1":
                    landUseStatus = "Vacant";
                    break;
                case "2":
                    landUseStatus = "In Use";
                    break;
                case "3":
                    landUseStatus = "Under Development";
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
        land.setLandUseStatus(landUseStatus);

        boolean zoningCompliant = false;
        while (!zoningCompliant) {
            try {
                land.checkZoningCompliance();
                zoningCompliant = true;
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.print(locationPrompt);
                location = scanner.nextLine();
                try {
                    land = switch (landType) {
                        case "1" -> new AgriculturalLand(landId, ownerName, location, sizeInAcres, registrationDate);
                        case "2" -> new ResidentialLand(landId, ownerName, location, sizeInAcres, registrationDate);
                        case "3" -> new CommercialLand(landId, ownerName, location, sizeInAcres, registrationDate);
                        case "4" -> new IndustrialLand(landId, ownerName, location, sizeInAcres, registrationDate, hasEnvironmentalClearance);
                        default -> land;
                    };
                    land.setLandUseStatus(landUseStatus);
                } catch (IllegalArgumentException e2) {
                    System.out.println("Unexpected error: " + e2.getMessage());
                }
            }
        }

        registry.registerLand(land);
        System.out.println(land.generateLandReport());
    }

    private static void searchByType() {
        System.out.print("Enter Land Type (Agricultural, Residential, Commercial, Industrial): ");
        String type = scanner.nextLine();
        List<Land> results = registry.searchByType(type);
        if (results.isEmpty()) {
            System.out.println("No lands found of type " + type + ".");
        } else {
            for (Land land : results) {
                System.out.println(land.generateLandReport());
            }
        }
    }

    private static void searchByOwner() {
        System.out.print("Enter Owner Name: ");
        String ownerName = scanner.nextLine();
        List<Land> results = registry.searchByOwner(ownerName);
        if (results.isEmpty()) {
            System.out.println("No lands found for owner " + ownerName + ".");
        } else {
            for (Land land : results) {
                System.out.println(land.generateLandReport());
            }
        }
    }

    private static void searchByLocation() {
        System.out.print("Enter Location Keyword: ");
        String location = scanner.nextLine();
        List<Land> results = registry.searchByLocation(location);
        if (results.isEmpty()) {
            System.out.println("No lands found in location containing " + location + ".");
        } else {
            for (Land land : results) {
                System.out.println(land.generateLandReport());
            }
        }
    }
}