package top.jingwenmc.wtfsurvival.object;

import org.bukkit.entity.Player;
import top.jingwenmc.wtfsurvival.enums.GameStatus;
import top.jingwenmc.wtfsurvival.enums.LangItem;
import top.jingwenmc.wtfsurvival.util.MessageUtil;

import java.util.Arrays;
import java.util.List;

public class Game {
    //todo: reconnect
    GameplayRule gameplayRule;
    List<Player> players;
    String gameID;
    boolean canDestroy = false;
    GameStatus status = GameStatus.NOT_STARTED;

    public Game(Player[] players, GameplayRule gameplayRule) {
        this.players = Arrays.asList(players);
        this.gameplayRule = gameplayRule;
        this.gameID = "#" + (System.currentTimeMillis() - 1612842129332L);
        gameplayRule.setGame(this);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getGameID() {
        return gameID;
    }

    public GameplayRule getGameplayRule() {
        return gameplayRule;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void start() {
        StringBuilder playersName = new StringBuilder(" ");
        for(Player player : players) {
            playersName.append(player.getName());
            playersName.append(" ");
        }
        for(Player player : players) {
            MessageUtil.sendWrappedMessage(player, LangItem.GAME_START
                    ,new String[]{"%players%"},new String[]{playersName.toString()});
        }
        gameplayRule.onStart();
        status = GameStatus.STARTED;
    }
    public void end() {
        status = GameStatus.ENDING;
        for(Player player : players) {
            MessageUtil.sendWrappedMessage(player, LangItem.GAME_END
                    ,new String[]{"%id%"},new String[]{getGameID()});
        }
        gameplayRule.onEnd();
        canDestroy = true;
    }
    public void destroy() {
        //Destroy after all player leaves the game
        if(canDestroy)gameplayRule.onDestroy();
        status = GameStatus.DESTROYED;
    }
    public void disconnect(Player player) {
        players.remove(player);
        gameplayRule.onDisconnect(player);
    }
}
