package de.zeus.simpletrashbin;

import de.zeus.simpletrashbin.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class SimpleTrashBin extends JavaPlugin {

    private static SimpleTrashBin instance;

    public ArrayList<Location> locations = new ArrayList<>();

    private final File trashBinsConfig = new File("plugins/SimpleTrashBins/TrashBins.yml");
    private final YamlConfiguration trashBins = YamlConfiguration.loadConfiguration(trashBinsConfig);

    public static SimpleTrashBin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        if(trashBins.get("bins") != null)
            locations.addAll((ArrayList<Location>) trashBins.getList("bins"));

        Bukkit.getPluginManager().registerEvents(new TrashBinListener(), this);

        getCommand("trashbin").setExecutor(new TrashBinCommand());
        getCommand("opentrashbin").setExecutor(new OpenTrashBinCommand());
    }

    @Override
    public void onDisable() {
        trashBins.set("bins", locations);

        try {
            trashBins.save(trashBinsConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public static Sound getSound(String... soundNames) {
        for (String soundName : soundNames) {
            try {
                return Sound.valueOf(soundName.toUpperCase());
            } catch(Exception ignored) {}
        }
        return null;
    }
}