package de.zeus.simpletrashbin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrashBinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        ItemStack item = event.getItemInHand();

        if (!event.isCancelled() && item.getType() == Material.DROPPER && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§aTrashBin")) {
            if (player.hasPermission("trashbin.create")) {
                SimpleTrashBin.getInstance().getLocations().add(block.getLocation());
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        SimpleTrashBin.getInstance().getLocations().remove(block.getLocation());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !player.hasPermission("trashbin.use")) return;

        Block block = event.getClickedBlock();

        if (block != null && SimpleTrashBin.getInstance().getLocations().contains(block.getLocation())) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), SimpleTrashBin.getSound("BLOCK_CHEST_OPEN", "CHEST_OPEN"), 0.5F, 1.0F);
            createTrashBinInv(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.hasPermission("trashbin.use") && event.getView().getTitle().equals("§aTrashBin")) {
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().getDisplayName().equals("§cClear")) {
                event.setCancelled(true);

                Inventory inv = event.getClickedInventory();

                inv.clear();
                createTrashBinInv(player);
            }
        }
    }

    public static void createTrashBinInv(Player player) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, "§aTrashBin");

        ItemStack clearItem = new ItemStack(Material.BARRIER);
        ItemMeta clearMeta = clearItem.getItemMeta();
        clearMeta.setDisplayName("§cClear");
        clearItem.setItemMeta(clearMeta);

        inv.setItem(3 * 9 - 1, clearItem);

        player.openInventory(inv);
    }
}