package de.pxav.halloween.utils;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This class contains utility methods
 *
 * @author pxav.
 * (c) 2018
 */

public class PlayerHandler {

    // contains the player which is scaring another player.
    // (Trolling player, trolled player)
    public Map<Player, Player> scaringPlayer = new HashMap<>();

    // basic directions
    private final BlockFace[] axis = {
            BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST
    };

    // more exact directions
    // these directions will be needed for another version of the plugin.
    private final BlockFace[] radial = {
            BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST
    };

    /**
     * Gets the horizontal Block Face from a given yaw angle<br>
     * This includes the NORTH_WEST faces
     *
     * @param yaw angle
     * @return The Block Face of the angle
     */
    public BlockFace yawToFace(float yaw) {
        return yawToFace(yaw, true);
    }

    /**
     * Gets the horizontal Block Face from a given yaw angle
     *
     * @param yaw angle
     * @param useSubCardinalDirections setting, True to allow NORTH_WEST to be returned
     * @return The Block Face of the angle
     */
    public BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        final BlockFace output;
        if (useSubCardinalDirections) {
            output = radial[Math.round(yaw / 45f) & 0x7];
        } else {
            output = axis[Math.round(yaw / 90f) & 0x3];
        }

        // invert results to be correct!
        if(output == BlockFace.SOUTH) {
            return BlockFace.NORTH;
        } else if(output == BlockFace.NORTH) {
            return BlockFace.SOUTH;
        } else if(output == BlockFace.EAST) {
            return BlockFace.WEST;
        } else {
            return BlockFace.EAST;
        }
    }

    /**
     * Returns
     * @param yaw angle
     * @param useSubCardinalDirections setting, True to allow NORTH_WEST to be returned
     * @return The Block Face of the angle
     */
    public BlockFace getDirection(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections) {
            return radial[Math.round(yaw / 45f) & 0x7];
        } else {
            return axis[Math.round(yaw / 90f) & 0x3];
        }
    }

}
