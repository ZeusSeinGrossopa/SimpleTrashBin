package de.zeus.simpletrashbin.commands;

import de.zeus.simpletrashbin.SimpleTrashBin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TrashBinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("trashbin.create")) {
                ItemStack trashBinItem = new ItemStack(Material.DROPPER);
                ItemMeta itemMeta = trashBinItem.getItemMeta();
                itemMeta.setLore(Arrays.asList("§8»", "§aPlace the trash bin to use"));
                itemMeta.setDisplayName("§aTrashBin");
                trashBinItem.setItemMeta(itemMeta);

                player.getInventory().addItem(trashBinItem);
                player.sendMessage("§aYou have successfully got the trash bin!");
                player.playSound(player.getLocation(), SimpleTrashBin.getSound("ENTITY_PLAYER_LEVELUP", "LEVEL_UP"), 0.5F, 1.0F);
            } else {
                player.sendMessage("§cYou don't have permissions to execute this command!");
            }
        } else {
            sender.sendMessage("§cThis command is only for a player!");
        }
        return false;
    }
}
