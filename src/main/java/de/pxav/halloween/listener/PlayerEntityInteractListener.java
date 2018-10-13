package de.pxav.halloween.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when an entity is right clicked.
 *
 * @author pxav.
 * (c) 2018
 */

public class PlayerEntityInteractListener implements Listener {

    @EventHandler
    public void onPlayerEntityInteract(final PlayerInteractEntityEvent event) {
        // check if an armor stand is right clicked
        if(event.getRightClicked().getType() == EntityType.ARMOR_STAND
                // check if this armor stand is riding on a bat (flying jack)
                && event.getRightClicked().getVehicle().getType() == EntityType.BAT)
            // cancel the event (the player cannot remove the lantern from the pumpkin)
            event.setCancelled(true);
    }

}
