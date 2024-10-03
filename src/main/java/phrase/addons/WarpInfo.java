package phrase.addons;

import org.bukkit.Location;

import java.util.UUID;

public class WarpInfo {

    private UUID owner;
    private Location location;

    public WarpInfo(UUID owner, Location location) {
        this.owner = owner;
        this.location = location;
    }

    public UUID getOwner() {
        return owner;
    }


    public Location getLocation() {
        return location;
    }

}
