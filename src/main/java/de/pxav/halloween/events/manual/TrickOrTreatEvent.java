package de.pxav.halloween.events.manual;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.events.EventLauncherState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This event will spawn a chest in front of the player which
 * contains items from the config.
 *
 * Directions in relation to the axes.
 * NORTH: Z negative (-)
 * SOUTH: Z positive (+)
 * EAST: X negative (-)
 * WEST: X positive (+)
 *
 * @author pxav.
 * (c) 2018
 */

public class TrickOrTreatEvent {

    /**
     * Launches the TrickOrTreat event and will spawn a chest filled with
     * items from the config in front of the player.
     * @param player The player who should get the chest.
     * @return Returns if the event was successful or if there wasn't enough
     *         space left around the player to spawn a chest. This can be used
     *         to build warn messages.
     */
    public EventLauncherState launch(final Player player) {

        Location location = new Location(player.getWorld(), 0, 0, 0);
        boolean foundLocation = false;

        // first of all the plugin checks if the player is looking in the north.
        if(Halloween.getInstance().getPlayerHandler().yawToFace(player.getLocation().getYaw(), false) == BlockFace.NORTH) {
            // if this is true, the plugin will check the blocks in front of him to place the chest there.
            for (int i = 2; i < 12; i++) {
                if (player.getWorld().getBlockAt(player.getLocation().subtract(0, 0, i)).getType() == Material.AIR) {
                    foundLocation = true;
                    location = player.getLocation().subtract(0, 0, i);
                    break;
                }
            }

            // if no block was free the plugin will check upper layers. In total it will check two layers.
            // if both are not free the method will return with a failure message.
            if (!foundLocation) {
                for (int i = 2; i < 12; i++) {
                    if (player.getWorld().getBlockAt(player.getLocation().subtract(0, -1, i)).getType() == Material.AIR) {
                        foundLocation = true;
                        location = player.getLocation().subtract(0, -1, i);
                        break;
                    }
                }

                if (!foundLocation) {
                    for (int i = 2; i < 12; i++) {
                        if (player.getWorld().getBlockAt(player.getLocation().subtract(0, -2, i)).getType() == Material.AIR) {
                            foundLocation = true;
                            location = player.getLocation().subtract(0, -2, i);
                            break;
                        }
                    }

                    if (!foundLocation)
                        return EventLauncherState.FAILED_NO_SPACE;

                }

            }

        // the plugin does the same with all other directions (if needed!).
        } else if(Halloween.getInstance().getPlayerHandler().yawToFace(player.getLocation().getYaw(), false) == BlockFace.SOUTH) {
            for (int i = 2; i < 12; i++) {
                if (player.getWorld().getBlockAt(player.getLocation().add(0, 0, i)).getType() == Material.AIR) {
                    foundLocation = true;
                    location = player.getLocation().add(0, 0, i);
                    break;
                }
            }

            if (!foundLocation) {
                for (int i = 2; i < 12; i++) {
                    if (player.getWorld().getBlockAt(player.getLocation().add(0, 1, i)).getType() == Material.AIR) {
                        foundLocation = true;
                        location = player.getLocation().add(0, 1, i);
                        break;
                    }
                }

                if (!foundLocation) {
                    for (int i = 2; i < 12; i++) {
                        if (player.getWorld().getBlockAt(player.getLocation().add(0, 2, i)).getType() == Material.AIR) {
                            foundLocation = true;
                            location = player.getLocation().add(0, 2, i);
                            break;
                        }
                    }

                    if (!foundLocation)
                        return EventLauncherState.FAILED_NO_SPACE;

                }

            }
        } else if(Halloween.getInstance().getPlayerHandler().yawToFace(player.getLocation().getYaw(), false) == BlockFace.EAST) {
            for (int i = 2; i < 12; i++) {
                if (player.getWorld().getBlockAt(player.getLocation().add(i, 0, 0)).getType() == Material.AIR) {
                    foundLocation = true;
                    location = player.getLocation().add(i, 0, 0);
                    break;
                }
            }

            if (!foundLocation) {
                for (int i = 2; i < 12; i++) {
                    if (player.getWorld().getBlockAt(player.getLocation().add(i, 1, 0)).getType() == Material.AIR) {
                        foundLocation = true;
                        location = player.getLocation().add(i, -1, 0);
                        break;
                    }
                }

                if (!foundLocation) {
                    for (int i = 2; i < 12; i++) {
                        if (player.getWorld().getBlockAt(player.getLocation().add(i, 2, 0)).getType() == Material.AIR) {
                            foundLocation = true;
                            location = player.getLocation().add(i, -2, 0);
                            break;
                        }
                    }

                    if (!foundLocation)
                        return EventLauncherState.FAILED_NO_SPACE;

                }

            }
        } else if(Halloween.getInstance().getPlayerHandler().yawToFace(player.getLocation().getYaw(), false) == BlockFace.WEST) {
            for (int i = 2; i < 12; i++) {
                if (player.getWorld().getBlockAt(player.getLocation().subtract(i, 0, 0)).getType() == Material.AIR) {
                    foundLocation = true;
                    location = player.getLocation().subtract(i, 0, 0);
                    break;
                }
            }

            if (!foundLocation) {
                for (int i = 2; i < 12; i++) {
                    if (player.getWorld().getBlockAt(player.getLocation().subtract(i, -1, 0)).getType() == Material.AIR) {
                        foundLocation = true;
                        location = player.getLocation().subtract(i, -1, 0);
                        break;
                    }
                }

                if (!foundLocation) {
                    for (int i = 2; i < 12; i++) {
                        if (player.getWorld().getBlockAt(player.getLocation().subtract(i, -2, 0)).getType() == Material.AIR) {
                            foundLocation = true;
                            location = player.getLocation().subtract(i, -2, 0);
                            break;
                        }
                    }

                    if (!foundLocation)
                        return EventLauncherState.FAILED_NO_SPACE;

                }

            }
        }

        // When the plugin has found a location to spawn the chest it
        // will finally spawn the chest block.
        final Block block = player.getWorld().getBlockAt(location);
        block.setType(Material.CHEST);

        final Chest chest = (Chest) block.getState();

        // Then it will generate random slots to fill in the
        // items.
        final List<Integer> usedSlots = new ArrayList<>();
        int slot = Halloween.getInstance().getMathUtils().getRandom(0, 26);
        int pickedItems = 0;

        while (usedSlots.contains(slot)) {
            slot = Halloween.getInstance().getMathUtils().getRandom(0, 26);
        }

        while (pickedItems <= Halloween.getInstance().getSettingsHandler().getPickAmount()) {
            pickedItems++;
            usedSlots.add(slot);
            chest.getBlockInventory().setItem(slot,
                    Halloween.getInstance().getSettingsHandler().getChestItems().get(Halloween.getInstance().getMathUtils().getRandom(0,
                            Halloween.getInstance().getSettingsHandler().getChestItems().size() - 1)));
        }

        // return success, if the chest could be placed.
        return EventLauncherState.SUCCESS;

    }

}