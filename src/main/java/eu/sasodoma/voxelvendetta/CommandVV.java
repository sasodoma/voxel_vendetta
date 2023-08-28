package eu.sasodoma.voxelvendetta;

import eu.sasodoma.voxelvendetta.game.GameWorld;
import eu.sasodoma.voxelvendetta.game.GameWorldManager;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class CommandVV implements CommandExecutor {
    private final VoxelVendetta VVPlugin;
    private final GameWorldManager gameWorldManager;

    public CommandVV(VoxelVendetta VVPlugin, GameWorldManager gameWorldManager) {
        this.VVPlugin = VVPlugin;
        this.gameWorldManager = gameWorldManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Component.text("Possible commands:\ntp <world>\njoin"));
            return true;
        }
        String sub_command = args[0].toLowerCase();
        switch (sub_command) {
            case "tp" -> {
                if (args.length == 1) {
                    player.sendMessage(Component.text("You need to provide a world name!"));
                    break;
                }
                // Try getting the world directly
                World target = VVPlugin.getServer().getWorld(args[1]);
                if (target != null) {
                    player.teleport(target.getSpawnLocation());
                    break;
                }
                // Try loading a game world
                GameWorld gameWorld = gameWorldManager.getWorld(args[1]);
                if (gameWorld == null) {
                    player.sendMessage(Component.text("World does not exist!"));
                    break;
                }
                player.teleport(gameWorld.getWorld().getSpawnLocation());
                player.sendMessage(Component.text(gameWorld.getRedSpawn().toString()));
            }
            case "join" -> player.sendMessage(Component.text("joining"));
            default -> player.sendMessage(Component.text("Unknown command!"));
        }
        return true;
    }
}
