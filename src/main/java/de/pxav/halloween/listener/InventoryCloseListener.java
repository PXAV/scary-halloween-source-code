package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when a player closes an
 * inventory.
 *
 * @author pxav.
 * (c) 2018
 */

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event) {
        if(event.getPlayer() instanceof Player
                && event.getInventory() != null) {
            // remove the player from the scaring player map to avoid issues later.
            final Player player = (Player) event.getPlayer();
            Halloween.getInstance().getPlayerHandler().scaringPlayer.remove(player);
        }

    }

}
