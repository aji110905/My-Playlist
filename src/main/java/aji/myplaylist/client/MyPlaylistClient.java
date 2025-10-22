package aji.myplaylist.client;

import aji.myplaylist.MyPlaylist;
import aji.myplaylist.client.config.Configmanager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class MyPlaylistClient implements ClientModInitializer {
    public static Configmanager configmanager;
    @Override
    public void onInitializeClient() {
        MyPlaylist.Env = EnvType.CLIENT;
        configmanager = new Configmanager(new File(FabricLoader.getInstance().getConfigDir().toFile(), MyPlaylist.MOD_ID + ".json"));
    }
}
