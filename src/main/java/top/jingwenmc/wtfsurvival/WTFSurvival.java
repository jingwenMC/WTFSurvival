package top.jingwenmc.wtfsurvival;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.jingwenmc.wtfsurvival.util.WorldUtil;

import java.util.logging.Level;

public final class WTFSurvival extends JavaPlugin {
    private static WTFSurvival instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().log(Level.INFO , "Hello,Debugger.");
        WorldUtil.createNewWorld("test");
        //Bukkit.getLogger().log(Level.INFO , "Test.");
    }

    @Override
    public void onDisable() {
        WorldUtil.deleteWorld("test");
        // Plugin shutdown logic
    }

    public static WTFSurvival getInstance() {
        return instance;
    }
}
