package top.jingwenmc.wtfsurvival.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.util.MessageUtil;

public class PlayerInOutListeners implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(MessageUtil.getRawMessage(LangItem.PREFIX)+MessageUtil.getRawMessage(LangItem.GAME_JOIN)
        .replaceAll("%player%",event.getPlayer().getName()));
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent event) {
        event.setQuitMessage(MessageUtil.getRawMessage(LangItem.PREFIX)+MessageUtil.getRawMessage(LangItem.GAME_LEFT)
                .replaceAll("%player%",event.getPlayer().getName()));
        WTFSurvival.getInstance().getGameManager().playerDisconnect(event.getPlayer());
    }
}
