package aji.myplaylist;

import aji.myplaylist.music.Playlist;
import net.fabricmc.api.DedicatedServerModInitializer;

public class MyPlaylistServer implements DedicatedServerModInitializer {
    private Playlist musics;
    private static final MyPlaylistServer INSTANCE = new MyPlaylistServer();

    @Override
    public void onInitializeServer() {
        musics = new Playlist();
    }

    public static MyPlaylistServer getInstance() {
        return INSTANCE;
    }

    public Playlist getMusics() {
        return musics;
    }

    public void setMusics(Playlist musics) {
        this.musics = musics;
    }
}