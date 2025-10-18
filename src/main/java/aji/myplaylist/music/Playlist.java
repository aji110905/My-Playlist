package aji.myplaylist.music;

import aji.myplaylist.MyPlaylist;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public File playlistFile = new File(FabricLoader.getInstance().getGameDir().toFile(), "playlist");
    private List<Music> musics = new ArrayList<>();

    public Playlist(){
        playlistFile.mkdirs();
        File[] files = playlistFile.listFiles();
        if(files == null) return;
        for(File file : files){
            try {
                musics.add(new Music(file));
            } catch (IOException e) {
                MyPlaylist.LOGGER.error("Failed to load music: {}", file.getName(), e);
            }
        }
    }

    public List<Music> getMusics() {
        return musics;
    }

    public Music getMusic(String title) {
        for (Music music : musics) {
            if (music.getTitle().equals(title)) return music;
        }
        return null;
    }
}
