package eu.sasodoma.voxelvendetta.commands;

import eu.sasodoma.voxelvendetta.VoxelVendetta;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandHub implements CommandExecutor {

    private final String hubWorldName;
    public CommandHub(String hubWorldName) {
        this.hubWorldName = hubWorldName;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (player.getWorld().getName().equals(hubWorldName)) {
            player.sendMessage(Component.text("You are already in the hub!"));
            return true;
        }
        player.teleport(Objects.requireNonNull(Bukkit.getWorld(hubWorldName)).getSpawnLocation());
        return true;
    }
}
