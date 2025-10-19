package aji.myplaylist.music;

import aji.myplaylist.MyPlaylist;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 表示歌单
 */
public class Playlist {
    private File playlistFile = new File(FabricLoader.getInstance().getGameDir().toFile(), "playlist");
    private List<Music> musics = new ArrayList<>();

    /**
     * 创建歌单 会自动读取歌单文件
     */
    public Playlist(){
        playlistFile.mkdirs();
        File[] files = playlistFile.listFiles();
        if(files == null) return;
        for(File file : files){
            try {
                musics.add(Music.of(file));
            } catch (IOException e) {
                MyPlaylist.LOGGER.error("Audio cannot be read from this file: {}", file.getName(), e);
            }
        }
    }

    /**
     * 获取所有歌曲
     * @return 所有歌曲
     */
    public List<Music> getMusics() {
        return musics;
    }

    /**
     * 获取指定歌曲
     * @param title 歌曲的标题
     * @return 对应的歌曲 如果为null则没有对应的歌曲
     */
    public Music getMusic(String title) {
        for (Music music : musics) {
            if (music.Title().StringValue().equals(title)) return music;
        }
        return null;
    }
}
