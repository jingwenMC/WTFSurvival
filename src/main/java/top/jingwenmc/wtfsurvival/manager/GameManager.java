package top.jingwenmc.wtfsurvival.manager;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import top.jingwenmc.wtfsurvival.WTFSurvival;
import top.jingwenmc.wtfsurvival.object.Game;
import top.jingwenmc.wtfsurvival.object.GameplayRule;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    Map<String , Game> gameMap = new HashMap<>();
    Map<String , BukkitTask> taskMap = new HashMap<>();

    public String startNewGame(Player[] players, GameplayRule rule) {
        Game game = new Game(players , rule);
        gameMap.put(game.getGameID(), game);
        game.start();
        taskMap.put(game.getGameID(),new BukkitRunnable() {
            @Override
            public void run() {
                game.getGameplayRule().loop();
            }
        }.runTaskTimerAsynchronously(WTFSurvival.getInstance(),0,1));
        return game.getGameID();
    }

    public boolean endGame(String gameID) {
        if(!gameMap.containsKey(gameID))return false;
        gameMap.get(gameID).tryToEnd();
        return true;
    }

    public boolean deleteGame(String gameID) {
        if(!gameMap.containsKey(gameID))return false;
        gameMap.get(gameID).destroy();
        gameMap.remove(gameID);
        taskMap.get(gameID).cancel();
        taskMap.remove(gameID);
        return true;
    }

    public Game getGame(String gameID) {
        if(!gameMap.containsKey(gameID))return null;
        return gameMap.get(gameID);
    }
}
