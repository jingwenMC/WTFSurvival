package top.jingwenmc.wtfsurvival.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import top.jingwenmc.wtfsurvival.event.LikeUpdateEvent;
import top.jingwenmc.wtfsurvival.games.LikeToGiveEffects;
import top.jingwenmc.wtfsurvival.util.ExceptionUtil;
import top.jingwenmc.wtfsurvival.util.LoginUtil;

public class LikeUpdateListener extends BukkitRunnable {
    int before = 0;
    static HttpGet request = new HttpGet("http://api.bilibili.com/x/space/upstat?mid="+"289155331");
    @Override
    public void run() {
        if(!LikeToGiveEffects.isLoggedIn) return;
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(LoginUtil.cookieStore).build();
             CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                String result = EntityUtils.toString(httpEntity);
                JSONObject jsonObject = JSON.parseObject(result);
            if(!jsonObject.getString("message").equals("0")) return;
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            int now = jsonObject1.getIntValue("likes");
            if(now > before) {
                Bukkit.getPluginManager().callEvent(new LikeUpdateEvent(before,now));
            }
            before = now;
            }
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
    }
    public static void setListen(String listenTo) {
        request = new HttpGet("http://api.bilibili.com/x/space/upstat?mid="+listenTo);
    }
}
