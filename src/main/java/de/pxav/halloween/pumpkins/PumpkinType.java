package de.pxav.halloween.pumpkins;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This enum describes the type of a certain pumpkin.
 *
 * @author pxav.
 * (c) 2018
 */

public enum PumpkinType {

    // pumpkins which turn a light on and off.
    GLOWING,

    // pumpkins which play smoke effects around them.
    SMOKING,

    // pumpkins which play lightning effects when a player comes near
    LIGHTNING,

    // pumpkins which jump scare players if they are near
    JUMP_SCARE,

    // pumpkins which play an effect when they are clicked
    CLICKABLE,

    // represents: 'no pumpkin found'
    NONE

}
