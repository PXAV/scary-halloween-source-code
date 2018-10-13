package de.pxav.halloween.events;

import de.pxav.halloween.Halloween;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 * <p>
 * basic class description
 *
 * @author pxav.
 * (c) 2018
 */

public class FlyingJackEvent implements IEvent {

    private int task;
    public int countDown;

    /**
     * Starts the event scheduler.
     */
    @Override
    public void startScheduler() {
        final Halloween instance = Halloween.getInstance();
        if(Halloween.getInstance().getSettingsHandler().isFlyingJackEvent()) {
            countDown = instance.getMathUtils().pickRandomItem(instance.getSettingsHandler().getFlyingJackDelays());
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
     * Launches the event for a certain player.
     * @param player The player for whom the event should be launched.
     */
    @Override
    public void launch(final Player player) {
        // checks if the player is in a world where the plugin is allowed to play events.
        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(player.getWorld().getName())) {

            // spawn the parent entity (flying)
            final Entity parentEntity = player.getWorld().spawnEntity(player.getEyeLocation().add(0, 1, 0), EntityType.BAT);
            // spawn the child entity (carrying the lantern)
            final Entity childEntity = player.getWorld().spawnEntity(player.getEyeLocation().add(0, 1, 0), EntityType.ARMOR_STAND);

            // specify the entities.
            final Bat bat = (Bat) parentEntity;
            final ArmorStand armorStand = (ArmorStand) childEntity;

            // make the bat invisible and make the armor stand riding the bat.
            bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
            bat.setPassenger(armorStand);

            // add custom effects to the armor stand.
            armorStand.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
            // add further effects.
            armorStand.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN));
            armorStand.setVisible(false);
            armorStand.setBasePlate(false);
            armorStand.setCustomNameVisible(false);
            armorStand.setSmall(true);
        }
    }

}
