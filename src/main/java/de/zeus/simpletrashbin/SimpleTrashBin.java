package de.zeus.simpletrashbin;

import de.zeus.simpletrashbin.commands.TrashBinCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleTrashBin extends JavaPlugin {

    private static SimpleTrashBin instance;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new TrashBinListener(), this);

        getCommand("trashbin").setExecutor(new TrashBinCommand());
    }

    @Override
    public void onDisable() {}

    public static SimpleTrashBin getInstance() {
        return instance;
    }
}