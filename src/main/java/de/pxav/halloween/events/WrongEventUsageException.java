package de.pxav.halloween.events;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This exception is triggered when a class is implementing
 * the event interface handles the event wrong.
 *
 * @author pxav.
 * (c) 2018
 */

public class WrongEventUsageException extends RuntimeException {

    public WrongEventUsageException() {
        super();
    }

    public WrongEventUsageException(final String message) {
        super(message);
    }

    public WrongEventUsageException(final String message, Throwable cause) {
        super(message, cause);
    }

    public WrongEventUsageException(final Throwable cause) {
        super(cause);
    }

}
