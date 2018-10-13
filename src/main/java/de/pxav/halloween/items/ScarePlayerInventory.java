package de.pxav.halloween.items;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.configuration.SettingsHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This inventory contains options to scare a player with custom events.
 *
 * @author pxav.
 * (c) 2018
 */

public class ScarePlayerInventory {

    private ItemStack fillMaterial;

    public void prepare() {
        fillMaterial = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7)
                .setNoName()
                .setAmount(1)
                .build();
    }

    public void open(final Player player) {

        // define instances
        final Halloween instance = Halloween.getInstance();
        final SettingsHandler settingsHandler = instance.getSettingsHandler();

        // create inventory
        final Inventory inventory = Bukkit.createInventory(null, 54, settingsHandler.getScareInventoryTitle());

        // set margin items
        for (int i = 0; i < 10; i++)
            inventory.setItem(i, fillMaterial);
        for (int i = 44; i < 54; i++)
            inventory.setItem(i, fillMaterial);

        inventory.setItem(17, fillMaterial);
        inventory.setItem(18, fillMaterial);
        inventory.setItem(26, fillMaterial);
        inventory.setItem(27, fillMaterial);
        inventory.setItem(35, fillMaterial);
        inventory.setItem(36, fillMaterial);

        // set troll items
        inventory.setItem(20, new ItemBuilder(Material.PUMPKIN).setDisplayName(settingsHandler.getScareInventoryJumpScare()).build());
        inventory.setItem(21, new ItemBuilder(Material.NOTE_BLOCK).setDisplayName(settingsHandler.getScareInventoryPlaySound()).build());
        inventory.setItem(22, new ItemBuilder(Material.getMaterial(383), (short) 65).setDisplayName(settingsHandler.getScareInventoryBatAttack()).build());
        inventory.setItem(23, new ItemBuilder(Material.BEACON).setDisplayName(settingsHandler.getScareInventoryLightning()).build());
        inventory.setItem(24, new ItemBuilder(Material.SHEARS).setDisplayName(settingsHandler.getScareInventorySpawnGhost()).build());
        inventory.setItem(29, new ItemBuilder(Material.RABBIT_FOOT).setDisplayName(settingsHandler.getScareInventoryScaryFlight()).build());
        inventory.setItem(30, new ItemBuilder(Material.CHEST).setDisplayName(settingsHandler.getScareInventoryTrickOrTreatChest()).build());
        inventory.setItem(31, new ItemBuilder(Material.JACK_O_LANTERN).setDisplayName(settingsHandler.getScareInventoryFlyingJack()).build());
        inventory.setItem(32, new ItemBuilder(Material.COBBLE_WALL, (short) 1).setDisplayName(settingsHandler.getScareInventoryPlayerGrave()).build());
        inventory.setItem(33, new ItemBuilder(Material.BARRIER).setDisplayName(settingsHandler.getScareInventoryNotAvailable()).build());

        // open inventory to player.
        player.openInventory(inventory);
    }

}
