package eu.sasodoma.voxelvendetta.game.listener;

import eu.sasodoma.voxelvendetta.game.Game;
import eu.sasodoma.voxelvendetta.game.task.ReloadTask;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ReloadListener implements Listener {
    private final Game game;
    public ReloadListener(Game game){ this.game = game; }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!game.isInGame(player)) return;
        Item itemDrop = event.getItemDrop();
        if (!(itemDrop.getItemStack().getType() == Material.SNOWBALL)) {
            event.setCancelled(true);
            return;
        }
        itemDrop.remove();
        reload(player);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Snowball)) return;
        if (!(event.getEntity().getShooter() instanceof Player player)) return;
        if (!game.isInGame(player)) return;
        ItemStack snowballs = player.getInventory().getItem(0);
        if (snowballs == null || snowballs.getAmount() == 1) reload(player);
    }

    private void reload(Player player) {
        player.getInventory().remove(Material.SNOWBALL);
        (new ReloadTask(player)).runTaskTimer(game.getVVPlugin(), 0, 20);
    }
}
