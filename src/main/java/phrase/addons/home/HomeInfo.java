package phrase.addons.home;

import org.bukkit.World;

import java.util.UUID;

public class HomeInfo {

    private UUID owner;
    private World world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public HomeInfo(UUID owner, World world, double x, double y, double z, float yaw, float pitch) {
        this.owner = owner;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public UUID getOwner() {
        return owner;
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
