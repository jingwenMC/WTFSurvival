package top.jingwenmc.wtfsurvival;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import top.jingwenmc.wtfsurvival.command.*;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.listener.PlayerInOutListeners;
import top.jingwenmc.wtfsurvival.manager.GameManager;
import top.jingwenmc.wtfsurvival.manager.SubCommandManager;
import top.jingwenmc.wtfsurvival.object.ConfigAccessor;
import top.jingwenmc.wtfsurvival.object.Game;
import top.jingwenmc.wtfsurvival.task.LikeUpdateListener;
import top.jingwenmc.wtfsurvival.util.CheckUtil;
import top.jingwenmc.wtfsurvival.util.ExceptionUtil;
import top.jingwenmc.wtfsurvival.util.MessageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public final class WTFSurvival extends JavaPlugin {
    public static final int configVersion = 1;
    private static WTFSurvival instance;
    private ConfigAccessor config;
    private ConfigAccessor lang;
    private SubCommandManager mainCommandManager;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().log(Level.INFO,"Loading Configuration and Language file...");
        getLogger().log(Level.INFO,"正在加载配置文件和语言文件...");
        config = new ConfigAccessor(this,"config.yml");
        lang = new ConfigAccessor(this,"lang.yml");
        config.saveDefaultConfig();
        lang.saveDefaultConfig();
        getLogger().log(Level.INFO,"Checking Config file...");
        getLogger().log(Level.INFO,"正在检查配置文件...");
        CheckUtil.checkConf(this,"config.yml",config.getConfig());
        MessageUtil.setSelectedLang(getConfig().getString("lang"));
        getLogger().log(Level.INFO,"Selected Language:"+MessageUtil.getSelectedLang());
        getLogger().log(Level.INFO,"所选语言："+MessageUtil.getSelectedLang());

        getLogger().log(Level.INFO,"Checking Language file...");
        getLogger().log(Level.INFO,"正在检查语言文件...");
        checkLang();
        LikeUpdateListener.setListen(WTFSurvival.getInstance().getConfig().getStringList("bili_uid"));
        MessageUtil.sendWrappedMessageToConsole(LangItem.CONSOLE_LOADING);
        mainCommandManager = new SubCommandManager();
        gameManager = new GameManager();

        mainCommandManager.register(new CMDhelp(),null);
        mainCommandManager.register(new CMDhelp(),"help");
        mainCommandManager.register(new CMDstart(),"start");
        mainCommandManager.register(new CMDend(),"end");
        mainCommandManager.register(new CMDreload(),"reload");
        mainCommandManager.register(new CMDreport(),"report");
        mainCommandManager.register(new CMDlogin(),"login");

        getCommand("wtfs").setExecutor(mainCommandManager);
        getCommand("wtfs").setTabCompleter(mainCommandManager);
        Bukkit.getPluginManager().registerEvents(new PlayerInOutListeners(),this);
        new LikeUpdateListener().runTaskTimer(this,15,15);
        MessageUtil.sendWrappedMessageToConsole(LangItem.CONSOLE_LOADING_FINISH);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public FileConfiguration getConfig() {
        return config.getConfig();
    }

    @Override
    public void saveDefaultConfig() {
        config.saveDefaultConfig();
    }

    @Override
    public void saveConfig() {
        config.saveConfig();
    }

    @Override
    public void reloadConfig() {
        config.reloadConfig();
    }

    public ConfigAccessor getLang() {
        return lang;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public static WTFSurvival getInstance() {
        return instance;
    }

    public SubCommandManager getMainCommandManager() {
        return mainCommandManager;
    }

    public void checkLang() {
        try {
            if (lang.getConfig().getInt(MessageUtil.getSelectedLang() + "." + "info.version") != 1) {
                getLogger().log(Level.SEVERE, "FATAL:Language file's version not correct");
                getLogger().log(Level.SEVERE, "致命错误：语言文件的版本不正确");
                getLogger().log(Level.SEVERE, "Do you updated you language file after you updated the plugin?");
                getLogger().log(Level.SEVERE, "更新插件后， 您是否更新了语言文件？");
                getLogger().log(Level.SEVERE, "We will regenerate a language file for you");
                getLogger().log(Level.SEVERE, "我们将会为您重新生成一份语言文件");
                lang.forceRename("lang-backup-" + System.currentTimeMillis() + ".yml");
                lang = new ConfigAccessor(this, "lang.yml");
                lang.saveDefaultConfig();
                lang.reloadConfig();
            }
            CheckUtil.checkLang(this, "lang.yml", MessageUtil.getSelectedLang(), lang.getConfig());
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
    }
}
