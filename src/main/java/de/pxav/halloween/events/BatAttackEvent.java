package de.pxav.halloween.events;

import de.pxav.halloween.Halloween;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This event spawns a custom amount of bats near the player.
 * These have a configurable display name.
 *
 * @author pxav.
 * (c) 2018
 */

public class BatAttackEvent implements IEvent {

    private int task;
    public int countDown;

    public Map<UUID, List<Entity>> playerEntities = new HashMap<>();

    /**
     * Starts the event scheduler.
     */
    @Override
    public void startScheduler() {
        final Halloween instance = Halloween.getInstance();
        if(Halloween.getInstance().getSettingsHandler().isBatAttackEvent()) {
            countDown = instance.getMathUtils().pickRandomItem(instance.getSettingsHandler().getBatAttackDelay());
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Halloween.getInstance(), () -> {
                if(countDown == 0) {
                    // finally launch the event
                    Bukkit.getOnlinePlayers().forEach(this::launch);

                    // reset the helmets after a certain time
                    Bukkit.getScheduler().runTaskLater(instance, this::startScheduler, 200L);

                    // stop the event scheduler
                    this.stopScheduler();
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
        Bukkit.getOnlinePlayers().forEach(current
                -> this.playerEntities.put(current.getUniqueId(), new ArrayList<>()));
    }

    /**
     * Launches the event for a certain player.
     * @param player The player for whom the event should be launched.
     */
    @Override
    public void launch(final Player player) {
        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(player.getWorld().getName())) {

            final List<Entity> list = this.playerEntities.get(player.getUniqueId());

            // executes the entity spawn x times (value configurable in config.yml!)
            for (int i = 0; i < Halloween.getInstance().getSettingsHandler().getBatAttackSpawnAmount(); i++) {
                final Entity batEntity = player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.BAT);
                batEntity.setCustomName(Halloween.getInstance().getSettingsHandler().getAttackBatName());
                batEntity.setCustomNameVisible(true);
                list.add(batEntity);
            }

            this.playerEntities.put(player.getUniqueId(), list);


            // plays a scary sound.
            player.playSound(player.getLocation(), Sound.BAT_DEATH, 3F, 1F);
            player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 3F, 1F);

            // remove the bats after some time
            Bukkit.getScheduler().runTaskLater(Halloween.getInstance(), ()
                   -> this.playerEntities.get(player.getUniqueId()).forEach(Entity::remove), 200L);

        }
    }
}
