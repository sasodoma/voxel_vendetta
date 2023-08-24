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

public class VoxelVendetta extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getCommand("listworlds").setExecutor(new CommandListworlds());
        getServer().createWorld(new WorldCreator("blur_lobby"));
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
                    if (playerWorld.equals("blur_hub")) {
                        victim.teleport(Bukkit.getWorld("blur_lobby").getSpawnLocation());
                    } else {
                        victim.teleport(Bukkit.getWorld("blur_hub").getSpawnLocation());
                    }
                }
            }
        }
    }
}
