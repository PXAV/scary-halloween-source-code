package de.pxav.halloween.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when an entity dies.
 *
 * @author pxav.
 * (c) 2018
 */

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {
        // check if a bat died.
        final Entity entity = event.getEntity();
        if(entity.getType() == EntityType.BAT) {
            if(entity.getPassenger() == null)
                return;
            // if it had a passenger (the flying jack is meant here) remove it.
            if(entity.getPassenger().getType() == EntityType.ARMOR_STAND) {
                entity.getPassenger().remove();
                event.setDroppedExp(0);
                event.getDrops().clear();
            }
        }
    }

}
