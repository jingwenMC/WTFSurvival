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
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.event.LikeUpdateEvent;
import top.jingwenmc.wtfsurvival.games.LikeToGiveEffects;
import top.jingwenmc.wtfsurvival.util.ExceptionUtil;
import top.jingwenmc.wtfsurvival.util.LoginUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikeUpdateListener extends BukkitRunnable {
    //int before = 0;
    static Map<String , HttpGet> requests = new HashMap<>();
    static Map<String , Integer> beforeMap = new HashMap<>();
    @Override
    public void run() {
        if(!LikeToGiveEffects.isLoggedIn) return;
        for(String uid : requests.keySet()) {
            HttpGet request = requests.get(uid);
            try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(LoginUtil.cookieStore).build();
                 CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    String result = EntityUtils.toString(httpEntity);
                    JSONObject jsonObject = JSON.parseObject(result);
                    if (!jsonObject.getString("message").equals("0")) return;
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    Integer now = jsonObject1.getInteger("likes");
                    Integer before = beforeMap.get(uid);
                    if (now > before) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Bukkit.getPluginManager().callEvent(new LikeUpdateEvent(uid, before, now));
                            }
                        }.runTask(WTFSurvival.getInstance());
                    }
                        beforeMap.remove(uid);
                        beforeMap.put(uid, now);
                    }
            } catch (Throwable e) {
                ExceptionUtil.print(e);
            }
        }
    }
    public static void setListen(List<String> listenTo) {
        requests.clear();
        for(String listen : listenTo) {
            requests.put(listen,new HttpGet("http://api.bilibili.com/x/space/upstat?mid="+listen));
            beforeMap.put(listen,0);
        }
    }
}
