package top.jingwenmc.wtfsurvival.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import top.jingwenmc.wtfsurvival.WTFSurvival;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ReportUtil {
    public static void generateDiagnoseFile(File folder) {
        File file = new File(folder, "report.txt");
        try (FileWriter out = new FileWriter(file,false); PrintWriter writer = new PrintWriter(out)) {
            if (!file.exists()) if(!file.createNewFile()) {
                throw new IllegalStateException("Cannot Create File");
            }
            if (!file.canWrite()) throw new IOException("report.txt is not writable");
            writer.println("[Report] Generated On: "+new SimpleDateFormat().format(new Date()));
            writer.println();
            writer.println("SECTION 1 : PLUGIN INFORMATION");
            writer.println();
            writer.println("Plugin Name: "+WTFSurvival.getInstance().getDescription().getName());
            writer.println("Plugin Version: "+WTFSurvival.getInstance().getDescription().getVersion());
            writer.println("Plugin API Version: "+WTFSurvival.getInstance().getDescription().getAPIVersion());
            writer.println("Plugin Data Folder: "+WTFSurvival.getInstance().getDataFolder().toString());
            writer.println();
            writer.println("SECTION 2 : SERVER INFORMATION");
            writer.println();
            writer.println("Server Version: "+ Bukkit.getVersion());
            writer.println("Bukkit Version: "+ Bukkit.getBukkitVersion());
            writer.println("Installed Plugins: ");
            PluginManager pluginManager = Bukkit.getPluginManager();
            writer.println("TOTAL: "+pluginManager.getPlugins().length);
            int i = 0;
            for(Plugin plugin : pluginManager.getPlugins()) {
                i++;
                writer.println("#"+i+": ");
                writer.println("Name: "+plugin.getName());
                writer.println("Authors: "+ Arrays.toString(plugin.getDescription().getAuthors().toArray()));
                writer.println("Plugin Version: "+plugin.getDescription().getVersion());
                writer.println("Website: "+plugin.getDescription().getWebsite());
                writer.println("Depend: H: "+ Arrays.toString(plugin.getDescription().getDepend().toArray())
                +" / S: "+ Arrays.toString(plugin.getDescription().getSoftDepend().toArray())
                        +" / LB: "+ Arrays.toString(plugin.getDescription().getLoadBefore().toArray()));
                writer.println("Enabled: "+plugin.isEnabled());
            }
            writer.println();
            writer.println("SECTION 3 : SYSTEM INFORMATION");
            writer.println();
            writer.println("System Java Version: "+System.getProperty("java.version"));
            writer.println("System Java Vendor: "+System.getProperty("java.vendor"));
            writer.println("System Java Home: "+System.getProperty("java.home"));
            writer.println("System OS Name: "+System.getProperty("os.name"));
            writer.println("System OS Arch: "+System.getProperty("os.arch"));
            writer.println("System OS Version: "+System.getProperty("os.version"));
            writer.println("Running Dir: "+System.getProperty("user.dir"));

        } catch (IOException | IllegalStateException exception) {
            System.err.println("意外的错误：生成报告时无法创建/写入 report.txt");
            System.err.println("UNEXPECTED ERROR:Cannot Create/Write errors.txt while generating a report");
            System.err.println("如果您的环境一切正常，请报告给开发人员。");
            System.err.println("If everything is NORMAL in your environment, please report to the developer.");
            ExceptionUtil.handle(exception);
        } catch (NullPointerException exception) {
            System.err.println("意外的错误：NPE");
            System.err.println("UNEXPECTED ERROR:NPE");
            System.err.println("如果您的环境一切正常，请报告给开发人员。");
            System.err.println("If everything is NORMAL in your environment, please report to the developer.");
            ExceptionUtil.handle(exception);
        }
    }
}
