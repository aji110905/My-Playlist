package aji.myplaylist.Language;

import aji.myplaylist.Language.lang.en_us;
import aji.myplaylist.Language.lang.zh_cn;

import java.util.Map;

public class MyPlaylistLanguage {
    public static final Language DEFAULT_LANGUAGE = null;
    public static final Map<String, Language> LANGUAGE_MAP = Map.of(
            "en_us", new en_us(),
            "zh_cn", new zh_cn()
    );

    private Language language = DEFAULT_LANGUAGE;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(String code) {
        Language language = LANGUAGE_MAP.get(code);
        if (language == null) this.language = DEFAULT_LANGUAGE;
        else this.language = language;
    }
}