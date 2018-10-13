package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when a player joins the server.
 *
 * @author pxav.
 * (c) 2018
 */

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        // add the player to essential lists to prevent upcoming exceptions.
        Halloween.getInstance().getBatAttackEvent().playerEntities.put(player.getUniqueId(), new ArrayList<>());
        Halloween.getInstance().getPumpkinDistanceScheduler().oldLocation.put(player.getUniqueId(), player.getLocation());
    }

}
