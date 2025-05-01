package landMgtSystem;

import java.util.Date;

public class AgriculturalLand extends Land {
    public AgriculturalLand(String landId, String ownerName, String location, double sizeInAcres, Date registrationDate) {
        super(landId, ownerName, location, sizeInAcres, registrationDate);
        if (sizeInAcres < 1) {
            throw new IllegalArgumentException("Agricultural land must be at least 1 acre.");
        }
    }

    @Override
    public void checkZoningCompliance() {
        if (!location.toLowerCase().contains("rural") && !location.toLowerCase().contains("farm")) {
            throw new IllegalStateException("Agricultural land must be in a farming zone (e.g., rural or farm area).");
        }
    }

    @Override
    public double calculateTax() {
        double value = sizeInAcres * 5000;
        return value * 0.01;
    }

    @Override
    public String generateLandReport() {
        String zoningStatus = "Compliant";
        try {
            checkZoningCompliance();
        } catch (IllegalStateException e) {
            zoningStatus = "Non-compliant: " + e.getMessage();
        }

        String ownershipStatus = "Valid";
        try {
            validateOwnership();
        } catch (IllegalArgumentException e) {
            ownershipStatus = "Invalid: " + e.getMessage();
        }

        return "===== Land Report =====\n" +
                "Land ID: " + landId + "\n" +
                "Owner: " + ownerName + "\n" +
                "Location: " + location + "\n" +
                "Size: " + sizeInAcres + " acres\n" +
                "Type: Agricultural\n" +
                "Tax: $" + String.format("%.2f", calculateTax()) + "\n" +
                "Zoning Compliance: " + zoningStatus + "\n" +
                "Ownership Validity: " + ownershipStatus + "\n" +
                "Usage Status: " + landUseStatus + "\n" +
                "======================";
    }

    @Override
    public String getLandType() {
        return "Agricultural";
    }
}