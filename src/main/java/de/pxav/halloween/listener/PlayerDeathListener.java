package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered, when a player on the server dies.
 * If you have enabled that zombies should spawn on a player death
 * this event will spawn a zombie at the location of the player.
 *
 * @author pxav.
 * (c) 2018
 */

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Halloween instance = Halloween.getInstance();
        // checks if this feature is enabled in the config.yml
        if(instance.getSettingsHandler().isZombieOnPlayerDeath()) {
            // checks if the world where the player died is affected
            if(instance.getSettingsHandler().getAffectedWorlds().contains(event.getEntity().getWorld().getName())) {
                final Player player = event.getEntity();
                final Entity entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                // set the custom name of the entity
                entity.setCustomName(instance.getSettingsHandler().getDeathZombieName(player.getName()));
                entity.setCustomNameVisible(true);
            }
        }
    }

}
