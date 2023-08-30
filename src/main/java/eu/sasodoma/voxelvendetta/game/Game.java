package eu.sasodoma.voxelvendetta.game;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import eu.sasodoma.voxelvendetta.game.listener.SnowballListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Game {
    private final VoxelVendetta VVPlugin;
    protected final GameWorld gameWorld;
    protected final List<Player> redPlayers = new ArrayList<Player>();
    protected final List<Player> bluePlayers = new ArrayList<Player>();
    private final SnowballListener snowballListener = new SnowballListener(this);

    public Game(VoxelVendetta VVPlugin, GameWorld gameWorld) {
        this.VVPlugin = VVPlugin;
        this.gameWorld = gameWorld;
    }

    public void startGame(List<Player> players) {
        Bukkit.getPluginManager().registerEvents(snowballListener, VVPlugin);

        players.forEach(player -> player.teleport(gameWorld.getWorld().getSpawnLocation()));

        boolean red = true;
        while (players.size() > 0) {
            Random random = new Random();
            (red ? redPlayers : bluePlayers).add(players.remove(random.nextInt(players.size())));
            red = !red;
        }

        redPlayers.forEach(player -> gameWorld.getWorld().sendMessage(
                Component.text(player.getName()).color(TextColor.color(0xdd2222))
        ));
        bluePlayers.forEach(player -> gameWorld.getWorld().sendMessage(
                Component.text(player.getName()).color(TextColor.color(0x2222dd))
        ));
    }

    public GameWorld getGameWorld() { return gameWorld; }

    public boolean isRed(Player player) { return redPlayers.contains(player); }
    public boolean isBlue(Player player) { return bluePlayers.contains(player); }
}
