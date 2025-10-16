package aji.myplaylist.server;

import aji.myplaylist.Language.MyPlaylistLanguage;
import aji.myplaylist.MyPlaylist;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;

public class MyPlaylistServer implements DedicatedServerModInitializer {
    public static final MyPlaylistLanguage LANGUAGE = new MyPlaylistLanguage();

    @Override
    public void onInitializeServer() {
        MyPlaylist.Env = EnvType.SERVER;
    }
}