package aji.myplaylist.client.gui.screen;

import aji.myplaylist.MyPlaylistClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

public class RestoreDefaultsScreen extends Screen {
    private final Screen parent;
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this, 61, 33);

    protected RestoreDefaultsScreen(Screen parent) {
        super(Text.translatable("myplaylist.screen.restoreDefaults"));
        this.parent = parent;
    }

    @Override
    protected void initTabNavigation() {
        layout.refreshPositions();
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    @Override
    protected void init() {
        DirectionalLayoutWidget directionalLayoutWidget1 = layout.addBody(DirectionalLayoutWidget.vertical().spacing(8));
        directionalLayoutWidget1.add(new TextWidget(Text.translatable("myplaylist.screen.restoreDefaults.text"), this.textRenderer), Positioner::alignHorizontalCenter);
        DirectionalLayoutWidget directionalLayoutWidget2 = directionalLayoutWidget1.add(DirectionalLayoutWidget.horizontal()).spacing(8);
        directionalLayoutWidget2.add(ButtonWidget.builder(Text.translatable("myplaylist.screen.restoreDefaults.no"), button -> this.client.setScreen(parent)).build());
        directionalLayoutWidget2.add(ButtonWidget.builder(Text.translatable("myplaylist.screen.restoreDefaults.yes"), button -> {
            MyPlaylistClient.getInstance().getConfigmanager().allConfigsToDefault();
            this.client.setScreen(parent);
        }).build());
        layout.forEachChild(this::addDrawableChild);
        this.initTabNavigation();
    }
}
