package de.pxav.halloween.utils;

import java.util.List;
import java.util.Random;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This class contains useful methods for mathematical functions
 * and calculations as well as calculations of time periods.
 *
 * @author pxav.
 * (c) 2018
 */

public class MathUtils {

    private final Random random = new Random();

    /**
     * Generates a random integer between the
     * given maximum and minimum bound.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The final generated value.
     */
    public int getRandom(final int min, final int max) {
        // Return the generated integer.
        return random.nextInt((max - min) + min);
    }

    /**
     * Picks a random item ({@code Integer}) from a list.
     * @param list The list of which the item
     *             should be picked from.
     * @return The final return value.
     */
    public int pickRandomItem(final List<Integer> list) {
        // check if the list isn't empty (empty -> no items)
        if(!list.isEmpty())
            return list.get(this.getRandom(0, list.size() - 1));
        return 0;
    }

    /**
     * Returns a {@code String} with a more readable time
     * format. Instead of {@code 3728 seconds} it will
     * show {@code 01:02:08}.
     * @param timeInSeconds The seconds which should be converted.
     * @return The final time format as a String.
     */
    public String getTimeFormat(int timeInSeconds) {
        int hours = 0;
        int minutes = 0;
        int seconds = timeInSeconds;

        while (seconds >= 3600) {
            seconds -= 3600;
            hours++;
        }

        while (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }

        String outputHours;
        String outputMinutes;
        String outputSeconds;

        if(seconds < 10)
            outputSeconds = "0" + seconds;
        else outputSeconds = String.valueOf(seconds);
        if(minutes < 10)
            outputMinutes = "0" + minutes;
        else outputMinutes = String.valueOf(minutes);
        if(hours < 10)
            outputHours = "0" + hours;
        else outputHours = String.valueOf(hours);

        return outputHours + ":" + outputMinutes + ":" + outputSeconds;
    }

}
