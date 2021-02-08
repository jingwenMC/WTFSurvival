package top.jingwenmc.wtfsurvival.object;

import org.bukkit.command.CommandSender;

public abstract class CommandItem {
    public abstract boolean onCommand(String[] args, CommandSender sender);
}
