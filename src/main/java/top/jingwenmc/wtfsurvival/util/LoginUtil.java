package top.jingwenmc.wtfsurvival.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.games.LikeToGiveEffects;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginUtil {
    public static CookieStore cookieStore = new BasicCookieStore();
    public static String oauthKey;
    public static boolean locked = false;

    public static void startLogin(CommandSender sender) {
        if(locked) {
            MessageUtil.sendWrappedMessage(sender,LangItem.LOGIN_SOMEONE_IS_LOGGING_IN);
            return;
        }
        if(LikeToGiveEffects.isLoggedIn) {
            MessageUtil.sendWrappedMessage(sender,LangItem.LOGIN_ALREADY);
            return;
        }
        if(Util.isNoPerm(sender,"wtfs.login"))return;
        final boolean[] success = {false};
        final boolean[] scanned = {false};
                locked = true;
                //STEP1&2
                MessageUtil.sendWrappedMessage(sender, LangItem.LOGIN_GENERATE);
                String QRLink = getLoginQRCodeLink();
                MessageUtil.sendWrappedMessage(sender, LangItem.LOGIN_GENERATE_FINISH
                , new String[]{"%url%"}, new String[]{QRLink});
                //STEP 2&3
                new BukkitRunnable() {
                    int sec = 0;
                    @Override
                    public void run() {
                        while(true) {
                            if (sec == 150) {
                                success[0] = false;
                                break;
                            }
                            int query = query();
                            if (query == 1) {
                                if (!scanned[0]) {
                                    MessageUtil.sendWrappedMessage(sender, LangItem.LOGIN_SCANNED);
                                }
                                scanned[0] = true;
                            }
                            if (query == 2) {
                                success[0] = true;
                                MessageUtil.sendWrappedMessage(sender, LangItem.LOGIN_LOGGING_IN);
                                break;
                            }
                            if (query == 3) {
                                success[0] = false;
                                break;
                            }
                            sec++;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(!success[0]) {
                                    MessageUtil.sendWrappedMessage(sender,LangItem.LOGIN_FAIL);
                                    locked = false;
                                    return;
                                }
                                    MessageUtil.sendWrappedMessage(sender,LangItem.LOGIN_LOGGED_IN);
                                    locked = false;
                                    LikeToGiveEffects.isLoggedIn = true;
                            }
                        }.runTask(WTFSurvival.getInstance());
                    }
                }.runTaskAsynchronously(WTFSurvival.getInstance());
    }

    public static String getLoginQRCodeLink() {
        String loginQR = "";
        HttpGet httpGet = new HttpGet("http://passport.bilibili.com/qrcode/getLoginUrl");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = JSON.parseObject(result);
                oauthKey = jsonObject.getJSONObject("data").getString("oauthKey");
                loginQR = jsonObject.getJSONObject("data").getString("url");
            }
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return "https://cli.im/api/qrcode/code?text="+loginQR;
    }

    //0 - not ; 1 - scanned ; 2 - logged in ; 3 - other
    public static int query() {
        HttpPost httpPost = new HttpPost("http://passport.bilibili.com/qrcode/getLoginInfo");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("oauthKey",oauthKey));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            ExceptionUtil.print(e);
        }
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                JSONObject jsonObject = JSON.parseObject(result);
                if(!jsonObject.getBoolean("status")) {
                    if(jsonObject.getIntValue("data") == -4) return 0;
                    if(jsonObject.getIntValue("data") == -5) return 1;
                }
                else return 2;
            }
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return 3;
    }

    public static boolean verify() {
        HttpGet httpGet = new HttpGet("http://api.bilibili.com/x/space/upstat?mid=289155331");
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = JSON.parseObject(result);
                return jsonObject.getJSONObject("data") == null;
            }
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return false;
    }
}
