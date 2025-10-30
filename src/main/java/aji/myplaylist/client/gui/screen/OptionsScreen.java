package aji.myplaylist.client.gui.screen;

import aji.myplaylist.MyPlaylistClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

/**
 * 模组设置界面
 */
public class OptionsScreen extends Screen {
    private final Screen parent;
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this, 61, 33);

    public OptionsScreen(Screen parent) {
        super(Text.translatable("myplaylist.screen.options"));
        this.parent = parent;
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    protected void remove(Element child) {
        MyPlaylistClient.getInstance().getConfigmanager().save();
    }

    @Override
    protected void init() {
        DirectionalLayoutWidget directionalLayoutWidget1 = this.layout.addHeader(DirectionalLayoutWidget.vertical().spacing(8));
        directionalLayoutWidget1.add(new TextWidget(Text.translatable("myplaylist.screen.options"), this.textRenderer), Positioner::alignHorizontalCenter);
        DirectionalLayoutWidget directionalLayoutWidget2 = directionalLayoutWidget1.add(DirectionalLayoutWidget.horizontal()).spacing(8);
        directionalLayoutWidget2.add(ButtonWidget.builder(Text.translatable("myplaylist.screen.playlist"), button -> this.client.setScreen(new PlaylistScreen(this))).width(308).build());

        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(4).marginBottom(4).alignHorizontalCenter();
        MyPlaylistClient.getInstance().getConfigmanager().asOptions(gridWidget.createAdder(2), this.client);

        layout.addBody(gridWidget);

        DirectionalLayoutWidget directionalLayoutWidget3 = layout.addFooter(DirectionalLayoutWidget.horizontal()).spacing(8);
        directionalLayoutWidget3.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).width(150).build());
        directionalLayoutWidget3.add(ButtonWidget.builder(Text.translatable("myplaylist.screen.options.restoreDefaults"), button -> this.client.setScreen(new RestoreDefaultsScreen(this))).build());

        layout.forEachChild(this::addDrawableChild);
        initTabNavigation();
    }

    @Override
    protected void initTabNavigation() {
        this.layout.refreshPositions();
    }
}
