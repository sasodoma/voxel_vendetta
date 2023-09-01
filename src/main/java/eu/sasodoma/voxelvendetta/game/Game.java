package eu.sasodoma.voxelvendetta.game;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import eu.sasodoma.voxelvendetta.game.listener.PlayerDamageListener;
import eu.sasodoma.voxelvendetta.game.listener.ReloadListener;
import eu.sasodoma.voxelvendetta.game.listener.SnowballHitListener;
import eu.sasodoma.voxelvendetta.game.task.RespawnTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Game {
    protected final VoxelVendetta VVPlugin;
    protected final GameWorld gameWorld;
    protected final Scoreboard scoreboard;
    protected final Team redTeam;
    protected final Team blueTeam;
    protected final ArrayList<Player> respawningPlayers = new ArrayList<>();
    private final SnowballHitListener snowballHitListener = new SnowballHitListener(this);
    private final ReloadListener reloadListener = new ReloadListener(this);
    private final PlayerDamageListener playerDamageListener = new PlayerDamageListener(this);

    public Game(VoxelVendetta VVPlugin, GameWorld gameWorld) {
        this.VVPlugin = VVPlugin;
        this.gameWorld = gameWorld;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        redTeam = scoreboard.registerNewTeam("red");
        blueTeam = scoreboard.registerNewTeam("blue");

        redTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        blueTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
    }

    public void startGame(List<Player> players) {
        Bukkit.getPluginManager().registerEvents(snowballHitListener, VVPlugin);
        Bukkit.getPluginManager().registerEvents(reloadListener, VVPlugin);
        Bukkit.getPluginManager().registerEvents(playerDamageListener, VVPlugin);

        players.forEach(player -> {
            player.setScoreboard(scoreboard);
            killPlayer(player);
            (new RespawnTask(this, player, 5)).runTaskTimer(VVPlugin, 0, 20);
        });

        boolean red = true;
        while (players.size() > 0) {
            Random random = new Random();
            Player player = players.remove(random.nextInt(players.size()));
            (red ? redTeam : blueTeam).addEntry(player.getName());
            player.displayName(Component.text(player.getName()).color(TextColor.color(red ? 0xdd2222 : 0x2222dd)));
            red = !red;
        }

        redTeam.getEntries().forEach(playerName -> gameWorld.getWorld().sendMessage(
                Component.text(playerName).color(TextColor.color(0xdd2222))
        ));
        blueTeam.getEntries().forEach(playerName -> gameWorld.getWorld().sendMessage(
                Component.text(playerName).color(TextColor.color(0x2222dd))
        ));
    }

    public void endGame() {
        HandlerList.unregisterAll(snowballHitListener);
        HandlerList.unregisterAll(reloadListener);
        HandlerList.unregisterAll(playerDamageListener);
        gameWorld.getWorld().getPlayers().forEach(
                player -> player.teleport(VVPlugin.getLobby().getLobbyWorld().getSpawnLocation())
        );
    }

    public GameWorld getGameWorld() { return gameWorld; }
    public VoxelVendetta getVVPlugin() { return VVPlugin; }

    public boolean isRed(Player player) { return redTeam.getEntries().contains(player.getName()); }
    public boolean isBlue(Player player) { return blueTeam.getEntries().contains(player.getName()); }
    public boolean isInGame(Player player) { return isRed(player) || isBlue(player); }

    public boolean isRespawning(Player player) { return respawningPlayers.contains(player); }

    public abstract void registerKill(Player victim, Player killer);

    public void killPlayer(Player player) {
        respawningPlayers.add(player);
        player.teleport(gameWorld.getWorld().getSpawnLocation());
    }

    public void respawnPlayer(Player player) {
        player.getInventory().setItem(0, new ItemStack(Material.SNOWBALL, 64));
        if (isRed(player)) player.teleport(gameWorld.getRedSpawn());
        if (isBlue(player)) player.teleport(gameWorld.getBlueSpawn());
        respawningPlayers.remove(player);
        snowballHitListener.makeInvulnerable(player, 40);
    }
}
