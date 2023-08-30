package eu.sasodoma.voxelvendetta.lobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTask extends BukkitRunnable {
    private final Lobby lobby;
    private static final int MIN_PLAYERS = 1;
    private static final int COUNTDOWN_TIME = 30;
    private Integer countdown = COUNTDOWN_TIME;
    public LobbyTask(Lobby lobby)  {
        this.lobby = lobby;
    }
    @Override
    public void run() {
        switch (lobby.getLobbyState()) {
            case IDLE -> {
                if (lobby.getLobbyWorld().getPlayerCount() >= MIN_PLAYERS) {
                    lobby.setLobbyState(LobbyState.SLOW_COUNTDOWN);
                }
            }
            case SLOW_COUNTDOWN -> {
                if (lobby.getLobbyWorld().getPlayerCount() < MIN_PLAYERS) {
                    cancelCountdown();
                    break;
                }
                if (countdown % 30 == 0) {
                    lobby.getLobbyWorld().sendMessage(Component.text("Game starting in ")
                            .append(Component.text(countdown).color(TextColor.color(0x6DDD87)))
                            .append(Component.text(" seconds."))
                    );
                }
                countdown--;
                if (countdown < 10) lobby.setLobbyState(LobbyState.FAST_COUNTDOWN);
            }
            case FAST_COUNTDOWN -> {
                if (lobby.getLobbyWorld().getPlayerCount() < MIN_PLAYERS) {
                    cancelCountdown();
                    break;
                }
                lobby.getLobbyWorld().sendMessage(Component.text("Game starting in ")
                        .append(Component.text(countdown).color(TextColor.color(0x6DDD87)))
                        .append(Component.text(" seconds."))
                );
                countdown--;
                if (countdown == 0) {
                    countdown = COUNTDOWN_TIME;
                    lobby.setLobbyState(LobbyState.IN_GAME);
                    lobby.startGame();
                }
            }
            case IN_GAME -> {
                if (lobby.getLobbyWorld().getPlayerCount() == 0) {
                    lobby.setLobbyState(LobbyState.IDLE);
                }
            }
        }
    }

    private void cancelCountdown() {
        lobby.getLobbyWorld().sendMessage(
                Component.text("Game countdown cancelled. Not enough players")
                        .color(TextColor.color(0xDD2222))
        );
        countdown = COUNTDOWN_TIME;
        lobby.setLobbyState(LobbyState.IDLE);
    }
}
