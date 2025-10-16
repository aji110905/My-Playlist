package aji.myplaylist.Language.element;

public class LanguageHelper {
    public static LanguageNode node(String key){
        return new LanguageNode(key);
    }

    public static LanguageValue value(String key){
        return new LanguageValue(key);
    }
}
