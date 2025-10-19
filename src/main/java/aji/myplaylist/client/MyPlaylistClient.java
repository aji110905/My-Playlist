package aji.myplaylist.client;

import aji.myplaylist.MyPlaylist;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;

public class MyPlaylistClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MyPlaylist.Env = EnvType.CLIENT;
    }
}
