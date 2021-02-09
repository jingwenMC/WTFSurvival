package top.jingwenmc.wtfsurvival.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.enums.LangItem;

import java.util.Arrays;
import java.util.List;

public class MessageUtil {
    static String selectedLang = "zh_CN";

    public static void setSelectedLang(String selectLang) {
        selectedLang = selectLang;
    }

    public static String getSelectedLang() {
        return selectedLang;
    }

    public static String getRawMessage(LangItem langItem) {
       String root = selectedLang + "." + langItem.getValue();
       return getRawMessage(root);
    }

    public static List<String> getRawMessageAsList(LangItem langItem) {
        String root = selectedLang + "." + langItem.getValue();
        return Arrays.asList(getRawMessage(root).split(System.lineSeparator()));
    }

    public static void sendWrappedMessageToConsole(LangItem langItem) {
        sendWrappedMessage(Bukkit.getConsoleSender(), langItem, true);
    }
    public static void sendWrappedMessageToConsole(LangItem langItem, boolean prefix) {
        sendWrappedMessage(Bukkit.getConsoleSender(), langItem, prefix);
    }

    public static void sendWrappedMessage(CommandSender sender, LangItem langItem) {
        sendWrappedMessage(sender, langItem, true, new String[]{}, new String[]{});
    }

    public static void sendWrappedMessage(CommandSender[] senders, LangItem langItem) {
        for(CommandSender sender : senders)
        sendWrappedMessage(sender, langItem, true, new String[]{}, new String[]{});
    }

    public static void sendWrappedMessage(CommandSender sender, LangItem langItem, boolean prefix) {
        sendWrappedMessage(sender, langItem, prefix, new String[]{}, new String[]{});
    }

    public static void sendWrappedMessage(CommandSender[] senders, LangItem langItem, boolean prefix) {
        for(CommandSender sender : senders)
        sendWrappedMessage(sender, langItem, prefix, new String[]{}, new String[]{});
    }

    public static void sendWrappedMessage(CommandSender sender, LangItem langItem, String[] replaceFrom , String[] replaceTo) {
        sendWrappedMessage(sender, langItem.getValue(), replaceFrom, replaceTo, true);
    }

    public static void sendWrappedMessage(CommandSender[] senders, LangItem langItem, String[] replaceFrom , String[] replaceTo) {
        for(CommandSender sender : senders)
        sendWrappedMessage(sender, langItem.getValue(), replaceFrom, replaceTo, true);
    }

    public static void sendWrappedMessageToConsole(LangItem langItem,boolean prefix, String[] replaceFrom , String[] replaceTo) {
        sendWrappedMessage(Bukkit.getConsoleSender(), langItem, prefix, replaceFrom, replaceTo);
    }

    public static void sendWrappedMessage(CommandSender sender, LangItem langItem, boolean prefix, String[] replaceFrom , String[] replaceTo) {
        sendWrappedMessage(sender, langItem.getValue(), replaceFrom, replaceTo, prefix);
    }

    public static void sendWrappedMessage(CommandSender[] senders, LangItem langItem, boolean prefix, String[] replaceFrom , String[] replaceTo) {
        for(CommandSender sender : senders)
        sendWrappedMessage(sender, langItem.getValue(), replaceFrom, replaceTo, prefix);
    }


    private static String getRawMessage(String rootWithLang) {
        try {
            StringBuilder r = new StringBuilder();
            if (getConfig().isList(rootWithLang)) {
                for (String s : getConfig().getStringList(rootWithLang)) {
                    r.append(s).append(System.lineSeparator());
                }
            } else {
                r.append(getConfig().getString(rootWithLang));
            }
            return r.toString();
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return null;
    }

    private static void sendWrappedMessage(CommandSender sender, String rootWithLang, String[] replaceFrom , String[] replaceTo, boolean prefix) {
        try {
            if (getConfig().isList(rootWithLang)) {
                for (String s : getConfig().getStringList(rootWithLang)) {
                    int i = 0;
                    for(String ss : replaceFrom) {
                        s = s.replaceAll(ss,replaceTo[i]);
                        i++;
                    }
                    if(prefix)
                    s = ChatColor.translateAlternateColorCodes('&',getPrefix()+s);
                    else s = ChatColor.translateAlternateColorCodes('&',s);
                    sender.sendMessage(s);
                }
            } else {
                String message = getConfig().getString(rootWithLang);
                assert message != null;
                int i = 0;
                for(String ss : replaceFrom) {
                    message = message.replaceAll(ss,replaceTo[i]);
                    i++;
                }
                message = ChatColor.translateAlternateColorCodes('&',getPrefix()+message);
                sender.sendMessage(message);
            }
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
    }

    private static FileConfiguration getConfig() {
        return WTFSurvival.getInstance().getConfig();
    }

    private static String getPrefix() {
        try {
            return WTFSurvival.getInstance().getConfig().getString(LangItem.PREFIX.getValue());
        } catch (Throwable e) {
            if(WTFSurvival.getInstance().getConfig().getString(LangItem.PREFIX.getValue()) == null) {
                WTFSurvival.getInstance().checkLang();
                return WTFSurvival.getInstance().getConfig().getString(LangItem.PREFIX.getValue());
            }
            else {
                ExceptionUtil.print(e);
            }
        }
        return null;
    }
}
