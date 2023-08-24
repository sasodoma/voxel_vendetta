package eu.sasodoma.voxelvendetta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class VoxelVendetta extends JavaPlugin implements Listener {
    private String hubWorldName = "hub";
    private String lobbyWorldName = "lobby";
    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(hubWorldName), this);
        Objects.requireNonNull(this.getCommand("listworlds")).setExecutor(new CommandListworlds());
        // Load the lobby world so it's ready
        getServer().createWorld(new WorldCreator(lobbyWorldName));
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball snowball) {
            if (event.getHitEntity() instanceof Player victim) {
                if (snowball.getShooter() instanceof Player shooter) {
                    final TextComponent hitMessage = Component.text("")
                            .append(Component.text(victim.getName()).color(TextColor.color(0xDD555B)))
                            .append(Component.text(" was shot by "))
                            .append(Component.text(shooter.getName()).color(TextColor.color(0x4A73DD)));

                    getServer().sendMessage(hitMessage);
                    String playerWorld = victim.getWorld().getName();
                    if (playerWorld.equals(hubWorldName)) {
                        victim.teleport(Objects.requireNonNull(Bukkit.getWorld(lobbyWorldName)).getSpawnLocation());
                    } else {
                        victim.teleport(Objects.requireNonNull(Bukkit.getWorld(hubWorldName)).getSpawnLocation());
                    }
                }
            }
        }
    }

    private void loadConfig() {
        String hubWorldNameConfig = getConfig().getString("worlds.hub");
        assert hubWorldNameConfig != null;
        hubWorldName = hubWorldNameConfig;
        String lobbyWorldNameConfig = getConfig().getString("worlds.lobby");
        assert lobbyWorldNameConfig != null;
        lobbyWorldName = lobbyWorldNameConfig;
    }
}
