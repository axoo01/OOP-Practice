package landMgtSystem;

import java.util.Date;

public class CommercialLand extends Land {
    public CommercialLand(String landId, String ownerName, String location, double sizeInAcres, Date registrationDate) {
        super(landId, ownerName, location, sizeInAcres, registrationDate);
    }

    @Override
    public void checkZoningCompliance() {
        if (!location.toLowerCase().contains("commercial") && !location.toLowerCase().contains("business")) {
            throw new IllegalStateException("Commercial land must be in a commercial zone.");
        }
    }

    @Override
    public double calculateTax() {
        double value = sizeInAcres * 10000;
        return value * 0.025;
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
                "Type: Commercial\n" +
                "Tax: $" + String.format("%.2f", calculateTax()) + "\n" +
                "Zoning Compliance: " + zoningStatus + "\n" +
                "Ownership Validity: " + ownershipStatus + "\n" +
                "Usage Status: " + landUseStatus + "\n" +
                "======================";
    }

    @Override
    public String getLandType() {
        return "Commercial";
    }
}
