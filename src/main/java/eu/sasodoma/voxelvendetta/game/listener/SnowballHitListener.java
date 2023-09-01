package eu.sasodoma.voxelvendetta.game.listener;

import eu.sasodoma.voxelvendetta.game.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SnowballHitListener implements Listener {
    private final Game game;
    private final ArrayList<Player> invulnerablePlayers = new ArrayList<>();
    public SnowballHitListener(Game game){ this.game = game; }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball snowball)) return;
        if (!(event.getHitEntity() instanceof Player victim)) return;
        if (!(snowball.getShooter() instanceof Player shooter)) return;
        if (invulnerablePlayers.contains(victim)) return;
        if (!victim.getWorld().equals(game.getGameWorld().getWorld())) return;
        if (game.isRed(shooter) == game.isRed(victim)) return;
        if (game.isRespawning(shooter)) return;
        if (game.isRespawning(victim)) return;
        final TextComponent hitMessage = Component.text("")
                .append(Component.text(victim.getName()).color(TextColor.color(game.isRed(victim) ? 0xDD555B : 0x4A73DD)))
                .append(Component.text(" was shot by "))
                .append(Component.text(shooter.getName()).color(TextColor.color(game.isRed(shooter) ? 0xDD555B : 0x4A73DD)));
        game.getGameWorld().getWorld().sendMessage(hitMessage);
        game.registerKill(victim, shooter);

    }

    public void makeInvulnerable(Player player, int duration_ticks) {
        invulnerablePlayers.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                invulnerablePlayers.remove(player);
            }
        }.runTaskLater(game.getVVPlugin(), duration_ticks);
    }
}
