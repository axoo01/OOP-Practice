package landMgtSystem;

import java.util.Date;

public class ResidentialLand extends Land {
    public ResidentialLand(String landId, String ownerName, String location, double sizeInAcres, Date registrationDate) {
        super(landId, ownerName, location, sizeInAcres, registrationDate);
    }

    @Override
    public void checkZoningCompliance() {
        if (!location.toLowerCase().contains("residential") && !location.toLowerCase().contains("housing")) {
            throw new IllegalStateException("Residential land must be in a residential zone.");
        }
        if (landUseStatus.equals("In Use") && sizeInAcres < 0.5) {
            throw new IllegalStateException("Residential land in use must support at least 1 unit (min 0.5 acres for 2 units/acre).");
        }
    }

    @Override
    public double calculateTax() {
        double value = sizeInAcres * 8000;
        return value * 0.015;
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
                "Type: Residential\n" +
                "Tax: $" + String.format("%.2f", calculateTax()) + "\n" +
                "Zoning Compliance: " + zoningStatus + "\n" +
                "Ownership Validity: " + ownershipStatus + "\n" +
                "Usage Status: " + landUseStatus + "\n" +
                "======================";
    }

    @Override
    public String getLandType() {
        return "Residential";
    }
}