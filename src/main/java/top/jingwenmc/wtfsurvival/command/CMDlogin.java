package top.jingwenmc.wtfsurvival.command;

import org.bukkit.command.CommandSender;
import top.jingwenmc.wtfsurvival.object.CommandItem;
import top.jingwenmc.wtfsurvival.util.LoginUtil;
import top.jingwenmc.wtfsurvival.util.Util;

public class CMDlogin implements CommandItem {
    @Override
    public boolean onCommand(String[] args, CommandSender sender) {
        if (Util.isNoPerm(sender, "wtfs.admin")) return true;
        LoginUtil.startLogin(sender);
        return true;
    }
}
