package top.jingwenmc.wtfsurvival.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.jingwenmc.wtfsurvival.enums.LangItem;

import java.util.Random;

public class Util {
    public static boolean isNoPerm(CommandSender sender , String permission) {
        if(!sender.hasPermission(permission)) {
            MessageUtil.sendWrappedMessage(sender, LangItem.SERVER_NO_PERM);
            return true;
        }
        return false;
    }

    public static PotionEffect setEffectToPlayers(Player[] players) {
        PotionEffectType type = getRandomType(new Random());
        PotionEffect effect = new PotionEffect(type,15*20,0);
        for(Player player : players) {
            player.addPotionEffect(effect);
        }
        return effect;
    }

    public static PotionEffectType getRandomType(Random random) {
        return PotionEffectType.values()[random.nextInt(PotionEffectType.values().length)];
    }
}
