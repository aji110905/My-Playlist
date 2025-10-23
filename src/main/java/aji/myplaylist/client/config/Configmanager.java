package aji.myplaylist.client.config;

import aji.myplaylist.MyPlaylist;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.GridWidget;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Configmanager {
    private final File configFile;
    private final Set<ParsedConfig> configs = new HashSet<>();


    public Configmanager(File configFile) {
        this.configFile = configFile;
        if (!configFile.exists()) save();
        load();
    }

    public void load() {
        for (Field field : Configs.class.getDeclaredFields()) {
            Config annotation = field.getAnnotation(Config.class);
            if (annotation == null) break;
            if (!String.class.isAssignableFrom(field.getType())) throw new IllegalArgumentException("Config field must be of type String:" + field.getName());
            List<String> list = Arrays.asList(annotation.value());
            try {
                String string = (String) field.get(null);
                if (!list.contains(string)) {
                    list.add(string);
                }
                configs.add(new ParsedConfig(field.getName(), list, string));
            } catch (IllegalAccessException e) {
                MyPlaylist.LOGGER.error("Failed to load config field:{}", field.getName(), e);
            }
        }
        try {
            JsonParser.parseReader(new FileReader(configFile)).getAsJsonObject().asMap().forEach((key, value) -> {
                String string = value.getAsString();
                for (ParsedConfig config : configs) {
                    if (config.getName().equals(key)) {
                        config.setValue(string);
                        break;
                    }
                }
            });
        } catch (FileNotFoundException e) {
            MyPlaylist.LOGGER.error("Failed to load config file", e);
        }
    }

    public void save() {
        try {
            configFile.createNewFile();
            JsonWriter writer = new JsonWriter(new FileWriter(configFile));
            writer.setIndent("  ");
            writer.beginObject();
            for (ParsedConfig config : configs) {
                writer.name(config.getName());
                writer.value(config.getValue());
            }
            writer.endObject();
            writer.close();
            MyPlaylist.LOGGER.info("Saved config file");
        } catch (IOException e) {
            MyPlaylist.LOGGER.error("Failed to save config file", e);
        }
    }

    public void asOptions(GridWidget.Adder adder, MinecraftClient client){
        for (ParsedConfig config : configs) {
            adder.add(config.toSimpleOption().createWidget(client.options));
        }
    }

    public Set<ParsedConfig> getConfigs() {
        return configs;
    }

    public ParsedConfig getConfig(String name) {
        for (ParsedConfig config : configs) {
            if (config.getName().equals(name)) {
                return config;
            }
        }
        return null;
    }

    public void allConfigsToDefault(){
        for (ParsedConfig config : configs) {
            config.toDefault();
        }
    }
}
