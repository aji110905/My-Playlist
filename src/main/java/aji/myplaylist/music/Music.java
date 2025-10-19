package aji.myplaylist.music;

import aji.myplaylist.music.elements.Artist;
import aji.myplaylist.music.elements.Cover;
import aji.myplaylist.music.elements.Lyrics;
import aji.myplaylist.music.elements.Title;
import aji.myplaylist.music.format.AAC;
import aji.myplaylist.music.format.FLAC;
import aji.myplaylist.music.format.MP3;

import java.io.File;
import java.io.IOException;

/**
 * 表示歌曲
 */
public interface Music {
    Title Title();

    Artist Artist();

    Cover Cover();

    Lyrics lyrics();

    /**
     *
     * @param file 对应的文件
     * @return 返回对应格式的歌曲对象
     * @throws IOException 当文件不存在或者不是文件或无法解析时抛出此异常
     */
    static Music of (File file) throws IOException {
        if (!file.exists()) throw new IOException("File does not exist");
        if (!file.isFile()) throw new IOException("File is not a file");
        if (file.getName().endsWith(".mp3")) return new MP3();
        if (file.getName().endsWith(".aac")) return new AAC();
        if (file.getName().endsWith(".flac")) return new FLAC();
        throw new IOException("File format not supported");
    }
}
