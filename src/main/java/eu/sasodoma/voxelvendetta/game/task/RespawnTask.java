package eu.sasodoma.voxelvendetta.game.task;

import eu.sasodoma.voxelvendetta.game.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnTask extends BukkitRunnable {
    private final Game game;
    private final Player player;
    private Integer countdown = 5;
    public RespawnTask(Game game, Player player, Integer delay) {
        this.game = game;
        this.player = player;
        this.countdown = delay;
    }
    @Override
    public void run() {
        if (countdown == 0) {
            game.respawnPlayer(player);
            this.cancel();
            return;
        }
        player.sendMessage(Component.text("Respawning in ")
                .append(Component.text(countdown--))
                .append(Component.text("s"))
        );
    }
}
