package aji.myplaylist;

import aji.myplaylist.client.config.Configmanager;
import aji.myplaylist.music.Playlist;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class MyPlaylistClient implements ClientModInitializer {
    private Configmanager configmanager;
    private Playlist musics;
    private static final MyPlaylistClient INSTANCE = new MyPlaylistClient();

    @Override
    public void onInitializeClient() {
        getInstance().configmanager = new Configmanager(new File(FabricLoader.getInstance().getConfigDir().toFile(), MyPlaylistSettings.MOD_ID + ".json"));
        musics = new Playlist();
    }

    public Configmanager getConfigmanager() {
        return configmanager;
    }

    public static MyPlaylistClient getInstance() {
        return MyPlaylistClient.INSTANCE;
    }

    public Playlist getMusics() {
        return musics;
    }

    public void setMusics(Playlist musics) {
        this.musics = musics;
    }
}
