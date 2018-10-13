package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.items.PumpkinPlaceInventory;
import de.pxav.halloween.pumpkins.PumpkinType;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when a player places a block.
 *
 * @author pxav.
 * (c) 2018
 */

public class BlockPlaceListener implements Listener {

    @EventHandler @SuppressWarnings("deprecation")
    public void onBlockPlace(final BlockPlaceEvent event) {
        if(event.getBlock() != null
                && event.getPlayer().getItemInHand() != null) {

            final Player player = event.getPlayer();
            final Block block = event.getBlockPlaced();
            final ItemStack itemInHand = player.getItemInHand();

            if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null) {
                if(itemInHand.getType() == Material.PUMPKIN) {
                    if(itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(PumpkinPlaceInventory.PLACE_CLICKABLE)) {
                        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(block.getWorld().getName())) {
                            Halloween.getInstance().getPumpkinHandler().addPumpkin(PumpkinType.CLICKABLE, block.getLocation());
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10F, 10F);
                            player.playEffect(block.getLocation(), Effect.FLAME, 1);
                            player.playEffect(block.getLocation().add(1, 0, 0), Effect.FLAME, 1);
                            player.playEffect(block.getLocation().add(0, 1, 0), Effect.FLAME, 1);
                            player.playEffect(block.getLocation().add(0, 0, 1), Effect.FLAME, 1);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlacedPumpkin(PumpkinType.CLICKABLE.toString()));
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getCannotPlaceHere());
                        }
                    }  else if(itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(PumpkinPlaceInventory.PLACE_SMOKING)) {
                        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(block.getWorld().getName())) {
                            Halloween.getInstance().getPumpkinHandler().addPumpkin(PumpkinType.SMOKING, block.getLocation());
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10F, 10F);
                            player.playEffect(block.getLocation(), Effect.SMOKE, 1);
                            player.playEffect(block.getLocation().add(1, 0, 0), Effect.SMOKE, 1);
                            player.playEffect(block.getLocation().add(0, 1, 0), Effect.SMOKE, 1);
                            player.playEffect(block.getLocation().add(0, 0, 1), Effect.SMOKE, 1);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlacedPumpkin(PumpkinType.SMOKING.toString()));
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getCannotPlaceHere());
                        }
                    } else if(itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(PumpkinPlaceInventory.PLACE_LIGHTNING)) {
                        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(block.getWorld().getName())) {
                            Halloween.getInstance().getPumpkinHandler().addPumpkin(PumpkinType.LIGHTNING, block.getLocation());
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10F, 10F);
                            player.playEffect(block.getLocation(), Effect.CLOUD, 1);
                            player.playEffect(block.getLocation().add(1, 0, 0), Effect.CLOUD, 1);
                            player.playEffect(block.getLocation().add(0, 1, 0), Effect.MAGIC_CRIT, 1);
                            player.playEffect(block.getLocation().add(0, 0, 1), Effect.MAGIC_CRIT, 1);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlacedPumpkin(PumpkinType.LIGHTNING.toString()));
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getCannotPlaceHere());
                        }
                    } else if(itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(PumpkinPlaceInventory.PLACE_GLOWING)) {
                        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(block.getWorld().getName())) {
                            Halloween.getInstance().getPumpkinHandler().addPumpkin(PumpkinType.GLOWING, block.getLocation());
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10F, 10F);
                            player.playEffect(block.getLocation(), Effect.WATERDRIP, 1);
                            player.playEffect(block.getLocation().add(1, 0, 0), Effect.WATERDRIP, 1);
                            player.playEffect(block.getLocation().add(0, 1, 0), Effect.HAPPY_VILLAGER, 1);
                            player.playEffect(block.getLocation().add(0, 0, 1), Effect.HAPPY_VILLAGER, 1);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlacedPumpkin(PumpkinType.GLOWING.toString()));
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getCannotPlaceHere());
                        }
                    } else if(itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(PumpkinPlaceInventory.PLACE_JUMP_SCARE)) {
                        if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(block.getWorld().getName())) {
                            Halloween.getInstance().getPumpkinHandler().addPumpkin(PumpkinType.JUMP_SCARE, block.getLocation());
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10F, 10F);
                            for (int i = 0; i < 10; i++) {
                                player.playEffect(block.getLocation(), Effect.GHAST_SHOOT, 1);
                                player.playEffect(block.getLocation(), Effect.GHAST_SHRIEK, 1);
                            }
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlacedPumpkin(PumpkinType.JUMP_SCARE.toString()));
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(Halloween.getInstance().getSettingsHandler().getCannotPlaceHere());
                        }
                    }
                }
            }
        }
    }

}
