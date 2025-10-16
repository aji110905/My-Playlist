package aji.myplaylist.translation;

import aji.myplaylist.MyPlaylist;
import aji.myplaylist.MyPlaylistClient;
import aji.myplaylist.MyPlaylistServer;
import net.fabricmc.api.EnvType;
import net.minecraft.text.Text;

public class Translation {
    public static String tr (String path){
        String str;
        if (MyPlaylist.Env == EnvType.CLIENT) str = MyPlaylistClient.LANGUAGE.getLanguage().getTranslations().get(path);
        else if (MyPlaylist.Env == EnvType.SERVER) str = MyPlaylistServer.LANGUAGE.getLanguage().getTranslations().get(path);
        else str = null;
        if (str == null) return "";
        else return str;
    }

    public static String tr (String path, String... args) {
        String str = tr(path);
        if (str.isEmpty()) return str;
        for (int i = 0; i < args.length; i++) {
            String placeholder = "{" + i + "}";
            String value = (args[i] == null) ? "null" : args[i];
            str = str.replace(placeholder, value);
        }
        return str;
    }

    public static String tr (String path, Text... args){
        if (tr(path).isEmpty()) return "";
        String[] stringArgs = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            stringArgs[i] = args[i].getString();
        }
        return tr(path, stringArgs);
    }

    public static Text trText (String path) {
        if (tr(path).isEmpty()) return Text.empty();
        String str = tr(path);
        return Text.literal(str);
    }

    public static Text trText (String path, String... args) {
        if (tr(path).isEmpty()) return Text.empty();
        String str = tr(path, args);
        return Text.literal(str);
    }

    public static Text trText (String path, Text... args){
        String template = tr(path);
        if (template.isEmpty()) return Text.empty();
        Text result = Text.empty();
        String[] parts = template.split("\\{\\d+}");
        for (int i = 0; i < parts.length; i++) {
            result = result.copy().append(parts[i]);
            if (i < args.length) {
                result = result.copy().append(args[i]);
            }
        }
        return result;
    }

}
