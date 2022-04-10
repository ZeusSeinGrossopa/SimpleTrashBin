package de.zeus.simpletrashbin.commands;

import de.zeus.simpletrashbin.TrashBinListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenTrashBinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("trashbin.opencommand")) {
                TrashBinListener.createTrashBinInv(player);
            } else {
                player.sendMessage("§cYou don't have permissions to execute this command!");
            }
        } else {
            sender.sendMessage("§cThis command is only for a player!");
        }
        return false;
    }
}
