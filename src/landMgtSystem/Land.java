package landMgtSystem;

import java.util.Date;

public abstract class Land {
    protected String landId;
    protected String ownerName;
    protected String location;
    protected double sizeInAcres;
    protected Date registrationDate;
    protected String landUseStatus;

    public Land(String landId, String ownerName, String location, double sizeInAcres, Date registrationDate) {
        this.landId = landId;
        this.ownerName = ownerName;
        this.location = location;
        this.sizeInAcres = sizeInAcres;
        this.registrationDate = registrationDate;
        this.landUseStatus = "Vacant";
        validateOwnership();
    }

    protected void validateOwnership() {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty.");
        }
    }

    public abstract void checkZoningCompliance();
    public abstract double calculateTax();
    public abstract String generateLandReport();

    // Getters
    public String getLandId() { return landId; }
    public String getOwnerName() { return ownerName; }
    public String getLocation() { return location; }
    public double getSizeInAcres() { return sizeInAcres; }
    public Date getRegistrationDate() { return registrationDate; }
    public String getLandUseStatus() { return landUseStatus; }
    public void setLandUseStatus(String status) { this.landUseStatus = status; }
    public abstract String getLandType();
}