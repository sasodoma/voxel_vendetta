package eu.sasodoma.voxelvendetta.game;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Set;

public class GameWorldManager {
    private final VoxelVendetta VVPlugin;
    private final ConfigurationSection maps_section;
    private final Set<String> maps;
    public GameWorldManager(VoxelVendetta plugin) {
        VVPlugin = plugin;
        maps_section = VVPlugin.getConfig().getConfigurationSection("worlds.maps");
        assert maps_section != null;
        maps = maps_section.getKeys(false);
    }

    public GameWorld getWorld(String name) {
        if (!maps.contains(name)) return null;
        GameWorld gameWorld = new GameWorld(VVPlugin.getServer().createWorld(new WorldCreator(name)));
        try {
            ConfigurationSection gameWorldConfig = maps_section.getConfigurationSection(name);
            assert gameWorldConfig != null;
            assert gameWorld.getWorld() != null;
            gameWorld.getWorld().setSpawnLocation(
                    gameWorldConfig.getInt("spawn.x"),
                    gameWorldConfig.getInt("spawn.y"),
                    gameWorldConfig.getInt("spawn.z"),
                    (float) gameWorldConfig.getDouble("spawn.rot")
            );
            gameWorld.setRedSpawn(new Location(
                    gameWorld.getWorld(),
                    gameWorldConfig.getInt("red.spawn.x"),
                    gameWorldConfig.getInt("red.spawn.y"),
                    gameWorldConfig.getInt("red.spawn.z"),
                    (float) gameWorldConfig.getDouble("red.spawn.rot"),
                    0)
            );
            gameWorld.setRedFlag(new Location(
                    gameWorld.getWorld(),
                    gameWorldConfig.getInt("red.flag.x"),
                    gameWorldConfig.getInt("red.flag.y"),
                    gameWorldConfig.getInt("red.flag.z"),
                    (float) gameWorldConfig.getDouble("red.flag.rot"),
                    0)
            );
            gameWorld.setBlueSpawn(new Location(
                    gameWorld.getWorld(),
                    gameWorldConfig.getInt("blue.spawn.x"),
                    gameWorldConfig.getInt("blue.spawn.y"),
                    gameWorldConfig.getInt("blue.spawn.z"),
                    (float) gameWorldConfig.getDouble("blue.spawn.rot"),
                    0)
            );
            gameWorld.setBlueFlag(new Location(
                    gameWorld.getWorld(),
                    gameWorldConfig.getInt("blue.flag.x"),
                    gameWorldConfig.getInt("blue.flag.y"),
                    gameWorldConfig.getInt("blue.flag.z"),
                    (float) gameWorldConfig.getDouble("blue.flag.rot"),
                    0)
            );
        } catch (AssertionError ignored) {}
        return gameWorld;
    }

    public boolean isGameWorld(String name) {
        return maps.contains(name);
    }

    public Set<String> getMaps() {
        return maps;
    }
}
