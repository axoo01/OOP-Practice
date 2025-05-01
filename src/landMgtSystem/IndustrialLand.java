package landMgtSystem;

import java.util.Date;

public class IndustrialLand extends Land {
    private boolean hasEnvironmentalClearance;

    public IndustrialLand(String landId, String ownerName, String location, double sizeInAcres, Date registrationDate, boolean hasEnvironmentalClearance) {
        super(landId, ownerName, location, sizeInAcres, registrationDate);
        this.hasEnvironmentalClearance = hasEnvironmentalClearance;
    }

    @Override
    public void checkZoningCompliance() {
        if (!location.toLowerCase().contains("industrial") && !location.toLowerCase().contains("factory")) {
            throw new IllegalStateException("Industrial land must be in an industrial zone.");
        }
        if (!hasEnvironmentalClearance && landUseStatus.equals("In Use")) {
            throw new IllegalStateException("Industrial land requires environmental clearance before use.");
        }
    }

    @Override
    public double calculateTax() {
        double value = sizeInAcres * 12000;
        return value * 0.03;
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
                "Type: Industrial\n" +
                "Tax: $" + String.format("%.2f", calculateTax()) + "\n" +
                "Zoning Compliance: " + zoningStatus + "\n" +
                "Ownership Validity: " + ownershipStatus + "\n" +
                "Usage Status: " + landUseStatus + "\n" +
                "Environmental Clearance: " + (hasEnvironmentalClearance ? "Yes" : "No") + "\n" +
                "======================";
    }

    @Override
    public String getLandType() {
        return "Industrial";
    }
}