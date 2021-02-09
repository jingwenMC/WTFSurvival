package top.jingwenmc.wtfsurvival.object;

public abstract class GameplayRule {
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

    protected abstract void registerListeners();

    protected abstract void onStart();

    public abstract void loop();

    protected abstract void onEnd();

    protected abstract boolean shouldEnd();

    protected abstract void onDestroy();
}
