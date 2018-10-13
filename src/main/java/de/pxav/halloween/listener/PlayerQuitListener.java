package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when a player quits the server (disconnects).
 *
 * @author pxav.
 * (c) 2018
 */

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        // reset the helmet of the player if a jump scare is active to prevent loss of equipment.
        if(Halloween.getInstance().getJumpScareEvent().playerHelmets.containsKey(player.getUniqueId()))
            player.getInventory().setHelmet(Halloween.getInstance().getJumpScareEvent().playerHelmets.get(player.getUniqueId()));
    }

}
