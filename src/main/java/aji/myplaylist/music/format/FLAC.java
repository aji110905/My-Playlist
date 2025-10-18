package aji.myplaylist.music.format;

import aji.myplaylist.music.Music;
import aji.myplaylist.music.elements.Artist;
import aji.myplaylist.music.elements.Cover;
import aji.myplaylist.music.elements.Lyrics;
import aji.myplaylist.music.elements.Title;

public class FLAC implements Music {
    @Override
    public Title Title() {
        return null;
    }

    @Override
    public Artist Artist() {
        return null;
    }

    @Override
    public Cover Cover() {
        return null;
    }

    @Override
    public Lyrics lyrics() {
        return null;
    }
}
