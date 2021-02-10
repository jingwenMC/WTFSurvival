package top.jingwenmc.wtfsurvival.command;

import org.bukkit.command.CommandSender;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.object.CommandItem;
import top.jingwenmc.wtfsurvival.util.MessageUtil;

public class CMDhelp implements CommandItem {
    @Override
    public boolean onCommand(String[] args, CommandSender sender) {
        MessageUtil.sendWrappedMessage(sender, LangItem.SERVER_HELP);
        return true;
    }
}
