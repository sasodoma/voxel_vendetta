package eu.sasodoma.voxelvendetta.game.task;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ReloadTask extends BukkitRunnable {
    private final Player player;
    private Integer countdown = 5;
    public ReloadTask(Player player) {
        this.player = player;
    }
    @Override
    public void run() {
        if (countdown == 0) {
            player.getInventory().setItem(0, new ItemStack(Material.SNOWBALL, 64));
            player.sendMessage(Component.text("Reloaded!"));
            this.cancel();
            return;
        }
        player.sendMessage(Component.text("Reloading in ")
                .append(Component.text(countdown--))
                .append(Component.text("s"))
        );
    }
}
