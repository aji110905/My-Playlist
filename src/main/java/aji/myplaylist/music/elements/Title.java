package aji.myplaylist.music.elements;

import net.minecraft.text.Text;

import java.util.Objects;

public class Title {
    public final String value;

    public Title(String value) {
        this.value = value;
    }

    public String StringValue(){
        return value;
    }

    public Text TextValue(){
        return Text.of(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return Objects.equals(value, title.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
