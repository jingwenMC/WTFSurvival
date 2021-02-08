package top.jingwenmc.wtfsurvival.object;
/*
 * Copyright (C) 2012
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import java.io.*;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import top.jingwenmc.wtfsurvival.util.ExceptionUtil;

public class ConfigAccessor {

    private String fileName;
    private final JavaPlugin plugin;

    private final File configFile;
    private FileConfiguration fileConfiguration;

    public ConfigAccessor(JavaPlugin plugin, String fileName) {
        this(plugin,plugin.getDataFolder(),fileName);
    }

    public ConfigAccessor(JavaPlugin plugin, File folder, String fileName) {
        if (plugin == null)
            throw new IllegalArgumentException("plugin cannot be null");
        this.plugin = plugin;
        this.fileName = fileName;
        if (folder == null)
            throw new IllegalStateException();
        this.configFile = new File(folder, fileName);
    }

    public void forceRename(String name) {
        fileName = name;
        if(!configFile.renameTo(new File(plugin.getDataFolder(),name)))ExceptionUtil.print(new IllegalStateException("Cannot Force Rename"));
        reloadConfig();
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            fileConfiguration.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            this.reloadConfig();
        }
        return fileConfiguration;
    }

    public void saveConfig() {
        if (fileConfiguration != null && configFile != null) {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
                ExceptionUtil.print(ex);
            }
        }
    }

    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            this.plugin.saveResource(fileName, false);
        }
    }

    public void saveDefaultConfig(boolean manual)
    {
        if(!manual)saveDefaultConfig();
        else
        {
            if(!configFile.exists()) {
                (new File(configFile.getParent())).mkdirs();
                try {
                    if(!configFile.createNewFile())throw new IllegalStateException();
                } catch (IOException | IllegalStateException e) {
                    ExceptionUtil.print(e);
                }
            }
            try(OutputStream out = new FileOutputStream(configFile) ; InputStream inputStream = plugin.getResource(fileName)) {
                byte[] buf = new byte[1024];
                int len;
                while (true) {
                    assert inputStream != null;
                    if (!((len = inputStream.read(buf)) > 0)) break;
                    out.write(buf, 0, len);
                }
            } catch (IOException e) {
                ExceptionUtil.print(e);
            }
        }
    }

}