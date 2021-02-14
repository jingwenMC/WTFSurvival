package top.jingwenmc.wtfsurvival.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.games.LikeToGiveEffects;

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
        PotionEffect effect = new PotionEffect(type,
                WTFSurvival.getInstance().getConfig().getInt("potion_time"),
                WTFSurvival.getInstance().getConfig().getInt("potion_level")+1);
        for(Player player : players) {
            player.addPotionEffect(effect);
        }
        return effect;
    }

    public static PotionEffectType getRandomType(Random random) {
        PotionEffectType type =  PotionEffectType.values()[random.nextInt(PotionEffectType.values().length)];
        if(type == PotionEffectType.HARM || type == PotionEffectType.WITHER) {
            type = random.nextInt(2) == 0 ? PotionEffectType.POISON : PotionEffectType.HEAL;
        }
        return type;
    }

    public static String getUpName(String uid) {
        if(!LikeToGiveEffects.isLoggedIn)return null;
        HttpGet httpGet = new HttpGet("http://api.bilibili.com/x/space/acc/info?mid="+uid);
        try(CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(LoginUtil.cookieStore).build();
            CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            JSONObject object = JSON.parseObject(EntityUtils.toString(entity));
            assert object.getJSONObject("data") != null;
            return object.getJSONObject("data").getString("name");
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return null;
    }
}
