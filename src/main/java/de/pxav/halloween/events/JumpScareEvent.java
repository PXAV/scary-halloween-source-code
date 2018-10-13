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
 *
 * This event Jump Scares a player with a scary sound and a pumpkin
 * on his head as a helmet.
 *
 * @author pxav.
 * (c) 2018
 */

public class JumpScareEvent implements IEvent {

    private int task;
    public int countDown;

    private ItemStack pumpkinHelmet;

    public Map<UUID, ItemStack> playerHelmets = new HashMap<>();

    /**
     * Starts the event scheduler.
     */
    @Override
    public void startScheduler() {
        final Halloween instance = Halloween.getInstance();
        if(Halloween.getInstance().getSettingsHandler().isRandomPumpkinJumpScares()) {
            countDown = instance.getMathUtils().pickRandomItem(instance.getSettingsHandler().getJumpScareDelay());
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Halloween.getInstance(), () -> {

                if(countDown == 10) {
                    Bukkit.getOnlinePlayers().forEach(current -> {
                        if(instance.getSettingsHandler().getAffectedWorlds().contains(current.getWorld().getName()))
                            current.playSound(current.getLocation(), Sound.ENDERMAN_IDLE, 3, 1);
                    });
                }

                if(countDown == 0) {
                    // finally launch the event
                    Bukkit.getOnlinePlayers().forEach(this::launch);

                    // reset the helmets after a certain time
                    Bukkit.getScheduler().runTaskLater(instance, this::startScheduler, 120L);

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
        pumpkinHelmet = new ItemBuilder(Material.PUMPKIN)
                .setDisplayName(Halloween.getInstance().getSettingsHandler().getJumpScarePumpkinDisplayName())
                .setAmount(1)
                .addLoreAll(Halloween.getInstance().getSettingsHandler().getJumpScarePumpkinLore())
                .build();
    }

    @Override
    public void launch(final Player player) {
        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(player.getWorld().getName())) {
            // saves the old helmet of the player, so that the players don't
            // lose their items during the event.
            playerHelmets.put(player.getUniqueId(), player.getInventory().getHelmet());

            // plays a scary sound.
            player.playSound(player.getLocation(), Sound.ENDERMAN_SCREAM, 3F, 1F);

            // sets the pumpkin helmet.
            player.getInventory().setHelmet(pumpkinHelmet);

            // reset the helmets after a certain time.
            Bukkit.getScheduler().runTaskLater(Halloween.getInstance(), () -> {
                player.getInventory().setHelmet(this.playerHelmets.get(player.getUniqueId()));
                this.playerHelmets.remove(player.getUniqueId());
            }, 120L);




        }
    }
}
