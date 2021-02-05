package top.jingwenmc.wtfsurvival.enums;

public enum LangItem {
    PREFIX("prefix");

    private final String value;
    LangItem(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
