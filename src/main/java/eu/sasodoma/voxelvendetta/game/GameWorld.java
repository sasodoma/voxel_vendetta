package eu.sasodoma.voxelvendetta.game;

import org.bukkit.Location;
import org.bukkit.World;

public class GameWorld {
    private final World world;
    private Location redSpawn;
    private Location redFlag;
    private Location blueSpawn;
    private Location blueFlag;

    public GameWorld(World world) {
        this.world = world;
    }

    public Location getRedSpawn() {
        return redSpawn;
    }

    public void setRedSpawn(Location redSpawn) {
        this.redSpawn = redSpawn;
    }

    public Location getBlueSpawn() {
        return blueSpawn;
    }

    public void setBlueSpawn(Location blueSpawn) {
        this.blueSpawn = blueSpawn;
    }

    public Location getRedFlag() {
        return redFlag;
    }

    public void setRedFlag(Location redFlag) {
        this.redFlag = redFlag;
    }

    public Location getBlueFlag() {
        return blueFlag;
    }

    public void setBlueFlag(Location blueFlag) {
        this.blueFlag = blueFlag;
    }

    public World getWorld() {
        return world;
    }
}
