package aji.myplaylist.client.config;

import net.minecraft.client.gui.widget.GridWidget;

import java.io.File;

/**
 * 配置管理器
 */
public class Configmanager {
    public final File configFile;

    public Configmanager(File configFile) {
        this.configFile = configFile;
    }
    /**
     * 从配置文件加载配置
     */
    public void load() {

    }

    /**
     * 将配置保存到配置文件
     */
    public void save() {

    }

    public void asOptions(GridWidget.Adder adder){

    }
}
