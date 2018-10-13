package de.pxav.halloween.events;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 * <p>
 * basic class description
 *
 * @author pxav.
 * (c) 2018
 */

public class ScarySoundEvent implements IEvent {

    private int task;
    public int countDown;

    private final List<Sound> soundToPlay = new ArrayList<>();

    /**
     * Starts the event scheduler.
     */
    @Override
    public void startScheduler() {
        final Halloween instance = Halloween.getInstance();
        if(Halloween.getInstance().getSettingsHandler().isScarySoundEvent()) {
            countDown = instance.getMathUtils().pickRandomItem(instance.getSettingsHandler().getScarySoundDelay());
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
        // load the sound which should be played.
        soundToPlay.add(Sound.WOLF_GROWL);
        soundToPlay.add(Sound.WOLF_HOWL);
        soundToPlay.add(Sound.ZOMBIE_UNFECT);
        soundToPlay.add(Sound.WITHER_SPAWN);
        soundToPlay.add(Sound.WITHER_SHOOT);
        soundToPlay.add(Sound.ITEM_BREAK);
        soundToPlay.add(Sound.CREEPER_HISS);
        soundToPlay.add(Sound.GHAST_SCREAM);
        soundToPlay.add(Sound.COW_WALK);
    }

    /**
     * Launches the event for a certain player.
     * @param player The player for whom the event should be launched.
     */
    @Override
    public void launch(final Player player) {
        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(player.getWorld().getName())) {
            // generate the sound id which means that the system picks a sound from the list.
            final int soundID = Halloween.getInstance().getMathUtils().getRandom(0, soundToPlay.size() - 1);
            // play the picked sound.
            player.playSound(player.getLocation(), soundToPlay.get(soundID), 3, 1);
        }
    }

}
