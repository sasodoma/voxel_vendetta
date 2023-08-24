package eu.sasodoma.voxelvendetta;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

/**
 * This listener handles the player joining and teleports them to the hub spawn.
 */
public class PlayerJoinListener implements Listener {

    private final String hubWorldName;

    public PlayerJoinListener(String hubWorldName) {
        this.hubWorldName = hubWorldName;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(Objects.requireNonNull(Bukkit.getWorld(hubWorldName)).getSpawnLocation());
    }
}
