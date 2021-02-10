package top.jingwenmc.wtfsurvival.object;

import org.bukkit.entity.Player;

public abstract class GameplayRule {
    //todo: add disconnect detect
    String gameplayRuleName;
    Game game;

    public GameplayRule(String gameplayRuleName) {
        this.gameplayRuleName = gameplayRuleName;
    }

    protected Game getGame() {
        return game;
    }

    protected void setGame(Game game) {
        this.game = game;
    }

    protected abstract void onStart();

    public abstract void loop();

    protected abstract void onEnd();

    protected abstract void onDestroy();

    protected abstract void onDisconnect(Player player);

    public String getGameplayRuleName() {
        return gameplayRuleName;
    }
}
