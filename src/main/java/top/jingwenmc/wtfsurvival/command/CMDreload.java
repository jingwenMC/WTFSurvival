package top.jingwenmc.wtfsurvival.command;

import org.bukkit.command.CommandSender;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.object.CommandItem;
import top.jingwenmc.wtfsurvival.object.Game;
import top.jingwenmc.wtfsurvival.task.LikeUpdateListener;
import top.jingwenmc.wtfsurvival.util.CheckUtil;
import top.jingwenmc.wtfsurvival.util.ExceptionUtil;
import top.jingwenmc.wtfsurvival.util.MessageUtil;
import top.jingwenmc.wtfsurvival.util.Util;

import java.util.logging.Level;

public class CMDreload implements CommandItem {
    @Override
    public boolean onCommand(String[] args, CommandSender sender) {
        //3 needs to reload
        try {
            if (Util.isNoPerm(sender, "wtfs.admin")) return true;
            for (Game game : WTFSurvival.getInstance().getGameManager().getGames()) {
                WTFSurvival.getInstance().getGameManager().endGame(game.getGameID());
            }
            WTFSurvival.getInstance().getLang().reloadConfig();
            WTFSurvival.getInstance().reloadConfig();
            WTFSurvival.getInstance().getLogger().log(Level.INFO,"Checking Config file...");
            WTFSurvival.getInstance().getLogger().log(Level.INFO,"正在检查配置文件...");
            CheckUtil.checkConf(WTFSurvival.getInstance(),"config.yml", WTFSurvival.getInstance().getConfig());
            MessageUtil.setSelectedLang( WTFSurvival.getInstance().getConfig().getString("lang"));
            WTFSurvival.getInstance().getLogger().log(Level.INFO,"Selected Language:"+MessageUtil.getSelectedLang());
            WTFSurvival.getInstance().getLogger().log(Level.INFO,"所选语言："+MessageUtil.getSelectedLang());
            WTFSurvival.getInstance().getLogger().log(Level.INFO,"Checking Language file...");
            WTFSurvival.getInstance().getLogger().log(Level.INFO,"正在检查语言文件...");
            WTFSurvival.getInstance().checkLang();
            LikeUpdateListener.setListen(WTFSurvival.getInstance().getConfig().getString("bili_uid"));
            return true;
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return false;
    }
}
