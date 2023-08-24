package eu.sasodoma.voxelvendetta;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandListworlds implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<World> worlds = Bukkit.getWorlds();
        for (World world : worlds) {
            Bukkit.getServer().sendMessage(Component.text(world.getName()));
        }
        return true;
    }
}
