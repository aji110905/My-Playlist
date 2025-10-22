package aji.myplaylist.client.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class PlaylistScreen extends Screen {
    private final Screen parent;

    public PlaylistScreen(Screen parent){
        super(Text.translatable("myplaylist.screen.playlist"));
        this.parent = parent;
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
