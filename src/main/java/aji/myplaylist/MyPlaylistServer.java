package aji.myplaylist;

import aji.myplaylist.Language.Languages;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;

public class MyPlaylistServer implements DedicatedServerModInitializer {
    public static final Languages LANGUAGE = new Languages();

    @Override
    public void onInitializeServer() {
        MyPlaylist.Env = EnvType.SERVER;
    }
}