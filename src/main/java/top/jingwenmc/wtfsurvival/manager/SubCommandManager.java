package top.jingwenmc.wtfsurvival.manager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.object.CommandItem;
import top.jingwenmc.wtfsurvival.util.ExceptionUtil;
import top.jingwenmc.wtfsurvival.util.MessageUtil;

import java.util.*;

public class SubCommandManager implements CommandExecutor, TabCompleter {
    Map<String, CommandItem> map = new HashMap<>();
    List<String> strings = new ArrayList<>();
    public boolean sendCommand(String[] args, CommandSender sender)
    {
        try {
            if (args.length == 0 || !map.containsKey(args[0])) {
                if (map.containsKey(null)) {
                    return map.get(null).onCommand(new String[]{}, sender);
                } else return false;
            }
            for (String s : map.keySet()) {
                if (args[0].equalsIgnoreCase(s)) {
                    List<String> rt = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
                    return map.get(s).onCommand(rt.toArray(new String[0]), sender);
                }
            }
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return false;
    }
    public void register(CommandItem command,String root)
    {
        try {
            if (isRegistered(root)) throw new IllegalArgumentException("Already Registered");
            map.put(root, command);
            if (root != null) strings.add(root);
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
    }
    public void unregister(String root)
    {
        try {
            if (isRegistered(root)) {
                if (root != null) map.remove(root);
                strings.remove(root);
            } else throw new IllegalArgumentException("Not Registered");
        }catch (Throwable e) {
            ExceptionUtil.print(e);
        }
    }
    public boolean isRegistered(String root)
    {
        return map.containsKey(root);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isSuccess = sendCommand(args,sender);
        if(!isSuccess) MessageUtil.sendWrappedMessage(sender, LangItem.SERVER_NO_CMD);
        return true;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        boolean isSuccess = sendCommand(args,sender);
        if(!isSuccess) MessageUtil.sendWrappedMessage(sender, LangItem.SERVER_NO_CMD);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        try {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                StringUtil.copyPartialMatches(args[0], strings, completions);
                Collections.sort(completions);
                return completions;
            }
            return null;
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return null;
    }
}
