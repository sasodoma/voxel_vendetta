package eu.sasodoma.voxelvendetta.game;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import eu.sasodoma.voxelvendetta.game.task.RespawnTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.List;

public class GameTeamDeathMatch extends Game {

    private final Objective killsObjective;
    private final Score redScore;
    private final Score blueScore;

    public GameTeamDeathMatch(VoxelVendetta VVPlugin, GameWorld gameWorld) {
        super(VVPlugin, gameWorld);

        killsObjective = scoreboard.registerNewObjective("kills", Criteria.DUMMY, Component.text("Kills"));
        killsObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        redScore = killsObjective.getScore("RED:");
        blueScore = killsObjective.getScore("BLUE:");
    }

    @Override
    public void startGame(List<Player> players) {
        super.startGame(players);

        redScore.setScore(0);
        blueScore.setScore(0);
    }

    @Override
    public void registerKill(Player victim, Player killer) {
        if (isRed(killer)) {
            redScore.setScore(redScore.getScore() + 1);
            if (redScore.getScore() == 10) {
                gameWorld.getWorld().sendMessage(Component.text("RED team wins!").color(TextColor.color(0xDD2222)));
                endGame();
                return;
            }
        } else {
            blueScore.setScore(blueScore.getScore() + 1);
            if (blueScore.getScore() == 10) {
                gameWorld.getWorld().sendMessage(Component.text("BLUE team wins!").color(TextColor.color(0x2222DD)));
                endGame();
                return;
            }
        }

        killPlayer(victim);
        (new RespawnTask(this, victim, 5)).runTaskTimer(VVPlugin, 0, 20);
    }
}
