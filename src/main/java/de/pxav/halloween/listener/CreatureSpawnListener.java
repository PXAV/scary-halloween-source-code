package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when a creature of any type
 * spawns on the server. Depending on the settings in the config
 * it modifies the spawned entity with a simple pumpkin on
 * its head or something else.
 *
 * @author pxav.
 * (c) 2018
 */

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(final CreatureSpawnEvent event) {
        // check if creature modification is enabled in config file.
        if(Halloween.getInstance().getSettingsHandler().isModifyCreatureSpawn()) {
            // check if the world should be modified.
            if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(event.getEntity().getLocation().getWorld().getName())) {
                // get the reason why the entity spawned
                final CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();

                // get the entity and type of the entity that spawned.
                final LivingEntity entity = event.getEntity();
                final EntityType entityType = entity.getType();

                // check if the modification does not affect any other plugins (SpawnType.CUSTOM)
                // For a detailed overview over the different spawn reasons check this out:
                // https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/entity/CreatureSpawnEvent.SpawnReason.html
                if(spawnReason != CreatureSpawnEvent.SpawnReason.LIGHTNING
                        && spawnReason != CreatureSpawnEvent.SpawnReason.NETHER_PORTAL
                        && spawnReason != CreatureSpawnEvent.SpawnReason.SLIME_SPLIT
                        && spawnReason != CreatureSpawnEvent.SpawnReason.CURED
                        && spawnReason != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                    // check the different entity types and modify them in the
                    // correct way:
                    if(entityType == EntityType.ZOMBIE
                            || entityType == EntityType.SKELETON
                            || entityType == EntityType.PIG_ZOMBIE) {
                        // generate a random integer for the following procedure.
                        // 0 = Do nothing; 1 = Set the helmet to a normal pumpkin;
                        // 2 = Set the helmet to a jack'o'lantern
                        // 3 = Set the helmet to a normal pumpkin
                        // 4 = a ghost will spawn
                        // 5 = a ghost will spawn
                        final int procedureID = Halloween.getInstance().getMathUtils().getRandom(0, 5);
                        if(procedureID == 1 || procedureID == 3)
                            entity.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
                        else if(procedureID == 2)
                            entity.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN));
                        else if(procedureID == 4 || procedureID == 5) {
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 2));
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
                            // checks if the ghost is a zombie. If true
                            // the plugin will give the zombie a random item.
                            // 0 = wooden sword
                            // 1 = iron sword
                            // 2 = iron axe
                            // 3 = wooden axe & chain helmet
                            // 4 = stone sword
                            // 5 = stone axe & chain helmet
                            if(entityType == EntityType.ZOMBIE
                                    || entityType == EntityType.PIG_ZOMBIE) {
                                final int itemID = Halloween.getInstance().getMathUtils().getRandom(0, 5);
                                switch (itemID) {
                                    case 0:
                                        entity.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD));
                                        break;
                                    case 1:
                                        entity.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
                                        break;
                                    case 2:
                                        entity.getEquipment().setItemInHand(new ItemStack(Material.IRON_AXE));
                                        break;
                                    case 3:
                                        entity.getEquipment().setItemInHand(new ItemStack(Material.WOOD_AXE));
                                        entity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                                        break;
                                    case 4:
                                        entity.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
                                        break;
                                    case 5:
                                        entity.getEquipment().setItemInHand(new ItemStack(Material.STONE_AXE));
                                        entity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                                        break;
                                }
                            }
                            entity.getNearbyEntities(50, 50, 50).forEach(current -> {
                                if(current instanceof Player)
                                    if(Halloween.getInstance().getSettingsHandler().isWarnPlayerWhenGhostSpawned())
                                        current.sendMessage(Halloween.getInstance().getSettingsHandler().getGhostSpawnedNearWarning());
                            });
                        }
                    }
                }
            }
        }

    }

}
