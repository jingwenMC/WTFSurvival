import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.junit.Test;
import top.jingwenmc.wtfsurvival.util.CheckUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class CheckUtilUnitTest {
    @Test
    public void testConfCheck() {
        Plugin testPlugin = new Plugin() {
            @Override
            public File getDataFolder() {
                return new File(System.getProperty("user.dir"),"test_run_dir");
            }

            @Override
            public PluginDescriptionFile getDescription() {
                return null;
            }

            @Override
            public FileConfiguration getConfig() {
                return null;
            }

            @Override
            public InputStream getResource(String filename) {
                return CheckUtilUnitTest.class.getResourceAsStream(filename);
            }

            @Override
            public void saveConfig() {

            }

            @Override
            public void saveDefaultConfig() {

            }

            @Override
            public void saveResource(String resourcePath, boolean replace) {

            }

            @Override
            public void reloadConfig() {

            }

            @Override
            public PluginLoader getPluginLoader() {
                return null;
            }

            @Override
            public Server getServer() {
                return null;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void onDisable() {

            }

            @Override
            public void onLoad() {

            }

            @Override
            public void onEnable() {

            }

            @Override
            public boolean isNaggable() {
                return false;
            }

            @Override
            public void setNaggable(boolean canNag) {

            }

            @Override
            public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
                return null;
            }

            @Override
            public Logger getLogger() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                return false;
            }

            @Override
            public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
                return null;
            }
        };
        CheckUtil.checkConf(testPlugin,"config.yml"
                ,YamlConfiguration.loadConfiguration
                        (new InputStreamReader(getClass().getResourceAsStream("conf_test.yml"))));
        YamlConfiguration expected = YamlConfiguration.loadConfiguration(new File(testPlugin.getDataFolder(),"config.yml"));
        YamlConfiguration actual = YamlConfiguration.loadConfiguration(new InputStreamReader(getClass().getResourceAsStream("config.yml")));
        assertEquals(expected.getKeys(true),actual.getKeys(true));
        new File(System.getProperty("user.dir"),"test_run_dir").delete();
    }
}
