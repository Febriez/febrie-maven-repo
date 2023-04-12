package com.febrie.bin;

import com.febrie.bin.api.data.DataManager;
import com.febrie.bin.api.locale.Locale;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class BinSystemMain extends JavaPlugin {

    private static BinSystemMain instance;

    public void onEnable() {
        instance = this;
        DataManager.getInstance().load();
        Bukkit.getConsoleSender().sendMessage(Locale.getInstance().getMessage("msg.load.success"));
    }

    public void onDisable() {
        try {
            DataManager.getInstance().save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BinSystemMain getInstance() {
        return instance;
    }
}
