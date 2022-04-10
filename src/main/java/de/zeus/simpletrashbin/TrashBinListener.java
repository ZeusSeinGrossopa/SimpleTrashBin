package de.zeus.simpletrashbin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class TrashBinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        ItemStack item = event.getItemInHand();

        if (!event.isCancelled() && item.getType() == Material.DROPPER && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§aTrashBin")) {
            if (player.hasPermission("trashbin.create")) {
                block.setMetadata("Trashbin", new FixedMetadataValue(SimpleTrashBin.getInstance(), "trashbin"));
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !player.hasPermission("trashbin.use")) return;

        Block block = event.getClickedBlock();

        if (block != null && !block.getMetadata("Trashbin").isEmpty()) {
            event.setCancelled(true);
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

    private void createTrashBinInv(Player player) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, "§aTrashBin");

        ItemStack clearItem = new ItemStack(Material.BARRIER);
        ItemMeta clearMeta = clearItem.getItemMeta();
        clearMeta.setDisplayName("§cClear");
        clearItem.setItemMeta(clearMeta);

        inv.setItem(3 * 9 - 1, clearItem);

        player.openInventory(inv);
    }
}