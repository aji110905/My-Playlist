package aji.myplaylist.Language.lang;

import aji.myplaylist.Language.Language;

import java.util.HashMap;
import java.util.Map;

public class en_us implements Language {
    public static final Map<String, String> TRANSLATIONS;
    static {
        TRANSLATIONS = new HashMap<>();
    }

    @Override
    public Map<String, String> getTranslations() {
        return TRANSLATIONS;
    }
}
