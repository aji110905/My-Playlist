package aji.myplaylist.client;

import aji.myplaylist.Language.MyPlaylistLanguage;
import aji.myplaylist.MyPlaylist;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;

public class MyPlaylistClient implements ClientModInitializer {
    public static final MyPlaylistLanguage LANGUAGE = new MyPlaylistLanguage();

    @Override
    public void onInitializeClient() {
        MyPlaylist.Env = EnvType.CLIENT;
    }
}
