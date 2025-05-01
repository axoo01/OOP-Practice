package landMgtSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LandRegistry {
    private List<Land> lands;

    public LandRegistry() {
        this.lands = new ArrayList<>();
    }

    public void registerLand(Land land) {
        lands.add(land);
        System.out.println("Land " + land.getLandId() + " registered successfully.");
    }

    public List<Land> searchByType(String type) {
        return lands.stream()
                .filter(land -> land.getLandType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Land> searchByOwner(String ownerName) {
        return lands.stream()
                .filter(land -> land.getOwnerName().equalsIgnoreCase(ownerName))
                .collect(Collectors.toList());
    }

    public List<Land> searchByLocation(String location) {
        return lands.stream()
                .filter(land -> land.getLocation().toLowerCase().contains(location.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void displayAllLands() {
        if (lands.isEmpty()) {
            System.out.println("No lands registered.");
            return;
        }
        for (Land land : lands) {
            System.out.println(land.generateLandReport());
        }
    }
}
