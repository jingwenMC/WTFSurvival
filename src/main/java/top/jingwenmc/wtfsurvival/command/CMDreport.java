package top.jingwenmc.wtfsurvival.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.object.CommandItem;
import top.jingwenmc.wtfsurvival.util.ReportUtil;
import top.jingwenmc.wtfsurvival.util.Util;

public class CMDreport implements CommandItem {
    @Override
    public boolean onCommand(String[] args, CommandSender sender) {
        if (Util.isNoPerm(sender, "wtfs.admin")) return true;
        ReportUtil.generateDiagnoseFile(WTFSurvival.getInstance().getDataFolder());
        sender.sendMessage(ChatColor.GREEN+"[WTFS] 已在插件目录生成文件 report.txt");
        sender.sendMessage(ChatColor.GREEN+"[WTFS] File report.txt has been generated in the plugin directory");
        return true;
    }
}
