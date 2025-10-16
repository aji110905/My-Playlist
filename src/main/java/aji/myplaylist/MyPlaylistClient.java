package aji.myplaylist;

import aji.myplaylist.Language.Languages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;

public class MyPlaylistClient implements ClientModInitializer {
    public static final Languages LANGUAGE = new Languages();

    @Override
    public void onInitializeClient() {
        MyPlaylist.Env = EnvType.CLIENT;
    }
}
