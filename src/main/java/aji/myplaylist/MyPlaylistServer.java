package aji.myplaylist;

import aji.myplaylist.Language.MyPlaylistLanguage;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;

public class MyPlaylistServer implements DedicatedServerModInitializer {
    public static final MyPlaylistLanguage LANGUAGE = new MyPlaylistLanguage();

    @Override
    public void onInitializeServer() {
        MyPlaylist.Env = EnvType.SERVER;
    }
}