package aji.myplaylist.server;

import aji.myplaylist.MyPlaylist;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;

public class MyPlaylistServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        MyPlaylist.Env = EnvType.SERVER;
    }
}