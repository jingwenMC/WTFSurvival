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
    //K: Player's name  V: Game ID
    Map<String , String> playerMap = new HashMap<>();

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
        for(Player player : players) {
            playerMap.put(player.getName(),game.getGameID());
        }
        return game.getGameID();
    }

    public boolean endGame(String gameID) {
        if(!gameMap.containsKey(gameID))return false;
        gameMap.get(gameID).end();
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

    public void playerDisconnect(Player player) {
        //todo: improve
        if(playerMap.containsKey(player.getName())) {
            getGame(playerMap.get(player.getName())).disconnect(player);
            playerMap.remove(player.getName());
        }
    }

    public Game[] getGames() {
        return gameMap.values().toArray(new Game[0]);
    }
}
