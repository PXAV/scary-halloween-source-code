package de.pxav.halloween.pumpkins;

import de.pxav.halloween.Halloween;
import org.bukkit.*;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This class launches the events of the pumpkins automatically without
 * a player near the pumpkin.
 *
 * @author pxav.
 * (c) 2018
 */

public class PumpkinLauncherScheduler {

    // task for glowing pumpkins
    private int task;

    // task for jump scare pumpkin
    private int scareTask;

    /**
     * Starts the scheduler which manages the launch of the events.
     */
    public void startScheduler() {
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Halloween.getInstance(), () -> {
            Halloween.getInstance().getPumpkinHandler().pumpkins.get(PumpkinType.GLOWING).forEach(location -> {
                if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(location.getWorld().getName())) {
                    if(location.getWorld().getBlockAt(location).getType() == Material.PUMPKIN) {
                        location.getWorld().getBlockAt(location).setType(Material.JACK_O_LANTERN);
                        Bukkit.getOnlinePlayers().forEach(current -> {
                            if(current.getWorld().getName().equalsIgnoreCase(location.getWorld().getName())) {
                                if(current.getLocation().distance(location) < 13.0D)
                                    current.playSound(current.getLocation(), Sound.FIRE_IGNITE, 3, 1);
                                for (int i = 0; i < 3; i++)
                                    current.playEffect(location, Effect.LAVA_POP, 1);
                                for (int i = 0; i < 3; i++)
                                    current.playEffect(location, Effect.FIREWORKS_SPARK, 1);
                                for (int i = 0; i < 3; i++)
                                    current.playEffect(location, Effect.COLOURED_DUST, 1);
                            }
                        });
                    } else if(location.getWorld().getBlockAt(location).getType() == Material.JACK_O_LANTERN) {
                        location.getWorld().getBlockAt(location).setType(Material.PUMPKIN);
                        Bukkit.getOnlinePlayers().forEach(current -> {
                            if(current.getWorld().getName().equalsIgnoreCase(location.getWorld().getName())) {
                                for (int i = 0; i < 3; i++)
                                    current.playEffect(location, Effect.EXTINGUISH, 1);
                                for (int i = 0; i < 3; i++)
                                    current.playEffect(location, Effect.CLOUD, 1);
                            }
                        });
                    }
                }
            });
        }, 0L, 120L);

        scareTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Halloween.getInstance(), ()
            -> Halloween.getInstance().getPumpkinHandler().pumpkins.get(PumpkinType.JUMP_SCARE).forEach(location -> {
                if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(location.getWorld().getName())) {
                    if(location.getWorld().getBlockAt(location).getType() == Material.PUMPKIN) {
                        location.getWorld().getPlayers().forEach(current -> {
                            if(current.getLocation().distance(location) < 15.0D) {
                                if(current.getInventory().getHelmet() == null)
                                    Halloween.getInstance().getJumpScareEvent().launch(current);
                                else if(current.getInventory().getHelmet().getType() != Material.PUMPKIN
                                        && current.getInventory().getHelmet().getType() != Material.JACK_O_LANTERN)
                                    Halloween.getInstance().getJumpScareEvent().launch(current);
                            }
                        });
                    }
                }
        }), 0L, 300L);

    }

    /**
     * Stop both schedulers.
     */
    public void stopScheduler() {
        Bukkit.getScheduler().cancelTask(task);
        Bukkit.getScheduler().cancelTask(scareTask);
    }


}
