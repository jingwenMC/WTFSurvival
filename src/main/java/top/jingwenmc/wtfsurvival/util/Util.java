package top.jingwenmc.wtfsurvival.util;

import org.bukkit.command.CommandSender;
import top.jingwenmc.wtfsurvival.enums.LangItem;

public class Util {
    public static boolean isNoPerm(CommandSender sender , String permission) {
        if(!sender.hasPermission(permission)) {
            MessageUtil.sendWrappedMessage(sender, LangItem.SERVER_NO_PERM);
            return true;
        }
        return false;
    }
}
