package eu.sasodoma.voxelvendetta;

import eu.sasodoma.voxelvendetta.commands.CommandHub;
import eu.sasodoma.voxelvendetta.commands.CommandVV;
import eu.sasodoma.voxelvendetta.game.Game;
import eu.sasodoma.voxelvendetta.game.GameWorldManager;
import eu.sasodoma.voxelvendetta.listener.LobbyCompassListener;
import eu.sasodoma.voxelvendetta.listener.PlayerJoinListener;
import eu.sasodoma.voxelvendetta.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class VoxelVendetta extends JavaPlugin {
    private final GameWorldManager gameWorldManager = new GameWorldManager(this);
    private String hubWorldName = "hub";
    private String lobbyWorldName = "lobby";
    private final Lobby lobby = new Lobby(this);
    private Game game;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(hubWorldName), this);
        Bukkit.getPluginManager().registerEvents(new LobbyCompassListener(hubWorldName, lobbyWorldName), this);
        Objects.requireNonNull(this.getCommand("vv")).setExecutor(new CommandVV(this, gameWorldManager));
        Objects.requireNonNull(this.getCommand("hub")).setExecutor(new CommandHub(hubWorldName));
        lobby.loadLobby(lobbyWorldName);
    }

    private void loadConfig() {
        String hubWorldNameConfig = getConfig().getString("worlds.hub");
        assert hubWorldNameConfig != null;
        hubWorldName = hubWorldNameConfig;
        String lobbyWorldNameConfig = getConfig().getString("worlds.lobby");
        assert lobbyWorldNameConfig != null;
        lobbyWorldName = lobbyWorldNameConfig;
    }

    public Game getGame() { return game; }

    public void setGame(Game game) { this.game = game; }

    public GameWorldManager getGameWorldManager() { return gameWorldManager; }
    public Lobby getLobby() { return lobby; }
}
