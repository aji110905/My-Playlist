package aji.myplaylist.language.element;

public class LanguageElementHelper {
    public static LanguageNode node(String key){
        return new LanguageNode(key);
    }

    public static LanguageValue value(String key){
        return new LanguageValue(key);
    }
}
