package top.jingwenmc.wtfsurvival.task;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import top.jingwenmc.wtfsurvival.games.LikeToGiveItems;

public class LikeUpdateListener extends BukkitRunnable {
    int before = 0;
    HttpGet request = new HttpGet("http://api.bilibili.com/x/space/upstat");
    @Override
    public void run() {
        if(!LikeToGiveItems.isLoggedIn) return;

    }
}
