package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.pumpkins.PumpkinType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when a player breaks a block.
 *
 * @author pxav.
 * (c) 2018
 */

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {

        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final Halloween instance = Halloween.getInstance();

        // check if the player breaks a pumpkin
        if(instance.getSettingsHandler().getAffectedWorlds().contains(player.getWorld().getName()) && (event.getBlock().getType() == Material.PUMPKIN
                || event.getBlock().getType() == Material.JACK_O_LANTERN)) {
            // get the type of the pumpkin (probably it's an event pumpkin.)
            final PumpkinType pumpkinType = instance.getPumpkinHandler().getPumpkinType(block.getLocation());
            System.out.println(pumpkinType);

            // if the pumpkin was a custom EventPumpkin it will be removed.
            if(pumpkinType == PumpkinType.CLICKABLE) {
                if(instance.getSettingsHandler().isPlayersCanBreakEventPumpkin()
                        || player.hasPermission(instance.getSettingsHandler().getBreakPumpkinPermission())) {
                    player.sendMessage(instance.getSettingsHandler().getRemovedPumpkin(instance.getPumpkinHandler().getPumpkinType(block.getLocation()).toString()));
                    instance.getPumpkinHandler().removePumpkin(block.getLocation(), instance.getPumpkinHandler().getPumpkinType(block.getLocation()));
                } else {
                    event.setCancelled(true);
                    player.sendMessage(instance.getSettingsHandler().getCannotBreakBlock());
                }
            } else if(pumpkinType == PumpkinType.LIGHTNING) {
                if(instance.getSettingsHandler().isPlayersCanBreakEventPumpkin()
                        || player.hasPermission(instance.getSettingsHandler().getBreakPumpkinPermission())) {
                    player.sendMessage(instance.getSettingsHandler().getRemovedPumpkin(instance.getPumpkinHandler().getPumpkinType(block.getLocation()).toString()));
                    instance.getPumpkinHandler().removePumpkin(block.getLocation(), instance.getPumpkinHandler().getPumpkinType(block.getLocation()));
                } else {
                    event.setCancelled(true);
                    player.sendMessage(instance.getSettingsHandler().getCannotBreakBlock());
                }
            } else if(pumpkinType == PumpkinType.GLOWING) {
                if(instance.getSettingsHandler().isPlayersCanBreakEventPumpkin()
                        || player.hasPermission(instance.getSettingsHandler().getBreakPumpkinPermission())) {
                    player.sendMessage(instance.getSettingsHandler().getRemovedPumpkin(instance.getPumpkinHandler().getPumpkinType(block.getLocation()).toString()));
                    instance.getPumpkinHandler().removePumpkin(block.getLocation(), instance.getPumpkinHandler().getPumpkinType(block.getLocation()));
                } else {
                    event.setCancelled(true);
                    player.sendMessage(instance.getSettingsHandler().getCannotBreakBlock());
                }
            } else if(pumpkinType == PumpkinType.SMOKING) {
                if(instance.getSettingsHandler().isPlayersCanBreakEventPumpkin()
                        || player.hasPermission(instance.getSettingsHandler().getBreakPumpkinPermission())) {
                    player.sendMessage(instance.getSettingsHandler().getRemovedPumpkin(instance.getPumpkinHandler().getPumpkinType(block.getLocation()).toString()));
                    instance.getPumpkinHandler().removePumpkin(block.getLocation(), instance.getPumpkinHandler().getPumpkinType(block.getLocation()));
                } else {
                    event.setCancelled(true);
                    player.sendMessage(instance.getSettingsHandler().getCannotBreakBlock());
                }
            } else if(pumpkinType == PumpkinType.JUMP_SCARE) {
                if(instance.getSettingsHandler().isPlayersCanBreakEventPumpkin()
                        || player.hasPermission(instance.getSettingsHandler().getBreakPumpkinPermission())) {
                    player.sendMessage(instance.getSettingsHandler().getRemovedPumpkin(instance.getPumpkinHandler().getPumpkinType(block.getLocation()).toString()));
                    instance.getPumpkinHandler().removePumpkin(block.getLocation(), instance.getPumpkinHandler().getPumpkinType(block.getLocation()));
                } else {
                    event.setCancelled(true);
                    player.sendMessage(instance.getSettingsHandler().getCannotBreakBlock());
                }
            }
        }

    }

}
