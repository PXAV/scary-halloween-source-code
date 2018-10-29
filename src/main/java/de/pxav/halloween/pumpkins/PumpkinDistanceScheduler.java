package de.pxav.halloween.pumpkins;

import de.pxav.halloween.Halloween;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This class handles a scheduler which plays effects of custom pumpkins.
 *
 * @author pxav.
 * (c) 2018
 */

public class PumpkinDistanceScheduler {

    private int task;

    // saves the last known location of a player.
    public Map<UUID, Location> oldLocation = new HashMap<>();

    /**
     * Starts the scheduler which handles the events of pumpkins
     * which are distance based.
     */
    @SuppressWarnings("deprecation")
    public void startScheduler() {
        Bukkit.getOnlinePlayers().forEach(current -> this.oldLocation.put(current.getUniqueId(), current.getLocation()));
        task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Halloween.getInstance(), ()
                // iterate all players on the server.
                -> Bukkit.getOnlinePlayers().forEach(current -> {

            // check if the current player is in an affected world.
            if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(current.getWorld().getName())) {

                // check if the player has moved in the last second.
                if(oldLocation.get(current.getUniqueId()).getX() != current.getLocation().getX()
                        || oldLocation.get(current.getUniqueId()).getY() != current.getLocation().getY()
                        || oldLocation.get(current.getUniqueId()).getZ() != current.getLocation().getZ()) {

                    // play a lightning effect if the player is in a certain radius of a pumpkin.
                    Halloween.getInstance().getPumpkinHandler().pumpkins.get(PumpkinType.LIGHTNING).forEach(location -> {
                        if(current.getWorld().getName().equalsIgnoreCase(location.getWorld().getName())) {
                            if(current.getLocation().distance(location) <= Halloween.getInstance().getSettingsHandler().getLightningPumpkinDistance()) {
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.getInstance(), () -> {
                                    current.getWorld().strikeLightningEffect(location);
                                    current.playSound(current.getLocation(), Sound.AMBIENCE_THUNDER, 3, 1);
                                });
                            }
                        }
                    });

                    // play a smoke effect if the player is in a certain radius of a pumpkin.
                    Halloween.getInstance().getPumpkinHandler().pumpkins.get(PumpkinType.SMOKING).forEach(location -> {
                        if(current.getWorld().getName().equalsIgnoreCase(location.getWorld().getName())) {
                            if(current.getLocation().distance(location) < 30) {
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.getInstance(), () -> {
                                    current.playEffect(location, Effect.PARTICLE_SMOKE, 1);
                                    current.playEffect(location.add(0, 1, 0), Effect.LARGE_SMOKE, 1);
                                });
                            }
                        }
                    });

                }

                // update the location of the player.
                this.oldLocation.put(current.getUniqueId(), current.getLocation());
            }
        }), 0L, 20L);
    }

    public void stopScheduler() {
        Bukkit.getScheduler().cancelTask(task);
    }

}
