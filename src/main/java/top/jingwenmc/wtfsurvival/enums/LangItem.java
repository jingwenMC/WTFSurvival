package top.jingwenmc.wtfsurvival.enums;

public enum LangItem {
    INFO_VERSION("info.version"),

    PREFIX("prefix"),

    CONSOLE_LOADING("console.loading"),
    CONSOLE_LOADING_FINISH("console.loading_finish"),

    SERVER_NO_CMD("server.no_cmd"),
    SERVER_NO_PERM("server.no_perm"),
    SERVER_RELOAD_FINISH("server.reload_finish"),
    SERVER_HELP("server.help"),

    GAME_START("game.start"),
    GAME_STARTED("game.started"),
    GAME_END("game.end"),
    GAME_END_SUCCESS("game.end_success"),
    GAME_CANT_END("game.cant_end"),
    GAME_JOIN("game.join"),
    GAME_LEFT("game.left"),
    GAME_NEW_LIKE("game.new_like"),

    LOGIN_SOMEONE_IS_LOGGING_IN("login.someone_is_logging_in"),
    LOGIN_FAIL("login.fail"),
    LOGIN_ALREADY("login.already"),
    LOGIN_NOT_LOGGED_IN("login.not_logged_in"),
    LOGIN_GENERATE("login.generate"),
    LOGIN_GENERATE_FINISH("login.generate_finish"),
    LOGIN_SCANNED("login.scanned"),
    LOGIN_LOGGING_IN("login.logging_in"),
    LOGIN_LOGGED_IN("login.logged_in"),
    ;
    private final String value;
    LangItem(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
