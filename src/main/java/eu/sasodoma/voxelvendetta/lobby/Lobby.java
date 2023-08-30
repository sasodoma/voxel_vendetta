package eu.sasodoma.voxelvendetta.lobby;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import eu.sasodoma.voxelvendetta.game.GameCTF;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

public class Lobby {
    private final VoxelVendetta VVPlugin;
    private World lobbyWorld;
    private LobbyState lobbyState = LobbyState.IDLE;
    private LobbyTask lobbyTask;
    public Lobby(VoxelVendetta plugin)  {
        VVPlugin = plugin;
    }

    public World getLobbyWorld() {
        return lobbyWorld;
    }

    public LobbyState getLobbyState() {
        return lobbyState;
    }

    public void setLobbyState(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }

    public void loadLobby(String lobbyWorldName) {
        lobbyWorld = VVPlugin.getServer().createWorld(new WorldCreator(lobbyWorldName));
        lobbyTask = new LobbyTask(this);
            lobbyTask.runTaskTimer(VVPlugin, 0, 20);
    }

    public void startGame() {
        VVPlugin.setGame(new GameCTF(VVPlugin, VVPlugin.getGameWorldManager().getWorld("sakura")));
        VVPlugin.getGame().startGame(lobbyWorld.getPlayers());
    }
}
