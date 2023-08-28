package eu.sasodoma.voxelvendetta.game.listener;

import eu.sasodoma.voxelvendetta.game.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SnowballListener implements Listener {
    private final Game game;
    public SnowballListener(Game game){
        this.game = game;
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball snowball)) return;
        if (!(event.getHitEntity() instanceof Player victim)) return;
        if (!(snowball.getShooter() instanceof Player shooter)) return;
        if (!victim.getWorld().getName().equals(game.getGameWorld().getWorld().getName())) return;
        final TextComponent hitMessage = Component.text("")
                .append(Component.text(victim.getName()).color(TextColor.color(game.isRed(victim) ? 0xDD555B : 0x4A73DD)))
                .append(Component.text(" was shot by "))
                .append(Component.text(shooter.getName()).color(TextColor.color(game.isRed(shooter) ? 0xDD555B : 0x4A73DD)));

        Bukkit.getServer().sendMessage(hitMessage);
    }
}
