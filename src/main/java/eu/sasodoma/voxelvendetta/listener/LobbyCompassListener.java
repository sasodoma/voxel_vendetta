package eu.sasodoma.voxelvendetta.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class LobbyCompassListener implements Listener {
    private final String hubWorldName;
    private final String lobbyWorldName;
    public LobbyCompassListener(String hubWorldName, String lobbyWorldName) {
        this.hubWorldName = hubWorldName;
        this.lobbyWorldName = lobbyWorldName;
    }

    @EventHandler
    public void onCompassClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;
        if (item.getType() != Material.COMPASS) return;
        if (!player.getWorld().getName().equals(hubWorldName)) return;
        player.teleport(Objects.requireNonNull(Bukkit.getWorld(lobbyWorldName)).getSpawnLocation());
        player.getInventory().clear();
    }
}
