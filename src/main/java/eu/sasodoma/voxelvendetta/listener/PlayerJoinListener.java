package eu.sasodoma.voxelvendetta.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

/**
 * This listener handles the player joining and teleports them to the hub spawn.
 * It also gives every player a compass to join the lobby.
 */
public class PlayerJoinListener implements Listener {

    private final String hubWorldName;

    public PlayerJoinListener(String hubWorldName) {
        this.hubWorldName = hubWorldName;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals(hubWorldName)) {
            giveLobbyCompass(player);
        }
        player.teleport(Objects.requireNonNull(Bukkit.getWorld(hubWorldName)).getSpawnLocation());
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals(hubWorldName)) {
            giveLobbyCompass(player);
        }
    }

    private void giveLobbyCompass(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        ItemStack lobbyCompass = new ItemStack(Material.COMPASS);
        ItemMeta lobbyCompassMeta = lobbyCompass.getItemMeta();
        lobbyCompassMeta.displayName(Component.text("Join lobby"));
        lobbyCompass.setItemMeta(lobbyCompassMeta);
        inventory.setItem(4, lobbyCompass);
        inventory.setHeldItemSlot(4);
    }
}
