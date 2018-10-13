package de.pxav.halloween.events;

import de.pxav.halloween.Halloween;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 * <p>
 * basic class description
 *
 * @author pxav.
 * (c) 2018
 */

public class SpawnGhostEvent implements IEvent {

    private int task;
    public int countDown;

    private final List<EntityType> ghostTypes = new ArrayList<>();

    /**
     * Starts the event scheduler.
     */
    @Override
    public void startScheduler() {
        final Halloween instance = Halloween.getInstance();
        if(Halloween.getInstance().getSettingsHandler().isScarySoundEvent()) {
            countDown = instance.getMathUtils().pickRandomItem(instance.getSettingsHandler().getSpawnGhostDelay());
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
        ghostTypes.add(EntityType.ZOMBIE);
        ghostTypes.add(EntityType.SKELETON);
    }

    @Override
    public void launch(final Player player) {
        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(player.getWorld().getName())) {
            Entity ghost;
            final int typeID = Halloween.getInstance().getMathUtils().getRandom(0, this.ghostTypes.size() - 1);
            if(player.getWorld().getBlockAt(player.getLocation().add(4, 0, 0)).getType() == Material.AIR)
                ghost = player.getWorld().spawnEntity(player.getLocation().add(4, 0, 0), this.ghostTypes.get(typeID));
            else if(player.getWorld().getBlockAt(player.getLocation().add(0, 0, 4)).getType() == Material.AIR)
                ghost = player.getWorld().spawnEntity(player.getLocation().add(0, 0, 4), this.ghostTypes.get(typeID));
            else
                ghost = player.getWorld().spawnEntity(player.getLocation().add(4, 0, 4), this.ghostTypes.get(typeID));

            final LivingEntity livingEntity = (LivingEntity) ghost;
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 2));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));

            if(ghost.getType() == EntityType.ZOMBIE) {
                // give the zombie his equipment
                final int itemID = Halloween.getInstance().getMathUtils().getRandom(0, 5);
                switch (itemID) {
                    case 0:
                        livingEntity.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD));
                        final ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET);
                        final LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
                        leatherArmorMeta.setColor(Color.BLUE);
                        itemStack.setItemMeta(leatherArmorMeta);
                        livingEntity.getEquipment().setHelmet(itemStack);
                        break;
                    case 1:
                        livingEntity.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
                        final ItemStack whiteItemStack = new ItemStack(Material.LEATHER_HELMET);
                        final LeatherArmorMeta whiteLeatherArmorMeta = (LeatherArmorMeta) whiteItemStack.getItemMeta();
                        whiteLeatherArmorMeta.setColor(Color.WHITE);
                        whiteItemStack.setItemMeta(whiteLeatherArmorMeta);
                        break;
                    case 2:
                        livingEntity.getEquipment().setItemInHand(new ItemStack(Material.IRON_AXE));
                        livingEntity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        break;
                    case 3:
                        livingEntity.getEquipment().setItemInHand(new ItemStack(Material.WOOD_AXE));
                        livingEntity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        livingEntity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                        break;
                    case 4:
                        livingEntity.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
                        livingEntity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                        break;
                    case 5:
                        livingEntity.getEquipment().setItemInHand(new ItemStack(Material.STONE_AXE));
                        livingEntity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        break;
                }
            }
            ghost.getNearbyEntities(50, 50, 50).forEach(current -> {
                if(current instanceof Player)
                    if(Halloween.getInstance().getSettingsHandler().isWarnPlayerWhenGhostSpawned())
                        current.sendMessage(Halloween.getInstance().getSettingsHandler().getGhostSpawnedNearWarning());
            });
        }
    }
}
