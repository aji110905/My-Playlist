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

public interface Music {
    Title Title();

    Artist Artist();

    Cover Cover();

    Lyrics lyrics();

    static Music of (File file) throws IOException {
        if (!file.isFile()) throw new IOException("File is not a file");
        if (file.getName().endsWith(".mp3")) return new MP3();
        if (file.getName().endsWith(".aac")) return new AAC();
        if (file.getName().endsWith(".flac")) return new FLAC();
        throw new IOException("File format not supported");
    }
}
