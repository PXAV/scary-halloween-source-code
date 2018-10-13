package de.pxav.halloween.pumpkins;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This class handles all information about the event pumpkins.
 * This includes which type has how many pumpkins and where they are placed.
 *
 * @author pxav.
 * (c) 2018
 */

public class PumpkinHandler {

    // here will be saved all pumpkins
    public Map<PumpkinType, List<Location>> pumpkins = new HashMap<>();

    /**
     * Loads the pumpkin file including the data about the pumpkins.
     */
    public void loadPumpkinFiles() {
        final File file = new File("plugins//ScaryHalloween", "pumpkins.yml");
        final File folder = new File("plugins//ScaryHalloween");

        if(!folder.exists())
            folder.mkdirs();

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Loads all pumpkins from the config into the cache
     * to save performance.
     */
    public void loadPumpkins() {
        // get the file and the configuration
        final File file = new File("plugins//ScaryHalloween", "pumpkins.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        // iterate all pumpkin types
        for (final PumpkinType pumpkinType : PumpkinType.values()) {
            // get the entries from a type in a list from the config.
            final List<String> locationFormat = configuration.getStringList(pumpkinType.toString());
            final List<Location> locations = new ArrayList<>();
            locationFormat.forEach(current -> {
                // unpack the location format into a location object
                final String[] locationArray = current.split(":");
                locations.add(new Location(
                        Bukkit.getWorld(locationArray[0]),
                        Double.parseDouble(locationArray[1]),
                        Double.parseDouble(locationArray[2]),
                        Double.parseDouble(locationArray[3])
                ));
            });
            this.pumpkins.put(pumpkinType, locations);
        }
    }

    /**
     * Adds a pumpkin to the file and to the cache.
     * @param pumpkinType The type of the pumpkin you want to add.
     * @param location The location where the pumpkin should be placed.
     */
    public void addPumpkin(final PumpkinType pumpkinType, final Location location) {
        final File file = new File("plugins//ScaryHalloween", "pumpkins.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        // update the old list in the cache
        final List<Location> oldList = pumpkins.get(pumpkinType);
        oldList.add(location);
        pumpkins.put(pumpkinType, oldList);

        // define the format for the config entry.
        final List<String> rawFormat = configuration.getStringList(pumpkinType.toString());
        rawFormat.add(location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ());

        // finally set the list to the file
        configuration.set(pumpkinType.toString(), rawFormat);

        // save all changes in the file
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a pumpkin from the file and the
     * cache so that no effects will be played
     * anymore.
     * @param location The location of the pumpkin you want to remove.
     * @param pumpkinType The type of pumpkin you want to remove.
     */
    public void removePumpkin(final Location location, final PumpkinType pumpkinType) {
        // get the file and the configuration
        final File file = new File("plugins//ScaryHalloween", "pumpkins.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        // update the list in cache.
        final List<Location> oldList = pumpkins.get(pumpkinType);
        oldList.remove(location);
        pumpkins.put(pumpkinType, oldList);

        // remove all entries containing the matching data.
        final List<String> rawFormat = configuration.getStringList(pumpkinType.toString());
        rawFormat.remove(location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ());

        // finally set the updated list to the file
        configuration.set(pumpkinType.toString(), rawFormat);

        // save all changes in the file.
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param location The location of the pumpkin you want to check.
     * @return Returns the type of pumpkin which is located on the given location. Returns NONE if no pumpkin could be found.
     */
    public PumpkinType getPumpkinType(final Location location) {

        // iterate all pumpkin types.
        for (final PumpkinType pumpkinType : PumpkinType.values()) {
            for (final Location current : pumpkins.get(pumpkinType)) {
                // check location for pumpkin type
                if(current.getZ() == location.getZ()
                        && current.getY() == location.getY()
                        && current.getX() == location.getX()
                        && current.getWorld().getName().equalsIgnoreCase(location.getWorld().getName())) {
                    return pumpkinType;
                }
            }
        }

        return PumpkinType.NONE;
    }

}
