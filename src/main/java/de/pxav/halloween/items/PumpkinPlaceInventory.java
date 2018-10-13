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
 * This builds the inventory in which the player can select
 * which pumpkins he would like to place.
 *
 * @author pxav.
 * (c) 2018
 */

public class PumpkinPlaceInventory {

    public static final String PLACE_CLICKABLE = "§8> §6§lCLICKABLE PUMPKIN";
    public static final String PLACE_SMOKING = "§8> §6§lSMOKING PUMPKIN";
    public static final String PLACE_LIGHTNING = "§8> §6§lLIGHTNING PUMPKIN";
    public static final String PLACE_JUMP_SCARE = "§8> §6§lJUMP SCARE PUMPKIN";
    public static final String PLACE_GLOWING = "§8> §6§lGLOWING PUMPKIN";

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
        final Inventory inventory = Bukkit.createInventory(null, 45, settingsHandler.getPumpkinInventoryTitle());

        // set margin items
        for (int i = 0; i < 10; i++)
            inventory.setItem(i, fillMaterial);
        for (int i = 35; i < 45; i++)
            inventory.setItem(i, fillMaterial);

        inventory.setItem(17, fillMaterial);
        inventory.setItem(18, fillMaterial);
        inventory.setItem(26, fillMaterial);
        inventory.setItem(27, fillMaterial);

        // set troll items
        inventory.setItem(20, new ItemBuilder(Material.REDSTONE_LAMP_OFF)
                .setDisplayName(settingsHandler.getPumpkinInventoryGlowing())
                .addLoreAll(settingsHandler.getPumpkinInventoryGlowingLore())
                .build());
        inventory.setItem(21, new ItemBuilder(Material.BLAZE_POWDER)
                .setDisplayName(settingsHandler.getPumpkinInventoryClickable())
                .addLoreAll(settingsHandler.getPumpkinInventoryClickableLore())
                .build());
        inventory.setItem(22, new ItemBuilder(Material.SEA_LANTERN)
                .setDisplayName(settingsHandler.getPumpkinInventoryLightning())
                .addLoreAll(settingsHandler.getPumpkinInventoryLightningLore())
                .build());
        inventory.setItem(23, new ItemBuilder(Material.FLINT_AND_STEEL)
                .setDisplayName(settingsHandler.getPumpkinInventorySmoking())
                .addLoreAll(settingsHandler.getPumpkinInventorySmokingLore())
                .build());
        inventory.setItem(24, new ItemBuilder(Material.PUMPKIN)
                .setDisplayName(settingsHandler.getPumpkinInventoryJumpScare())
                .addLoreAll(settingsHandler.getPumpkinInventoryJumpScareLore())
                .build());


        // open inventory to player.
        player.openInventory(inventory);
    }

}
