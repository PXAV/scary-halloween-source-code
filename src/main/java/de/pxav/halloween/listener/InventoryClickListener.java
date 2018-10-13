package de.pxav.halloween.listener;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.configuration.SettingsHandler;
import de.pxav.halloween.events.EventLauncherState;
import de.pxav.halloween.items.ItemBuilder;
import de.pxav.halloween.items.PumpkinPlaceInventory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This listener is triggered when a player clicks in his
 * inventory.
 *
 * @author pxav.
 * (c) 2018
 */

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if(event.getClickedInventory() != null
                && event.getCurrentItem() != null
                && event.getWhoClicked() instanceof Player
                && event.getCurrentItem().getItemMeta() != null
                && event.getCurrentItem().getItemMeta().getDisplayName() != null) {

            final Halloween instance = Halloween.getInstance();
            final SettingsHandler settingsHandler = instance.getSettingsHandler();
            final Player player = (Player) event.getWhoClicked();

            if(event.getClickedInventory().getName().equalsIgnoreCase(settingsHandler.getScareInventoryTitle())) {
                event.setCancelled(true);
                final Player targetPlayer = instance.getPlayerHandler().scaringPlayer.get(player);
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryJumpScare())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {
                        if(!instance.getJumpScareEvent().playerHelmets.containsKey(player.getUniqueId())) {
                            player.sendMessage(settingsHandler.getScaredPlayer(targetPlayer.getName()));
                            player.closeInventory();
                            // launch event
                            instance.getJumpScareEvent().launch(targetPlayer);
                        } else
                            player.sendMessage(settingsHandler.getPleaseWaitMessage());
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryPlaySound())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {
                        player.sendMessage(settingsHandler.getScaredPlayer(targetPlayer.getName()));
                        player.closeInventory();
                        // launch event
                        instance.getScarySoundEvent().launch(targetPlayer);
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryLightning())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {
                        player.sendMessage(settingsHandler.getScaredPlayer(targetPlayer.getName()));
                        player.closeInventory();
                        // launch event
                        instance.getFakeLightningEvent().launch(targetPlayer);
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventorySpawnGhost())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {
                        player.sendMessage(settingsHandler.getScaredPlayer(targetPlayer.getName()));
                        player.closeInventory();
                        // launch event
                        instance.getSpawnGhostEvent().launch(targetPlayer);
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryScaryFlight())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {
                        if(!instance.getAirBoostEvent().playerTasks.containsKey(targetPlayer.getUniqueId())) {
                            player.sendMessage(settingsHandler.getScaredPlayer(targetPlayer.getName()));
                            player.closeInventory();
                            // launch event
                            instance.getAirBoostEvent().launch(targetPlayer);
                        } else
                            player.sendMessage(settingsHandler.getPleaseWaitMessage());
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryFlyingJack())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {

                        player.sendMessage(settingsHandler.getScaredPlayer(targetPlayer.getName()));
                        player.closeInventory();
                        // launch event
                        instance.getFlyingJackEvent().launch(targetPlayer);
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryTrickOrTreatChest())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {
                        final EventLauncherState eventLauncherState = Halloween.getInstance().getTrickOrTreatEvent().launch(targetPlayer);
                        if(eventLauncherState == EventLauncherState.SUCCESS)
                            player.sendMessage(settingsHandler.getScaredPlayer(targetPlayer.getName()));
                        else if(eventLauncherState == EventLauncherState.FAILED_NO_SPACE)
                            player.sendMessage(settingsHandler.getNoSpaceFailure());
                        else if(eventLauncherState == EventLauncherState.FAILED_OTHER)
                            player.sendMessage(settingsHandler.getUnknownFailure());
                        player.closeInventory();
                        // launch event
                        instance.getFlyingJackEvent().launch(targetPlayer);
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryBatAttack())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {
                        player.sendMessage(settingsHandler.getScaredPlayer(targetPlayer.getName()));
                        player.closeInventory();
                        // launch event
                        instance.getBatAttackEvent().launch(targetPlayer);
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryPlayerGrave())
                        || event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getScareInventoryNotAvailable())) {
                    if(Halloween.getInstance().getSettingsHandler().getAffectedWorlds().contains(targetPlayer.getWorld().getName())) {
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 3, 1);
                        player.sendMessage(settingsHandler.getFeatureUnavailable());
                    } else
                        player.sendMessage(Halloween.getInstance().getSettingsHandler().getPlayerNotInWorld());

                }
            } else if(event.getClickedInventory().getName().equalsIgnoreCase(settingsHandler.getPumpkinInventoryTitle())) {
                event.setCancelled(true);
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getPumpkinInventoryClickable())) {
                    player.getInventory().addItem(new ItemBuilder(Material.PUMPKIN).setDisplayName(PumpkinPlaceInventory.PLACE_CLICKABLE).build());
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 10F);
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getPumpkinInventoryLightning())) {
                    player.getInventory().addItem(new ItemBuilder(Material.PUMPKIN).setDisplayName(PumpkinPlaceInventory.PLACE_LIGHTNING).build());
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 10F);
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getPumpkinInventorySmoking())) {
                    player.getInventory().addItem(new ItemBuilder(Material.PUMPKIN).setDisplayName(PumpkinPlaceInventory.PLACE_SMOKING).build());
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 10F);
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getPumpkinInventoryGlowing())) {
                    player.getInventory().addItem(new ItemBuilder(Material.PUMPKIN).setDisplayName(PumpkinPlaceInventory.PLACE_GLOWING).build());
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 10F);
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(settingsHandler.getPumpkinInventoryJumpScare())) {
                    player.getInventory().addItem(new ItemBuilder(Material.PUMPKIN).setDisplayName(PumpkinPlaceInventory.PLACE_JUMP_SCARE).build());
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 10F);
                }
            }

        }
    }

}
