package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.pumpkins.PumpkinType;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when a player interacts with
 * an item or block.
 *
 * @author pxav.
 * (c) 2018
 */

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if(event.getClickedBlock() != null
                && event.getAction() != null
                && event.getMaterial() != null) {

            // check if the world is a halloween world.
            if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(event.getClickedBlock().getWorld().getName())) {
                // check if the pumpkin effect is activated.
                if(Halloween.getInstance().getSettingsHandler().isPumpkinClickEffect()) {
                    if(Halloween.getInstance().getPumpkinHandler().getPumpkinType(event.getClickedBlock().getLocation()) == PumpkinType.CLICKABLE) {
                        final Player player = event.getPlayer();
                        if(event.getClickedBlock().getType() == Material.PUMPKIN) {
                            // play the pumpkin click effects.
                            player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 3, 1);
                            event.getClickedBlock().setType(Material.JACK_O_LANTERN);
                            for (int i = 0; i < 50; i++)
                                player.playEffect(event.getClickedBlock().getLocation(), Effect.FLAME, 1);
                            for (int i = 0; i < 50; i++)
                                player.playEffect(event.getClickedBlock().getLocation().add(1, 0, 0), Effect.FLAME, 1);
                            for (int i = 0; i < 50; i++)
                                player.playEffect(event.getClickedBlock().getLocation().add(0, 0, 1), Effect.FLAME, 1);
                            for (int i = 0; i < 50; i++)
                                player.playEffect(event.getClickedBlock().getLocation(), Effect.LAVA_POP, 1);
                            for (int i = 0; i < 50; i++)
                                player.playEffect(event.getClickedBlock().getLocation(), Effect.LAVA_POP, 1);
                            for (int i = 0; i < 50; i++)
                                player.playEffect(event.getClickedBlock().getLocation(), Effect.LAVA_POP, 1);
                            for (int i = 0; i < 50; i++)
                                player.playEffect(event.getClickedBlock().getLocation().add(1, 0, 0), Effect.LAVA_POP, 1);
                            for (int i = 0; i < 50; i++)
                                player.playEffect(event.getClickedBlock().getLocation().add(0, 0, 1), Effect.LAVA_POP, 1);
                            Bukkit.getScheduler().runTaskLater(Halloween.getInstance(), () -> {
                                if(event.getClickedBlock().getType() == Material.JACK_O_LANTERN) {
                                    event.getClickedBlock().setType(Material.PUMPKIN);
                                }
                            }, 60L);
                        }
                    }
                }
            }
        }
    }

}
