package top.jingwenmc.wtfsurvival.games;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.object.GameplayRule;

public class LikeToGiveItems extends GameplayRule implements Listener {
    public static boolean isLoggedIn = false;

    public LikeToGiveItems() {
        super("LikeToGiveItems");
    }

    @Override
    protected void registerListeners() {
        Bukkit.getPluginManager().registerEvents(this,WTFSurvival.getInstance());
    }

    @Override
    protected void onStart() {

    }

    @Override
    public void loop() {

    }

    @Override
    protected void onEnd() {

    }

    @Override
    protected boolean shouldEnd() {
        return false;
    }

    @Override
    protected void onDestroy() {

    }

}
