package top.jingwenmc.wtfsurvival.object;

import org.bukkit.command.CommandSender;

public interface CommandItem {
    boolean onCommand(String[] args, CommandSender sender);
}
