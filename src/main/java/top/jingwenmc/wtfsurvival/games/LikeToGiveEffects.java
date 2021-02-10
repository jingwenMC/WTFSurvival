package top.jingwenmc.wtfsurvival.games;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.enums.GameStatus;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.event.LikeUpdateEvent;
import top.jingwenmc.wtfsurvival.object.GameplayRule;
import top.jingwenmc.wtfsurvival.util.MessageUtil;
import top.jingwenmc.wtfsurvival.util.Util;

public class LikeToGiveEffects extends GameplayRule implements Listener {
    public static boolean isLoggedIn = false;
    boolean shouldEnd = false;

    public LikeToGiveEffects() {
        super("LikeToGiveEffects");
    }

    @Override
    protected void onStart() {
        Bukkit.getPluginManager().registerEvents(this,WTFSurvival.getInstance());
        if(!isLoggedIn) {
            MessageUtil.sendWrappedMessage(getGame().getPlayers().toArray(new Player[0]), LangItem.LOGIN_NOT_LOGGED_IN);
            shouldEnd = true;
            getGame().end();
            getGame().destroy();
        }
    }

    @Override
    public void loop() {
        //Now, didn't do anything(Using Listener)
    }

    @Override
    protected void onEnd() {
        //Manual End
        getGame().destroy();
    }

    @Override
    protected void onDestroy() {
        //Didn't do anything now
    }

    @Override
    protected void onDisconnect(Player player) {
        //
    }

    @EventHandler
    public void onUpdate(LikeUpdateEvent event) {
        MessageUtil.sendWrappedMessage(getGame().getPlayers().toArray(new Player[0]), LangItem.GAME_NEW_LIKE);
        if(this.getGame().getStatus().equals(GameStatus.DESTROYED))return;
        Util.setEffectToPlayers(getGame().getPlayers().toArray(new Player[0]));
    }
}
