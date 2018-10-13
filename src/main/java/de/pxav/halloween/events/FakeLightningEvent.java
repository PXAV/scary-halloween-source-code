package de.pxav.halloween.events;

import de.pxav.halloween.Halloween;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This event summons a lightning effect which doesn't break any blocks or
 * doesn't burn.
 *
 * @author pxav.
 * (c) 2018
 */

public class FakeLightningEvent implements IEvent {

    private int task;
    public int countDown;

    /**
     * Starts the event scheduler.
     */
    @Override
    public void startScheduler() {
        final Halloween instance = Halloween.getInstance();
        if(Halloween.getInstance().getSettingsHandler().isScarySoundEvent()) {
            countDown = instance.getMathUtils().pickRandomItem(instance.getSettingsHandler().getFakeLightningDelay());
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Halloween.getInstance(), () -> {

                if(countDown == 0) {
                    // finally launch the event
                    Bukkit.getOnlinePlayers().forEach(this::launch);

                    // stop the event scheduler
                    this.stopScheduler();

                    // reset the helmets after a certain time
                    Bukkit.getScheduler().runTaskLater(instance, this::startScheduler, 20L);
                }

                if(countDown > 0)
                    countDown--;
            }, 0L, 20L);
        }
    }

    @Override
    public void stopScheduler() {
        Bukkit.getScheduler().cancelTask(task);
    }

    @Override
    public void prepare() {

    }

    /**
     * Launches The event for a certain player.
     * @param player The player for whom the event should be launched.
     */
    @Override
    public void launch(final Player player) {
        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(player.getWorld().getName())) {
            // play the actual lightning effect.
            player.getWorld().strikeLightningEffect(player.getLocation());

            // play some other effects
            player.playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 1);
            player.playEffect(player.getLocation(), Effect.LAVA_POP, 1);
            player.playEffect(player.getLocation(), Effect.LARGE_SMOKE, 1);

            // play the lightning sound to make it more realistic.
            player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 3, 1);
        }
    }

}
