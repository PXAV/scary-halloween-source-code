package de.pxav.halloween.commands;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.configuration.SettingsHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This class handles the main command of the plugin.
 * This includes:
 * - starting events
 * - see plugin information
 * - see event information
 * - scare players
 * - place pumpkins
 *
 * @author pxav.
 * (c) 2018
 */

public class HalloweenCommand implements CommandExecutor {

    /**
     * The constructor to register the command in the plugin.
     * You also need to register the command in the plugin.yml!
     * @param command The command string.
     */
    public HalloweenCommand(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    /**
     * This is the method which handles the command itself.
     * This method is only executed by the spigot server,
     * which means that the parameters are not relevant for you.
     */
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {

        final Halloween instance = Halloween.getInstance();
        final SettingsHandler settingsHandler = instance.getSettingsHandler();

        if(commandSender instanceof Player) {
            final Player player = (Player) commandSender;

            if(player.hasPermission(settingsHandler.getHalloweenCommandPermission())) {
                if(args.length == 0) {
                    this.sendUsage(player, settingsHandler);
                } else if(args.length == 1 && args[0].equalsIgnoreCase("events")) {
                    player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
                    player.sendMessage(settingsHandler.getPrefix() + " §eBelow some information about the events§8:");

                    // jump scare
                    if(instance.getSettingsHandler().isRandomPumpkinJumpScares())
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lJumpScare §8× §2§l✔ §8» §6§lNext time §e"
                                + instance.getMathUtils().getTimeFormat(instance.getJumpScareEvent().countDown) + " hours");
                    else player.sendMessage(settingsHandler.getPrefix() + " §6§lJumpScare §8× §c✖");

                    // bat attack
                    if(instance.getSettingsHandler().isBatAttackEvent())
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lBatAttack §8× §2§l✔ §8» §6§lNext time §e"
                                + instance.getMathUtils().getTimeFormat(instance.getBatAttackEvent().countDown) + " hours");
                    else player.sendMessage(settingsHandler.getPrefix() + " §6§lBatAttack §8× §c✖");

                    // scary sound
                    if(instance.getSettingsHandler().isScarySoundEvent())
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lScarySound §8× §2§l✔ §8» §6§lNext time §e"
                                + instance.getMathUtils().getTimeFormat(instance.getScarySoundEvent().countDown) + " hours");
                    else player.sendMessage(settingsHandler.getPrefix() + " §6§lScarySound §8× §c✖");

                    // fake lightning
                    if(instance.getSettingsHandler().isFakeLightningEvent())
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lFakeLightning §8× §2§l✔ §8» §6§lNext time §e"
                                + instance.getMathUtils().getTimeFormat(instance.getFakeLightningEvent().countDown) + " hours");
                    else player.sendMessage(settingsHandler.getPrefix() + " §6§lFakeLightning §8× §c✖");

                    // fake lightning
                    if(instance.getSettingsHandler().isSpawnGhostEvent())
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lSpawnGhost §8× §2§l✔ §8» §6§lNext time §e"
                                + instance.getMathUtils().getTimeFormat(instance.getSpawnGhostEvent().countDown) + " hours");
                    else player.sendMessage(settingsHandler.getPrefix() + " §6§lSpawnGhost §8× §c✖");

                    // fake lightning
                    if(instance.getSettingsHandler().isAirBoostEvent())
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lAirBoost §8× §2§l✔ §8» §6§lNext time §e"
                                + instance.getMathUtils().getTimeFormat(instance.getAirBoostEvent().countDown) + " hours");
                    else player.sendMessage(settingsHandler.getPrefix() + " §6§lAirBoost §8× §c✖");

                    // fake lightning
                    if(instance.getSettingsHandler().isFlyingJackEvent())
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lFlyingJack §8× §2§l✔ §8» §6§lNext time §e"
                                + instance.getMathUtils().getTimeFormat(instance.getFlyingJackEvent().countDown) + " hours");
                    else player.sendMessage(settingsHandler.getPrefix() + " §6§lFlyingJack §8× §c✖");

                    player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
                } else if(args.length == 2 && args[0].equalsIgnoreCase("startEvent")) {
                    final String input = args[1];
                    if(input.equalsIgnoreCase("JumpScare")) {
                        instance.getJumpScareEvent().countDown = 15;
                        player.sendMessage(instance.getSettingsHandler().getForcedEvent(15));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                    } else if(input.equalsIgnoreCase("BatAttack")) {
                        instance.getBatAttackEvent().countDown = 15;
                        player.sendMessage(instance.getSettingsHandler().getForcedEvent(15));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                    } else if(input.equalsIgnoreCase("ScarySound")) {
                        instance.getScarySoundEvent().countDown = 15;
                        player.sendMessage(instance.getSettingsHandler().getForcedEvent(15));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                    } else if(input.equalsIgnoreCase("FakeLightning")) {
                        instance.getFakeLightningEvent().countDown = 15;
                        player.sendMessage(instance.getSettingsHandler().getForcedEvent(15));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                    } else if(input.equalsIgnoreCase("SpawnGhost")) {
                        instance.getSpawnGhostEvent().countDown = 15;
                        player.sendMessage(instance.getSettingsHandler().getForcedEvent(15));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                    } else if(input.equalsIgnoreCase("AirBoost")) {
                        instance.getAirBoostEvent().countDown = 15;
                        player.sendMessage(instance.getSettingsHandler().getForcedEvent(15));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                    } else if(input.equalsIgnoreCase("FlyingJack")) {
                        instance.getFlyingJackEvent().countDown = 15;
                        player.sendMessage(instance.getSettingsHandler().getForcedEvent(15));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                    } else {
                        player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
                        player.sendMessage(settingsHandler.getPrefix() + " §eThe given name is invalid. Please ");
                        player.sendMessage(settingsHandler.getPrefix() + " §echoose one of these event names:");
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lJumpScare§8, §6§lBatAttack§8, §6§lRandomSound");
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lFakeLightning§8, §6§lSpawnGhost");
                        player.sendMessage(settingsHandler.getPrefix() + " §6§lAirBoost§8, §6§lFlyingJack");
                        player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
                    }
                } else if(args.length == 1 && args[0].equalsIgnoreCase("startEvent")) {
                    player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
                    player.sendMessage(settingsHandler.getPrefix() + " §ePlease also give an event name like:");
                    player.sendMessage(settingsHandler.getPrefix() + " §6§lJumpScare§8, §6§lBatAttack§8, §6§lRandomSound");
                    player.sendMessage(settingsHandler.getPrefix() + " §6§lFakeLightning§8, §6§lSpawnGhost");
                    player.sendMessage(settingsHandler.getPrefix() + " §6§lAirBoost§8, §6§lFlyingJack");
                    player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
                } else if(args.length == 2 && args[0].equalsIgnoreCase("scare")) {
                    final Player targetPlayer = Bukkit.getPlayer(args[1]);
                    // check if the player is online
                    if(targetPlayer != null) {
                        // open the inventory
                        instance.getScarePlayerInventory().open(player);
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                        // assign the trolling player to the trolled player
                        instance.getPlayerHandler().scaringPlayer.put(player, targetPlayer);
                    } else
                        player.sendMessage(settingsHandler.getPlayerNotOnline());
                } else if(args.length == 1 && args[0].equalsIgnoreCase("pumpkins")) {
                    instance.getPumpkinPlaceInventory().open(player);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 10F);
                } else if(args.length == 1 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl"))) {
                    instance.saveDefaultConfig();
                    player.sendMessage(settingsHandler.getPrefix() + " §8[§2✔§8] §eSuccessfully loaded §6§lfiles");
                    instance.getSettingsHandler().loadFile();
                    player.sendMessage(settingsHandler.getPrefix() + " §8[§2✔§8] §eSuccessfully reloaded the §6§lconfig");
                    instance.getPumpkinHandler().loadPumpkinFiles();
                    instance.getPumpkinHandler().loadPumpkins();
                    player.sendMessage(settingsHandler.getPrefix() + " §8[§2✔§8] §eSuccessfully reloaded all §6§lpumpkins");
                    player.sendMessage(settingsHandler.getPrefix() + " §ePlugin reload §6§ldone§r§e! If you changed anything");
                    player.sendMessage(settingsHandler.getPrefix() + " §eabout the §6affected worlds §eit's highly recommended");
                    player.sendMessage(settingsHandler.getPrefix() + " §eto do execute a real server reload as well.");
                } else if(args.length == 1 && args[0].equalsIgnoreCase("worlds")) {
                    player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
                    player.sendMessage(settingsHandler.getPrefix() + " §eAll active worlds");
                    if(!settingsHandler.getAffectedWorlds().isEmpty())
                        settingsHandler.getAffectedWorlds().forEach(current -> player.sendMessage(settingsHandler.getPrefix() + " §8» §6§l" + current));
                    else player.sendMessage(settingsHandler.getPrefix() + " §8» §c✖");
                    player.sendMessage(settingsHandler.getPrefix() + " ");
                    player.sendMessage(settingsHandler.getPrefix() + " §eInvalid worlds from config");
                    if(!settingsHandler.getInvalidWorlds().isEmpty())
                        settingsHandler.getInvalidWorlds().forEach(current -> player.sendMessage(settingsHandler.getPrefix() + " §8» §6§l" + current));
                    else player.sendMessage(settingsHandler.getPrefix() + " §8» §c✖");
                    player.sendMessage(settingsHandler.getPrefix() + " ");
                    player.sendMessage(settingsHandler.getPrefix() + " §8» §eYou are in world §6" + player.getWorld().getName());
                    if(settingsHandler.getAffectedWorlds().contains(player.getWorld().getName()))
                        player.sendMessage(settingsHandler.getPrefix() + " §8» §eAffected from Halloween Events§8: §2✔");
                    else player.sendMessage(settingsHandler.getPrefix() + " §8» §eAffected from Halloween Events§8: §c✖");
                    player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
                } else if(args.length == 1 && args[0].equalsIgnoreCase("rmc")) {
                    Halloween.getInstance().getTrickOrTreatEvent().disable();
                    player.sendMessage(Halloween.getInstance().getSettingsHandler().getPrefix() + " §eSuccessfully §6removed §eall TrickOrTreat chests.");
                } else
                    this.sendUsage(player, settingsHandler);
            } else
                player.sendMessage(settingsHandler.getNoPermission());
        } else
            commandSender.sendMessage(settingsHandler.getMustBePlayer());
        return true;
    }

    private void sendUsage(final Player player, final SettingsHandler settingsHandler) {
        player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
        player.sendMessage(settingsHandler.getPrefix() + " §6/halloween pumpkins §7Select pumpkins");
        player.sendMessage(settingsHandler.getPrefix() + " §6/halloween scare <Player> §7Scare players");
        player.sendMessage(settingsHandler.getPrefix() + " §6/halloween events §7Info about events");
        player.sendMessage(settingsHandler.getPrefix() + " §6/halloween startEvent <EventName> §7Start events");
        player.sendMessage(settingsHandler.getPrefix() + " §6/halloween worlds §7See all worlds");
        player.sendMessage(settingsHandler.getPrefix() + " §6/halloween rmc §7Remove TrickOrTreat chests");
        player.sendMessage(settingsHandler.getPrefix() + " §6/halloween reload §7Reload the plugin");
        player.sendMessage(settingsHandler.getPrefix() + " §7");
        player.sendMessage(settingsHandler.getPrefix() + " §eYou are running §6§lScaryHalloween §eby §6§lPXAV§e.");
        player.sendMessage(settingsHandler.getPrefix() + " §eInstalled version §8» §6" + Halloween.getInstance().getDescription().getVersion());
        player.sendMessage(settingsHandler.getPrefix() + " §8§m--------------------------");
    }
}
