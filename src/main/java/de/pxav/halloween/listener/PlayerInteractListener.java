package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.pumpkins.PumpkinType;
import org.bukkit.*;
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

            final Player player = event.getPlayer();

            // check if the world is a halloween world.
            if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(event.getClickedBlock().getWorld().getName())) {
                // check if the pumpkin effect is activated.
                if(Halloween.getInstance().getSettingsHandler().isPumpkinClickEffect()) {
                    if(Halloween.getInstance().getPumpkinHandler().getPumpkinType(event.getClickedBlock().getLocation()) == PumpkinType.CLICKABLE) {
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

                if(event.getClickedBlock().getType() == Material.CHEST) {
                    if(!Halloween.getInstance().getTrickOrTreatEvent().chestData.getOrDefault(event.getClickedBlock(), true)) {
                        event.setCancelled(true);

                        // remove the chest from all lists.
                        Halloween.getInstance().getTrickOrTreatEvent().chestData.remove(event.getClickedBlock());
                        Halloween.getInstance().getTrickOrTreatEvent().chestLocations.remove(event.getClickedBlock().getLocation());

                        // remove the chest block itself.
                        event.getClickedBlock().setType(Material.AIR);

                        // play some spooky effects
                        player.playSound(player.getLocation(), Sound.EXPLODE, 3, 1);
                        for (int i = 0; i < 50; i++)
                            player.playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 1);
                        for (int i = 0; i < 50; i++)
                            player.playEffect(player.getLocation(), Effect.LARGE_SMOKE, 1);
                        for (int i = 0; i < 50; i++)
                            player.playEffect(player.getLocation(), Effect.PARTICLE_SMOKE, 1);
                        for (int i = 0; i < 50; i++)
                            player.playEffect(player.getLocation(), Effect.LAVA_POP, 1);

                        if(Halloween.getInstance().getSettingsHandler().isExplodeChests())
                            event.getClickedBlock().getWorld().createExplosion(event.getClickedBlock().getLocation(), 4, true);

                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getChestUnlucky());
                    }
                }
            }
        }
    }

}
