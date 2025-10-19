package aji.myplaylist.music.elements;

import net.minecraft.text.Text;

/**
 * 表示歌曲的作者
 */
public class Artist {
    public final String value;

    public Artist(String value) {
        this.value = value;
    }

    public String StringValue(){
        return value;
    }

    public Text TextValue(){
        return Text.of(value);
    }
}
