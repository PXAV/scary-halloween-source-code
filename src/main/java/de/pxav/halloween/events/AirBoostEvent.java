package de.pxav.halloween.events;

import de.pxav.halloween.Halloween;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This event boosts the player(s) into the air. The players
 * won't get any damage of this but will get scared a lot.
 *
 * @author pxav.
 * (c) 2018
 */

public class AirBoostEvent implements IEvent {

    private int task;
    public int countDown;

    public final Map<UUID, Integer> playerTasks = new HashMap<>();

    /**
     * Starts the event scheduler.
     */
    @Override
    public void startScheduler() {
        final Halloween instance = Halloween.getInstance();
        if(Halloween.getInstance().getSettingsHandler().isAirBoostEvent()) {
            countDown = instance.getMathUtils().pickRandomItem(instance.getSettingsHandler().getAirBoostDelays());
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Halloween.getInstance(), () -> {

                if(countDown == 0) {
                    // finally launch the event
                    Bukkit.getOnlinePlayers().forEach(this::launch);

                    Bukkit.getScheduler().runTaskLater(Halloween.getInstance(), this::startScheduler, 220L);

                    // stop the event scheduler
                    this.stopScheduler();

                }

                if(countDown > 0)
                    countDown--;
            }, 0L, 20L);
        }
    }

    /**
     * Stops the event scheduler
     */
    @Override
    public void stopScheduler() {
        Bukkit.getScheduler().cancelTask(task);
    }

    /**
     * Prepare the event (unnecessary here!)
     */
    @Override
    public void prepare() {

    }

    /**
     * Additional method: Cancels all tasks for the player
     * and allows fall damage again.
     * @param player The player who should be affected.
     */
    private void cancelForPlayer(final Player player) {
        Bukkit.getScheduler().runTaskLater(Halloween.getInstance(), () -> {
            if(this.playerTasks.containsKey(player.getUniqueId())) {
                Bukkit.getScheduler().cancelTask(this.playerTasks.get(player.getUniqueId()));
                this.playerTasks.remove(player.getUniqueId());
            }
        }, 220L);
    }

    /**
     * Launched the event for a certain player.
     * @param player The player for whom the event should be launched.
     */
    @Override
    public void launch(final Player player) {
        // check if the player is on the right world and doesn't fall right now.
        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(player.getWorld().getName())
                && !this.playerTasks.containsKey(player.getUniqueId())) {

            // set the vector of the player for a better animation
            final Vector vector = player.getLocation().getDirection().setY(2.0D).multiply(2.0D);
            player.setVelocity(vector);

            // add blindness to make the event more scary
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 3));

            // play a scary sound when the player gets boosted.
            Halloween.getInstance().getScarySoundEvent().launch(player);

            // start a scheduler.
            Bukkit.getScheduler().runTaskLater(Halloween.getInstance(), () ->  {
                // teleports the player high in the air as soon as the vector boost is over
                player.teleport(player.getLocation().add(0, 100, 0));
                // set the fall distance to 1 block so that the player doesn't get damaged when he falls on ground.
                playerTasks.put(player.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(Halloween.getInstance(), () -> {
                    if(player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0)).getType() == Material.AIR)
                        player.setFallDistance(1.0F);
                }, 0L, 1L));
            }, 20L);
            // cancel all tasks for the player.
            this.cancelForPlayer(player);
        }
    }
}
