package eu.sasodoma.voxelvendetta.game.listener;

import eu.sasodoma.voxelvendetta.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerDamageListener implements Listener {
    private final Game game;
    public PlayerDamageListener(Game game){ this.game = game; }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (game.isInGame(player)) event.setCancelled(true);
        if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            game.registerKill(player, null);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (game.isInGame(player)) event.setCancelled(true);
    }
}
