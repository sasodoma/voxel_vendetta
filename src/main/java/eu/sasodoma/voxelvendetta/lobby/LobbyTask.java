package eu.sasodoma.voxelvendetta.lobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTask extends BukkitRunnable {
    private final Lobby lobby;
    private Integer countdown = 120;
    public LobbyTask(Lobby lobby)  {
        this.lobby = lobby;
    }
    @Override
    public void run() {
        switch (lobby.getLobbyState()) {
            case IDLE -> {
                if (lobby.getLobbyWorld().getPlayerCount() > 1) {
                    lobby.setLobbyState(LobbyState.SLOW_COUNTDOWN);
                }
            }
            case SLOW_COUNTDOWN -> {
                if (lobby.getLobbyWorld().getPlayerCount() < 2) {
                    lobby.getLobbyWorld().sendMessage(
                            Component.text("Game countdown cancelled. Not enough players")
                    );
                    countdown = 120;
                    lobby.setLobbyState(LobbyState.IDLE);
                    break;
                }
                if (countdown % 30 == 0) {
                    lobby.getLobbyWorld().sendMessage(Component.text("Game starting in ")
                            .append(Component.text(countdown).color(TextColor.color(0x6DDD87)))
                            .append(Component.text(" seconds."))
                    );
                }
                countdown--;
                if (countdown < 30) lobby.setLobbyState(LobbyState.FAST_COUNTDOWN);
            }
            case FAST_COUNTDOWN -> {
                if (lobby.getLobbyWorld().getPlayerCount() < 2) {
                    lobby.getLobbyWorld().sendMessage(
                            Component.text("Game countdown cancelled. Not enough players")
                                    .color(TextColor.color(0xDD2222))
                    );
                    countdown = 120;
                    lobby.setLobbyState(LobbyState.IDLE);
                    break;
                }
                lobby.getLobbyWorld().sendMessage(Component.text("Game starting in ")
                        .append(Component.text(countdown).color(TextColor.color(0x6DDD87)))
                        .append(Component.text(" seconds."))
                );
                countdown--;
                if (countdown == 0) {
                    countdown = 120;
                    lobby.setLobbyState(LobbyState.IN_GAME);
                }
            }
            case IN_GAME -> {
                if (lobby.getLobbyWorld().getPlayerCount() == 0) {
                    lobby.setLobbyState(LobbyState.IDLE);
                    break;
                }
            }
        }
    }
}
