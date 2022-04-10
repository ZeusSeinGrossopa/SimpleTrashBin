package de.zeus.simpletrashbin;

import de.zeus.simpletrashbin.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleTrashBin extends JavaPlugin {

    private static SimpleTrashBin instance;

    public static SimpleTrashBin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new TrashBinListener(), this);

        getCommand("trashbin").setExecutor(new TrashBinCommand());
        getCommand("opentrashbin").setExecutor(new OpenTrashBinCommand());
    }

    @Override
    public void onDisable() {
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