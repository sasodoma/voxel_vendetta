package eu.sasodoma.voxelvendetta.lobby;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import eu.sasodoma.voxelvendetta.game.GameTeamDeathMatch;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Lobby {
    private final VoxelVendetta VVPlugin;
    private World lobbyWorld;
    private LobbyState lobbyState = LobbyState.IDLE;

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
        LobbyTask lobbyTask = new LobbyTask(this);
        lobbyTask.runTaskTimer(VVPlugin, 0, 20);
    }

    public void startGame() {
        VVPlugin.setGame(new GameTeamDeathMatch(VVPlugin, VVPlugin.getGameWorldManager().getWorld("sakura")));
        VVPlugin.getGame().startGame(lobbyWorld.getPlayers());
    }
}
