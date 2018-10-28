package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 * <p>
 * basic class description
 *
 * @author pxav.
 * (c) 2018
 */

public class PlayerCommandPreProcessListener implements Listener {

    @EventHandler
    public void onPlayerCommandPreProcess(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final List<String> aliasList = Halloween.getInstance().getSettingsHandler().getCommandAliases();
        if(!Halloween.getInstance().getSettingsHandler().getCommandAliases().contains(event.getMessage().replace("/", "")))
            return;
        if(player.hasPermission(Halloween.getInstance().getSettingsHandler().getHalloweenCommandPermission())) {
            event.setCancelled(true);
            player.performCommand("halloween");
        } else player.sendMessage(Halloween.getInstance().getSettingsHandler().getNoPermission());
    }

}
