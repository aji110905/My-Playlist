package aji.myplaylist.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.text.Text;

/**
 * 模组设置界面
 */
public class OptionsScreen extends GameOptionsScreen {
    public OptionsScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.literal("myplaylist.options"));
    }

    @Override
    protected void addOptions() {

    }
}
