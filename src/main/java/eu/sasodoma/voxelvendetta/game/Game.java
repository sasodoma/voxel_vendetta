package eu.sasodoma.voxelvendetta.game;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import eu.sasodoma.voxelvendetta.game.listener.ReloadListener;
import eu.sasodoma.voxelvendetta.game.listener.SnowballHitListener;
import eu.sasodoma.voxelvendetta.game.task.RespawnTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Game {
    private final VoxelVendetta VVPlugin;
    protected final GameWorld gameWorld;
    protected final List<Player> redPlayers = new ArrayList<>();
    protected final List<Player> bluePlayers = new ArrayList<>();
    protected final List<Player> respawningPlayers = new ArrayList<>();
    private final SnowballHitListener snowballHitListener = new SnowballHitListener(this);
    private final ReloadListener reloadListener = new ReloadListener(this);

    public Game(VoxelVendetta VVPlugin, GameWorld gameWorld) {
        this.VVPlugin = VVPlugin;
        this.gameWorld = gameWorld;
    }

    public void startGame(List<Player> players) {
        Bukkit.getPluginManager().registerEvents(snowballHitListener, VVPlugin);
        Bukkit.getPluginManager().registerEvents(reloadListener, VVPlugin);

        players.forEach(this::killPlayer);

        boolean red = true;
        while (players.size() > 0) {
            Random random = new Random();
            (red ? redPlayers : bluePlayers).add(players.remove(random.nextInt(players.size())));
            red = !red;
        }

        redPlayers.forEach(player -> {
            gameWorld.getWorld().sendMessage(
                Component.text(player.getName()).color(TextColor.color(0xdd2222))
            );
        });
        bluePlayers.forEach(player -> {
            gameWorld.getWorld().sendMessage(
                    Component.text(player.getName()).color(TextColor.color(0x2222dd))
            );
        });
    }

    public GameWorld getGameWorld() { return gameWorld; }
    public VoxelVendetta getVVPlugin() { return VVPlugin; }

    public boolean isRed(Player player) { return redPlayers.contains(player); }
    public boolean isBlue(Player player) { return bluePlayers.contains(player); }
    public boolean isInGame(Player player) { return isRed(player) || isBlue(player); }

    public boolean isRespawning(Player player) { return respawningPlayers.contains(player); }

    public void killPlayer(Player player) {
        respawningPlayers.add(player);
        player.teleport(gameWorld.getWorld().getSpawnLocation());
        (new RespawnTask(this, player, 5)).runTaskTimer(VVPlugin, 0, 20);
    }

    public void respawnPlayer(Player player) {
        player.getInventory().setItem(0, new ItemStack(Material.SNOWBALL, 64));
        if (isRed(player)) player.teleport(gameWorld.getRedSpawn());
        if (isBlue(player)) player.teleport(gameWorld.getBlueSpawn());
        respawningPlayers.remove(player);
    }
}
