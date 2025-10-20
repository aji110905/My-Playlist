package aji.myplaylist.client;

import aji.myplaylist.client.gui.OptionsScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuCompat implements ModMenuApi{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return OptionsScreen::new;
    }
}
