package top.jingwenmc.wtfsurvival.util;

import top.jingwenmc.wtfsurvival.enums.LangItem;

public class LangUtil {
    static String selectedLang = "zh_CN";
    public static void setSelectedLang(String selectLang) {
        selectedLang = selectLang;
    }
    public String getRawMessage(LangItem langItem) {
        try {
            String root = selectedLang + "." + langItem.getValue();
            //TODO
            return "TODO";
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return null;
    }
}
