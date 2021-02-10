package top.jingwenmc.wtfsurvival.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import top.jingwenmc.wtfsurvival.enums.LangItem;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CheckUtil {
    public static void checkLang(Plugin plugin, String confName, String lang, FileConfiguration configuration) {
        try {
            InputStream defConfigStream = plugin.getResource(confName);
            assert defConfigStream != null;
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream,
                    StandardCharsets.UTF_8));
            for(LangItem item : LangItem.values()) {
                if(!configuration.isSet(lang+"."+item.getValue())) {
                    System.err.println("[WARNING] Checked one unsetted item: "+item.getValue()+" ,setting it to default...");
                    configuration.set(lang + "." + item.getValue(), defConfig.get("zh_CN." + item.getValue()));
                    configuration.save(new File(plugin.getDataFolder(),"lang.yml"));
                }
            }
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
    }
    public static void checkConf(Plugin plugin, String confName, FileConfiguration configuration) {
        try {
            InputStream defConfigStream = plugin.getResource(confName);
            assert defConfigStream != null;
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream,
                    StandardCharsets.UTF_8));
            for(String item : defConfig.getKeys(true)) {
                if(!configuration.isSet(item)) {
                    System.err.println("[WARNING] Checked one unsetted item: "+item+" ,setting it to default...");
                    configuration.set(item, defConfig.get(item));
                }
            }
            File dic = plugin.getDataFolder();
            configuration.save(new File(dic,confName));
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
    }
}
