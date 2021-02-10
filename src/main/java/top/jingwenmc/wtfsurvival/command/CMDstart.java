package top.jingwenmc.wtfsurvival.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.games.LikeToGiveEffects;
import top.jingwenmc.wtfsurvival.object.CommandItem;
import top.jingwenmc.wtfsurvival.util.MessageUtil;
import top.jingwenmc.wtfsurvival.util.Util;

public class CMDstart implements CommandItem {
    @Override
    public boolean onCommand(String[] args, CommandSender sender) {
        if(Util.isNoPerm(sender,"wtfs.admin"))return true;
        if(WTFSurvival.getInstance().getGameManager().getGames().length != 0)
            MessageUtil.sendWrappedMessage(sender, LangItem.GAME_STARTED);
        WTFSurvival.getInstance().getGameManager()
                .startNewGame(Bukkit.getOnlinePlayers().toArray(new Player[0]),new LikeToGiveEffects());
        return true;
    }
}
