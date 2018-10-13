package de.pxav.halloween.events;

import org.bukkit.entity.Player;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This interface should be implemented in event classes. These
 * are classes which provide an event during the game.
 *
 * @author pxav.
 * (c) 2018
 */

public interface IEvent {

    /**
     * Starts the scheduler of the event. So that
     * the event can be launched every x seconds.
     */
    void startScheduler();

    /**
     * Stops the scheduler. It's recommended to do this
     * on the plugin disable.
     */
    void stopScheduler();

    /**
     * All things which need to be prepared
     * for the event go here. This includes for
     * example preparing the item stacks.
     */
    void prepare();

    /**
     * Launches the event for only one player.
     * @param player The player for whom the event should be launched.
     */
    void launch(final Player player);

}
