package phrase.addons;

import org.bukkit.Location;
import phrase.addons.command.Warp;

import java.util.UUID;

public class WarpInfo extends Warp {

    private UUID owner;
    private Location location;

    public WarpInfo(UUID owner, Location location) {
        this.owner = owner;
        this.location = location;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
