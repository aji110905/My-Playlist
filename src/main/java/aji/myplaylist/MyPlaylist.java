package aji.myplaylist;

import aji.myplaylist.music.Musics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPlaylist implements ModInitializer {
	public static final String MOD_ID = "myplaylist";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static EnvType Env = null;
    public static Musics musics = null;

	@Override
	public void onInitialize() {
        musics = new Musics();
    }
}