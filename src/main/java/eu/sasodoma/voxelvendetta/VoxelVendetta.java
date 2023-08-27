package eu.sasodoma.voxelvendetta;

import eu.sasodoma.voxelvendetta.game.GameWorldManager;
import eu.sasodoma.voxelvendetta.listener.PlayerJoinListener;
import eu.sasodoma.voxelvendetta.lobby.Lobby;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class VoxelVendetta extends JavaPlugin implements Listener {
    private final GameWorldManager gameWorldManager = new GameWorldManager(this);
    private String hubWorldName = "hub";
    private String lobbyWorldName = "lobby";
    private final Lobby lobby = new Lobby(this);
    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(hubWorldName), this);
        Objects.requireNonNull(this.getCommand("vv")).setExecutor(new CommandVV(this, gameWorldManager));
        lobby.loadLobby(lobbyWorldName);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball snowball)) return;
        if (!(event.getHitEntity() instanceof Player victim)) return;
        if (!(snowball.getShooter() instanceof Player shooter)) return;
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

    private void loadConfig() {
        String hubWorldNameConfig = getConfig().getString("worlds.hub");
        assert hubWorldNameConfig != null;
        hubWorldName = hubWorldNameConfig;
        String lobbyWorldNameConfig = getConfig().getString("worlds.lobby");
        assert lobbyWorldNameConfig != null;
        lobbyWorldName = lobbyWorldNameConfig;
    }
}
