package com.febrie.bin.api.data;

import com.febrie.bin.BinSystemMain;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataManager {

    private static DataManager instance;

    private final YamlConfiguration config;
    private final YamlConfiguration ItemConfig;
    private final Map<UUID, JsonObject> map;
    private final Map<UUID, ItemStack> itemMap;
    private final File file = new File(BinSystemMain.getInstance().getDataFolder() + "/data.yml");
    private final File Ifile = new File(BinSystemMain.getInstance().getDataFolder() + "/items.yml");

    private DataManager() {
        instance = this;
        map = new HashMap<>();
        itemMap = new HashMap<>();
        if (!file.exists()) file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!Ifile.exists()) Ifile.getParentFile().mkdirs();
        try {
            Ifile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        config = YamlConfiguration.loadConfiguration(file);
        ItemConfig = YamlConfiguration.loadConfiguration(Ifile);
    }

    public JsonObject getData(UUID uuid) {
        if (!map.containsKey(uuid)) map.put(uuid, new JsonObject());
        return map.get(uuid);
    }

    public Set<UUID> getKeys() {
        return map.keySet();
    }

    public void setData(UUID uuid, JsonObject data) {
        map.put(uuid, data);
    }

    public void registerItem(UUID uuid, ItemStack item) {
        itemMap.put(uuid, item.clone());
    }

    public void removeItem(UUID uuid) {
        itemMap.remove(uuid);
    }

    public ItemStack getItem(UUID uuid) {
        return itemMap.get(uuid);
    }

    public void load() {
        for (String key : config.getKeys(false))
            map.put(UUID.fromString(key), JsonParser.parseString(Objects.requireNonNull(config.getString(key))).getAsJsonObject());
        for (String key : ItemConfig.getKeys(false))
            itemMap.put(UUID.fromString(key), ItemConfig.getItemStack(key));
    }

    public void save() throws IOException {
        for (UUID uuid : map.keySet())
            config.set(uuid.toString(), new GsonBuilder()
                    .setPrettyPrinting().create().toJson(map.get(uuid)));
        config.save(file);
        for (UUID uuid : itemMap.keySet())
            ItemConfig.set(uuid.toString(), itemMap.get(uuid));
        ItemConfig.save(Ifile);
    }

    public static DataManager getInstance() {
        if (instance == null) new DataManager();
        return instance;
    }
}
