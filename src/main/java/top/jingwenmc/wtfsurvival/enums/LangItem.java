package top.jingwenmc.wtfsurvival.enums;

public enum LangItem {
    INFO_VERSION("info.version"),

    PREFIX("prefix"),

    CONSOLE_LOADING("console.loading"),
    CONSOLE_LOADING_FINISH("console.loading_finish"),

    SERVER_NO_CMD("server.no_cmd")
    ;
    private final String value;
    LangItem(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
