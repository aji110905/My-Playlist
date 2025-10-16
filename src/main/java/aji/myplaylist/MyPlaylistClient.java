package aji.myplaylist;

import aji.myplaylist.Language.Languages;
import net.fabricmc.api.ClientModInitializer;

public class MyPlaylistClient implements ClientModInitializer {
    public static final Languages LANGUAGE = new Languages();

    @Override
    public void onInitializeClient() {
    }
}
