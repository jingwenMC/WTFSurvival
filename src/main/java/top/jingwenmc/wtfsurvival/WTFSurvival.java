package top.jingwenmc.wtfsurvival;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class WTFSurvival extends JavaPlugin {
    private static WTFSurvival instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getLogger().log(Level.INFO , "Hello,Debugger.");
        //Bukkit.getLogger().log(Level.INFO , "Test.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static WTFSurvival getInstance() {
        return instance;
    }
}
