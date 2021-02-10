package top.jingwenmc.wtfsurvival.command;

import org.bukkit.command.CommandSender;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.object.CommandItem;
import top.jingwenmc.wtfsurvival.object.Game;
import top.jingwenmc.wtfsurvival.util.MessageUtil;
import top.jingwenmc.wtfsurvival.util.Util;

public class CMDend extends CommandItem {
    @Override
    public boolean onCommand(String[] args, CommandSender sender) {
        if(Util.isNoPerm(sender,"wtfs.admin"))return true;
        if(WTFSurvival.getInstance().getGameManager().getGames().length == 0)
            MessageUtil.sendWrappedMessage(sender, LangItem.GAME_CANT_END);
        for(Game game : WTFSurvival.getInstance().getGameManager().getGames())
        {
            WTFSurvival.getInstance().getGameManager().endGame(game.getGameID());
        }
        MessageUtil.sendWrappedMessage(sender,LangItem.GAME_END_SUCCESS);
        return true;
    }
}
