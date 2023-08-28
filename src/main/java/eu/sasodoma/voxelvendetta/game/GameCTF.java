package eu.sasodoma.voxelvendetta.game;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import org.bukkit.entity.Player;

import java.util.List;

public class GameCTF extends Game {

    public GameCTF(VoxelVendetta VVPlugin, GameWorld gameWorld) {
        super(VVPlugin, gameWorld);
    }

    @Override
    public void startGame(List<Player> players) {


        super.startGame(players);
    }
}
