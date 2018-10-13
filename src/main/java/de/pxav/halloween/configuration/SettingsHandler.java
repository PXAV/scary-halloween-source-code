package de.pxav.halloween.configuration;

import de.pxav.halloween.Halloween;
import de.pxav.halloween.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This class handles the config file (config.yml). You can
 * access to this class and get every message, etc. with the getter
 * for it.
 *
 * @author pxav.
 * (c) 2018
 */

public class SettingsHandler {

    private File file;
    private YamlConfiguration configuration;

    private String prefix, noPermission, mustBePlayer;
    private String halloweenCommandPermission;
    private String worldNotFound, noValidWorlds, noRealNumber,
                    playerNotOnline;
    private String jumpScarePumpkinDisplayName;
    private String forcedEvent, pleaseWaitMessage;
    private String ghostSpawnedNearWarning, deathZombieName, attackBatName,
                    scaredPlayer, placedPumpkin, cannotBreakBlock, removedPumpkin,
                    breakPumpkinPermission, unknownFailure, noSpaceFailure,
                    featureUnavailable, playerNotInWorld, cannotPlaceHere;
    private final List<String> affectedWorlds = new ArrayList<>();
    private final List<String> invalidWorlds = new ArrayList<>();
    private final List<String> jumpScarePumpkinLore = new ArrayList<>();
    private boolean modifyCreatureSpawn;
    private boolean randomPumpkinJumpScares, batAttackEvent;
    private boolean spawnGhosts, warnPlayerWhenGhostSpawned,
            zombieOnPlayerDeath, scarySoundEvent, pumpkinClickEffect,
            fakeLightningEvent, spawnGhostEvent, airBoostEvent,
            flyingJackEvent, playersCanBreakEventPumpkin, lagTorch;
    private int batAttackSpawnAmount;

    private String scareInventoryTitle;
    private String scareInventoryJumpScare;
    private String scareInventoryPlaySound;
    private String scareInventoryBatAttack;
    private String scareInventoryLightning;
    private String scareInventorySpawnGhost;
    private String scareInventoryScaryFlight;
    private String scareInventoryTrickOrTreatChest;
    private String scareInventoryFlyingJack;
    private String scareInventoryPlayerGrave;
    private String scareInventoryNotAvailable;

    private String pumpkinInventoryTitle;
    private String pumpkinInventoryJumpScare;
    private String pumpkinInventorySmoking;
    private String pumpkinInventoryLightning;
    private String pumpkinInventoryClickable;
    private String pumpkinInventoryGlowing;

    private final List<String> pumpkinInventoryJumpScareLore = new ArrayList<>();
    private final List<String> pumpkinInventorySmokingLore = new ArrayList<>();
    private final List<String> pumpkinInventoryLightningLore = new ArrayList<>();
    private final List<String> pumpkinInventoryClickableLore = new ArrayList<>();
    private final List<String> pumpkinInventoryGlowingLore = new ArrayList<>();

    private final List<Integer> jumpScareDelay = new ArrayList<>();
    private final List<Integer> batAttackDelay = new ArrayList<>();
    private final List<Integer> scarySoundDelay = new ArrayList<>();
    private final List<Integer> fakeLightningDelay = new ArrayList<>();
    private final List<Integer> spawnGhostDelay = new ArrayList<>();
    private final List<Integer> airBoostDelays = new ArrayList<>();
    private final List<Integer> flyingJackDelays = new ArrayList<>();

    private final List<ItemStack> chestItems = new ArrayList<>();
    private int pickAmount;

    private double lightningPumpkinDistance;

    /**
     * Declares the file resource and the configuration.
     * It also executes the loading progress of the messages
     * and settings.
     */
    public void loadFile() {
        this.file = new File("plugins//ScaryHalloween", "config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
        this.loadSettings();
    }

    /**
     * This method loads all setting and message values from the
     * config into the cache to save performance.
     */
    private void loadSettings() {

        // clear all list items to avoid duplicates in lists
        // which can be caused by the /halloween reload command.
        pumpkinInventoryClickableLore.clear();
        pumpkinInventoryGlowingLore.clear();
        pumpkinInventorySmokingLore.clear();
        pumpkinInventoryLightningLore.clear();
        pumpkinInventoryJumpScareLore.clear();

        affectedWorlds.clear();
        invalidWorlds.clear();
        jumpScarePumpkinLore.clear();

        spawnGhostDelay.clear();
        batAttackDelay.clear();
        scarySoundDelay.clear();
        fakeLightningDelay.clear();
        spawnGhostDelay.clear();
        airBoostDelays.clear();
        flyingJackDelays.clear();


        setPrefix(ChatColor.translateAlternateColorCodes('&', configuration.getString("General.Prefix")));

        setRandomPumpkinJumpScares(configuration.getBoolean("Features.RandomPumpkinJumpScares"));
        setModifyCreatureSpawn(configuration.getBoolean("Features.ModifyCreatureSpawning"));
        setSpawnGhosts(configuration.getBoolean("Features.SpawnGhosts"));
        setZombieOnPlayerDeath(configuration.getBoolean("Features.ZombieOnPlayerDeath"));
        setBatAttackEvent(configuration.getBoolean("Features.BatAttackEvent"));
        setScarySoundEvent(configuration.getBoolean("Features.ScarySoundEvent"));
        setPumpkinClickEffect(configuration.getBoolean("Features.PumpkinClickEffect"));
        setFakeLightningEvent(configuration.getBoolean("Features.FakeLightningEvent"));
        setSpawnGhostEvent(configuration.getBoolean("Features.SpawnGhostEvent"));
        setAirBoostEvent(configuration.getBoolean("Features.AirBoostEvent"));
        setFlyingJackEvent(configuration.getBoolean("Features.FlyingJackEvent"));
        setLagTorch(configuration.getBoolean("Features.LagTorchEffect"));

        setBatAttackSpawnAmount(configuration.getInt("Settings.BatAttackSpawnAmount"));

        setPlayersCanBreakEventPumpkin(configuration.getBoolean("Settings.PlayersCanBreakEventPumpkins"));
        setLightningPumpkinDistance(configuration.getDouble("Settings.LightningPumpkinDistance"));

        setWarnPlayerWhenGhostSpawned(configuration.getBoolean("Messages.Scares.WarnPlayerWhenGhostSpawned"));

        setHalloweenCommandPermission(configuration.getString("Permissions.HalloweenCommand"));

        // set inventory display names
        setScareInventoryBatAttack(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.BatAttack").replace("%prefix%", this.getPrefix())));
        setScareInventoryPlaySound(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.PlaySound").replace("%prefix%", this.getPrefix())));
        setScareInventoryJumpScare(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.JumpScare").replace("%prefix%", this.getPrefix())));
        setScareInventoryTitle(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.Title").replace("%prefix%", this.getPrefix())));
        setScareInventoryLightning(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.Lightning").replace("%prefix%", this.getPrefix())));
        setScareInventorySpawnGhost(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.SpawnGhost").replace("%prefix%", this.getPrefix())));
        setScareInventoryTrickOrTreatChest(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.TrickOrTreatChest").replace("%prefix%", this.getPrefix())));
        setScareInventoryFlyingJack(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.FlyingJack").replace("%prefix%", this.getPrefix())));
        setScareInventoryPlayerGrave(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.PlayerGrave").replace("%prefix%", this.getPrefix())));
        setScareInventoryScaryFlight(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.ScaryFlight").replace("%prefix%", this.getPrefix())));
        setScareInventoryNotAvailable(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.ScareInventory.NotAvailable").replace("%prefix%", this.getPrefix())));
        setPlayerNotOnline(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.PlayerNotOnline").replace("%prefix%", this.getPrefix())));
        setPlacedPumpkin(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Info.PlacedPumpkin").replace("%prefix%", this.getPrefix())));
        setCannotBreakBlock(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.CannotBreakBlock").replace("%prefix%", this.getPrefix())));
        setRemovedPumpkin(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Info.BrokePumpkin").replace("%prefix%", this.getPrefix())));
        setBreakPumpkinPermission(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Permissions.BreakPumpkins").replace("%prefix%", this.getPrefix())));

        setNoPermission(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.NoPermission")).replace("%prefix%", this.getPrefix()));
        setMustBePlayer(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.NoPlayer")).replace("%prefix%", this.getPrefix()));
        setWorldNotFound(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.WorldNotFound").replace("%prefix%", this.getPrefix())));
        setNoValidWorlds(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.NoValidWorlds").replace("%prefix%", this.getPrefix())));
        setNoRealNumber(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.NoRealNumber").replace("%prefix%", this.getPrefix())));
        setJumpScarePumpkinDisplayName(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.JumpScarePumpkinName")).replace("%prefix%", this.getPrefix()));
        setDeathZombieName(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.DeathZombieName").replace("%prefix%", this.getPrefix())));
        setGhostSpawnedNearWarning(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Scares.WarnGhostMessage").replace("%prefix%", this.getPrefix())));
        setAttackBatName(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.AttackBatName").replace("%prefix%", this.getPrefix())));
        setForcedEvent(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Scares.ForcedEvent").replace("%prefix%", this.getPrefix())));
        setScaredPlayer(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Scares.ScaredPlayer").replace("%prefix%", this.getPrefix())));
        setPleaseWaitMessage(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.PleaseWait").replace("%prefix%", this.getPrefix())));
        setUnknownFailure(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.UnknownLaunchFailure").replace("%prefix%", this.getPrefix())));
        setNoSpaceFailure(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.NoSpaceLaunchFailure").replace("%prefix%", this.getPrefix())));
        setNoSpaceFailure(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.FeatureUnavailable").replace("%prefix%", this.getPrefix())));
        setPlayerNotInWorld(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.PlayerNotInWorld").replace("%prefix%", this.getPrefix())));
        setCannotPlaceHere(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Messages.Errors.CannotPlaceHere").replace("%prefix%", this.getPrefix())));

        setPumpkinInventoryTitle(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.PumpkinInventory.Title").replace("%prefix%", this.getPrefix())));
        setPumpkinInventoryClickable(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.PumpkinInventory.ClickablePumpkin").replace("%prefix%", this.getPrefix())));
        setPumpkinInventoryGlowing(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.PumpkinInventory.GlowingPumpkin").replace("%prefix%", this.getPrefix())));
        setPumpkinInventoryLightning(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.PumpkinInventory.LightningPumpkin").replace("%prefix%", this.getPrefix())));
        setPumpkinInventorySmoking(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.PumpkinInventory.SmokingPumpkin").replace("%prefix%", this.getPrefix())));
        setPumpkinInventoryJumpScare(ChatColor.translateAlternateColorCodes('&',
                configuration.getString("Displays.PumpkinInventory.JumpScarePumpkin").replace("%prefix%", this.getPrefix())));

        // set chest items
        setPickAmount(configuration.getInt("TrickOrTreat.PickAmount"));
        final List<String> rawChestItemFormat = configuration.getStringList("TrickOrTreat.Items");
        rawChestItemFormat.forEach(current -> {
            final String[] dataArray = current.split(":");
            chestItems.add(
                new ItemBuilder(
                        Material.valueOf(dataArray[0]),
                        Short.parseShort(dataArray[1]))
                        .setAmount(Halloween.getInstance().getMathUtils().getRandom(Integer.valueOf(dataArray[2]),
                                Integer.valueOf(dataArray[3]))).build()
            );
            System.out.println("new ItemStack: mat.: " + dataArray[0] + "sub.: " + dataArray[1] + " min.: " + dataArray[2]+ " max.: " + dataArray[3]);
        });

        System.out.println("pick amount: " + pickAmount);

        if(chestItems.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(this.getPrefix() + "§cWARNING: Misconfiguration found in \"§7TrickOrTreat.Items\"§c!");
            Bukkit.getConsoleSender().sendMessage(this.getPrefix() + "§cWARNING: The list is empty and no items will spawn in chests. Please add");
            Bukkit.getConsoleSender().sendMessage(this.getPrefix() + "§cWARNING: at least one item (more are recommended) to make the plugin work.");
        }

        if(pickAmount > 27) {
            Bukkit.getConsoleSender().sendMessage(this.getPrefix() + "§cWARNING: Misconfiguration found in \"§7TrickOrTreat.PickAmount\"§c!");
            Bukkit.getConsoleSender().sendMessage(this.getPrefix() + "§cWARNING: The given number of §7" + this.getPickAmount() + " §cis too big, because the chest");
            Bukkit.getConsoleSender().sendMessage(this.getPrefix() + "§cWARNING: can only hold §727 §citems. Please lower this amount before using the plugin.");
        }

        final List<String> rawClickablePumpkinLore = configuration.getStringList("Displays.PumpkinInventory.Lore.ClickablePumpkin");
        rawClickablePumpkinLore.forEach(current
                -> pumpkinInventoryClickableLore
                .add(ChatColor.translateAlternateColorCodes('&', current).replace("%prefix%", this.getPrefix())));

        final List<String> rawGlowingPumpkinLore = configuration.getStringList("Displays.PumpkinInventory.Lore.GlowingPumpkin");
        rawGlowingPumpkinLore.forEach(current
                -> pumpkinInventoryGlowingLore
                .add(ChatColor.translateAlternateColorCodes('&', current).replace("%prefix%", this.getPrefix())));

        final List<String> rawJumpScarePumpkinSelectionLore = configuration.getStringList("Displays.PumpkinInventory.Lore.JumpScare");
        rawJumpScarePumpkinSelectionLore.forEach(current
                -> pumpkinInventoryJumpScareLore
                .add(ChatColor.translateAlternateColorCodes('&', current).replace("%prefix%", this.getPrefix())));

        final List<String> rawSmokingPumpkinLore = configuration.getStringList("Displays.PumpkinInventory.Lore.SmokingPumpkin");
        rawSmokingPumpkinLore.forEach(current
                -> pumpkinInventorySmokingLore
                .add(ChatColor.translateAlternateColorCodes('&', current).replace("%prefix%", this.getPrefix())));

        final List<String> rawLightningPumpkinLore = configuration.getStringList("Displays.PumpkinInventory.Lore.LightningPumpkin");
        rawLightningPumpkinLore.forEach(current
                -> pumpkinInventoryLightningLore
                .add(ChatColor.translateAlternateColorCodes('&', current).replace("%prefix%", this.getPrefix())));

        // set item lore(s)
        final List<String> rawJumpScarePumpkinLore = configuration.getStringList("Displays.JumpScarePumpkinLore");
        rawJumpScarePumpkinLore.forEach(current
                -> jumpScarePumpkinLore.add(ChatColor.translateAlternateColorCodes('&', current).replace("%prefix%", this.getPrefix())));

        // Saves the affected worlds from the config.
        final List<String> configWorlds = configuration.getStringList("General.AffectedWorlds");

        // iterates all entries from the config and checks
        // if the given world also exists on the server.
        configWorlds.forEach(current -> {
            boolean worldFound = false;
            for (final World currentWorld : Bukkit.getWorlds()) {
                if(currentWorld.getName().equalsIgnoreCase(current)) {
                    affectedWorlds.add(currentWorld.getName());
                    worldFound = true;
                }
            }
            if(!worldFound) {
                this.invalidWorlds.add(current);
                Bukkit.getConsoleSender().sendMessage(this.getWorldNotFound(current));
            }
        });

        // If the plugin does not have any worlds to work with it will
        // send this to the console.
        if(affectedWorlds.isEmpty())
            Bukkit.getConsoleSender().sendMessage(this.getNoValidWorlds());

        // in the next step we need to load the delay of the events.
        // first comes the jump scare event.

        final List<String> rawJumpScareDelay = configuration.getStringList("EventDelays.JumpScareDelays");
        rawJumpScareDelay.forEach(current -> {
            try {
                final Integer i = Integer.parseInt(current);
                jumpScareDelay.add(i);
            } catch (final NumberFormatException e) {
                Bukkit.getConsoleSender().sendMessage(this.getNoRealNumber(current));
            }
        });

        final List<String> rawBatAttackDelay = configuration.getStringList("EventDelays.BatAttackDelays");
        rawBatAttackDelay.forEach(current -> {
            try {
                final Integer i = Integer.parseInt(current);
                batAttackDelay.add(i);
            } catch (final NumberFormatException e) {
                Bukkit.getConsoleSender().sendMessage(this.getNoRealNumber(current));
            }
        });

        final List<String> rawScarySoundDelay = configuration.getStringList("EventDelays.ScarySoundDelays");
        rawScarySoundDelay.forEach(current -> {
            try {
                final Integer i = Integer.parseInt(current);
                scarySoundDelay.add(i);
            } catch (final NumberFormatException e) {
                Bukkit.getConsoleSender().sendMessage(this.getNoRealNumber(current));
            }
        });

        final List<String> rawFakeLightningDelay = configuration.getStringList("EventDelays.FakeLightningDelays");
        rawFakeLightningDelay.forEach(current -> {
            try {
                final Integer i = Integer.parseInt(current);
                fakeLightningDelay.add(i);
            } catch (final NumberFormatException e) {
                Bukkit.getConsoleSender().sendMessage(this.getNoRealNumber(current));
            }
        });

        final List<String> rawSpawnGhostDelay = configuration.getStringList("EventDelays.SpawnGhostDelays");
        rawSpawnGhostDelay.forEach(current -> {
            try {
                final Integer i = Integer.parseInt(current);
                spawnGhostDelay.add(i);
            } catch (final NumberFormatException e) {
                Bukkit.getConsoleSender().sendMessage(this.getNoRealNumber(current));
            }
        });

        final List<String> rawAirBoostDelay = configuration.getStringList("EventDelays.AirBoostDelays");
        rawAirBoostDelay.forEach(current -> {
            try {
                final Integer i = Integer.parseInt(current);
                airBoostDelays.add(i);
            } catch (final NumberFormatException e) {
                Bukkit.getConsoleSender().sendMessage(this.getNoRealNumber(current));
            }
        });

        final List<String> rawFlyingJackDelay = configuration.getStringList("EventDelays.FlyingJackDelays");
        rawFlyingJackDelay.forEach(current -> {
            try {
                final Integer i = Integer.parseInt(current);
                flyingJackDelays.add(i);
            } catch (final NumberFormatException e) {
                Bukkit.getConsoleSender().sendMessage(this.getNoRealNumber(current));
            }
        });

    }


    public String getHalloweenCommandPermission() {
        return halloweenCommandPermission;
    }

    private void setHalloweenCommandPermission(final String halloweenCommandPermission) {
        this.halloweenCommandPermission = halloweenCommandPermission;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public void setNoPermission(String noPermission) {
        this.noPermission = noPermission;
    }

    public String getMustBePlayer() {
        return mustBePlayer;
    }

    public void setMustBePlayer(String mustBePlayer) {
        this.mustBePlayer = mustBePlayer;
    }

    public boolean isRandomPumpkinJumpScares() {
        return randomPumpkinJumpScares;
    }

    public void setRandomPumpkinJumpScares(boolean randomPumpkinJumpScares) {
        this.randomPumpkinJumpScares = randomPumpkinJumpScares;
    }

    public boolean isModifyCreatureSpawn() {
        return modifyCreatureSpawn;
    }

    public void setModifyCreatureSpawn(boolean modifyCreatureSpawn) {
        this.modifyCreatureSpawn = modifyCreatureSpawn;
    }

    public List<String> getAffectedWorlds() {
        return affectedWorlds;
    }

    public String getNoValidWorlds() {
        return noValidWorlds;
    }

    public void setNoValidWorlds(String noValidWorlds) {
        this.noValidWorlds = noValidWorlds;
    }

    public String getWorldNotFound(final String world) {
        return worldNotFound.replace("%world%", world);
    }

    public void setWorldNotFound(String worldNotFound) {
        this.worldNotFound = worldNotFound;
    }

    public List<Integer> getJumpScareDelay() {
        return jumpScareDelay;
    }

    public String getNoRealNumber(final String input) {
        return noRealNumber.replace("%input%", input);
    }

    public void setNoRealNumber(String noRealNumber) {
        this.noRealNumber = noRealNumber;
    }

    public String getJumpScarePumpkinDisplayName() {
        return jumpScarePumpkinDisplayName;
    }

    public void setJumpScarePumpkinDisplayName(String jumpScarePumpkinDisplayName) {
        this.jumpScarePumpkinDisplayName = jumpScarePumpkinDisplayName;
    }

    public List<String> getJumpScarePumpkinLore() {
        return jumpScarePumpkinLore;
    }

    public boolean isSpawnGhosts() {
        return spawnGhosts;
    }

    public void setSpawnGhosts(boolean spawnGhosts) {
        this.spawnGhosts = spawnGhosts;
    }

    public String getGhostSpawnedNearWarning() {
        return ghostSpawnedNearWarning;
    }

    public void setGhostSpawnedNearWarning(String ghostSpawnedNearWarning) {
        this.ghostSpawnedNearWarning = ghostSpawnedNearWarning;
    }

    public boolean isWarnPlayerWhenGhostSpawned() {
        return warnPlayerWhenGhostSpawned;
    }

    public void setWarnPlayerWhenGhostSpawned(boolean warnPlayerWhenGhostSpawned) {
        this.warnPlayerWhenGhostSpawned = warnPlayerWhenGhostSpawned;
    }

    public boolean isZombieOnPlayerDeath() {
        return zombieOnPlayerDeath;
    }

    public void setZombieOnPlayerDeath(boolean zombieOnPlayerDeath) {
        this.zombieOnPlayerDeath = zombieOnPlayerDeath;
    }

    public String getDeathZombieName(final String name) {
        return deathZombieName.replace("%player%", name);
    }

    public void setDeathZombieName(String deathZombieName) {
        this.deathZombieName = deathZombieName;
    }

    public boolean isBatAttackEvent() {
        return batAttackEvent;
    }

    public void setBatAttackEvent(boolean batAttackEvent) {
        this.batAttackEvent = batAttackEvent;
    }

    public String isAttackBatName() {
        return attackBatName;
    }

    public void setAttackBatName(String attackBatName) {
        this.attackBatName = attackBatName;
    }

    public String getAttackBatName() {
        return attackBatName;
    }

    public List<Integer> getBatAttackDelay() {
        return batAttackDelay;
    }

    public String getForcedEvent(final Integer seconds) {
        return forcedEvent.replace("%time%", seconds.toString());
    }

    public void setForcedEvent(String forcedEvent) {
        this.forcedEvent = forcedEvent;
    }

    public boolean isScarySoundEvent() {
        return scarySoundEvent;
    }

    public void setScarySoundEvent(boolean scarySoundEvent) {
        this.scarySoundEvent = scarySoundEvent;
    }

    public List<Integer> getScarySoundDelay() {
        return scarySoundDelay;
    }

    public boolean isPumpkinClickEffect() {
        return pumpkinClickEffect;
    }

    public void setPumpkinClickEffect(boolean pumpkinClickEffect) {
        this.pumpkinClickEffect = pumpkinClickEffect;
    }

    public String getScareInventoryTitle() {
        return scareInventoryTitle;
    }

    public void setScareInventoryTitle(String scareInventoryTitle) {
        this.scareInventoryTitle = scareInventoryTitle;
    }

    public String getScareInventoryJumpScare() {
        return scareInventoryJumpScare;
    }

    public void setScareInventoryJumpScare(String scareInventoryJumpScare) {
        this.scareInventoryJumpScare = scareInventoryJumpScare;
    }

    public String getScareInventoryPlaySound() {
        return scareInventoryPlaySound;
    }

    public void setScareInventoryPlaySound(String scareInventoryPlaySound) {
        this.scareInventoryPlaySound = scareInventoryPlaySound;
    }

    public String getScareInventoryBatAttack() {
        return scareInventoryBatAttack;
    }

    public void setScareInventoryBatAttack(String scareInventoryBatAttack) {
        this.scareInventoryBatAttack = scareInventoryBatAttack;
    }

    public String getScareInventoryLightning() {
        return scareInventoryLightning;
    }

    public void setScareInventoryLightning(String scareInventoryLightning) {
        this.scareInventoryLightning = scareInventoryLightning;
    }

    public String getScareInventorySpawnGhost() {
        return scareInventorySpawnGhost;
    }

    public void setScareInventorySpawnGhost(String scareInventorySpawnGhost) {
        this.scareInventorySpawnGhost = scareInventorySpawnGhost;
    }

    public String getScareInventoryScaryFlight() {
        return scareInventoryScaryFlight;
    }

    public void setScareInventoryScaryFlight(String scareInventoryScaryFlight) {
        this.scareInventoryScaryFlight = scareInventoryScaryFlight;
    }

    public String getScareInventoryTrickOrTreatChest() {
        return scareInventoryTrickOrTreatChest;
    }

    public void setScareInventoryTrickOrTreatChest(String scareInventoryTrickOrTreatChest) {
        this.scareInventoryTrickOrTreatChest = scareInventoryTrickOrTreatChest;
    }

    public String getScareInventoryFlyingJack() {
        return scareInventoryFlyingJack;
    }

    public void setScareInventoryFlyingJack(String scareInventoryFlyingJack) {
        this.scareInventoryFlyingJack = scareInventoryFlyingJack;
    }

    public String getScareInventoryPlayerGrave() {
        return scareInventoryPlayerGrave;
    }

    public void setScareInventoryPlayerGrave(String scareInventoryPlayerGrave) {
        this.scareInventoryPlayerGrave = scareInventoryPlayerGrave;
    }

    public String getScareInventoryNotAvailable() {
        return scareInventoryNotAvailable;
    }

    public void setScareInventoryNotAvailable(String scareInventoryNotAvailable) {
        this.scareInventoryNotAvailable = scareInventoryNotAvailable;
    }

    public String getPlayerNotOnline() {
        return playerNotOnline;
    }

    public void setPlayerNotOnline(String playerNotOnline) {
        this.playerNotOnline = playerNotOnline;
    }

    public String getScaredPlayer(final String name) {
        return scaredPlayer.replace("%player%", name);
    }

    public void setScaredPlayer(String scaredPlayer) {
        this.scaredPlayer = scaredPlayer;
    }

    public boolean isFakeLightningEvent() {
        return fakeLightningEvent;
    }

    public void setFakeLightningEvent(boolean fakeLightningEvent) {
        this.fakeLightningEvent = fakeLightningEvent;
    }

    public List<Integer> getFakeLightningDelay() {
        return fakeLightningDelay;
    }

    public boolean isSpawnGhostEvent() {
        return spawnGhostEvent;
    }

    public void setSpawnGhostEvent(boolean spawnGhostEvent) {
        this.spawnGhostEvent = spawnGhostEvent;
    }

    public List<Integer> getAirBoostDelays() {
        return airBoostDelays;
    }

    public boolean isAirBoostEvent() {
        return airBoostEvent;
    }

    public void setAirBoostEvent(boolean airBoostEvent) {
        this.airBoostEvent = airBoostEvent;
    }

    public List<Integer> getSpawnGhostDelay() {
        return spawnGhostDelay;
    }

    public String getPleaseWaitMessage() {
        return pleaseWaitMessage;
    }

    public void setPleaseWaitMessage(String pleaseWaitMessage) {
        this.pleaseWaitMessage = pleaseWaitMessage;
    }

    public boolean isFlyingJackEvent() {
        return flyingJackEvent;
    }

    public void setFlyingJackEvent(boolean flyingJackEvent) {
        this.flyingJackEvent = flyingJackEvent;
    }

    public List<Integer> getFlyingJackDelays() {
        return flyingJackDelays;
    }

    public String getPumpkinInventoryTitle() {
        return pumpkinInventoryTitle;
    }

    public void setPumpkinInventoryTitle(String pumpkinInventoryTitle) {
        this.pumpkinInventoryTitle = pumpkinInventoryTitle;
    }

    public String getPumpkinInventoryJumpScare() {
        return pumpkinInventoryJumpScare;
    }

    public void setPumpkinInventoryJumpScare(String pumpkinInventoryJumpScare) {
        this.pumpkinInventoryJumpScare = pumpkinInventoryJumpScare;
    }

    public String getPumpkinInventorySmoking() {
        return pumpkinInventorySmoking;
    }

    public void setPumpkinInventorySmoking(String pumpkinInventorySmoking) {
        this.pumpkinInventorySmoking = pumpkinInventorySmoking;
    }

    public String getPumpkinInventoryLightning() {
        return pumpkinInventoryLightning;
    }

    public void setPumpkinInventoryLightning(String pumpkinInventoryLightning) {
        this.pumpkinInventoryLightning = pumpkinInventoryLightning;
    }

    public String getPumpkinInventoryClickable() {
        return pumpkinInventoryClickable;
    }

    public void setPumpkinInventoryClickable(String pumpkinInventoryClickable) {
        this.pumpkinInventoryClickable = pumpkinInventoryClickable;
    }

    public String getPumpkinInventoryGlowing() {
        return pumpkinInventoryGlowing;
    }

    public void setPumpkinInventoryGlowing(String pumpkinInventoryGlowing) {
        this.pumpkinInventoryGlowing = pumpkinInventoryGlowing;
    }

    public List<String> getPumpkinInventoryJumpScareLore() {
        return pumpkinInventoryJumpScareLore;
    }

    public List<String> getPumpkinInventorySmokingLore() {
        return pumpkinInventorySmokingLore;
    }

    public List<String> getPumpkinInventoryLightningLore() {
        return pumpkinInventoryLightningLore;
    }

    public List<String> getPumpkinInventoryClickableLore() {
        return pumpkinInventoryClickableLore;
    }

    public List<String> getPumpkinInventoryGlowingLore() {
        return pumpkinInventoryGlowingLore;
    }

    public String getPlacedPumpkin(final String type) {
        return placedPumpkin.replace("%type%", type);
    }

    public void setPlacedPumpkin(String placedPumpkin) {
        this.placedPumpkin = placedPumpkin;
    }

    public boolean isPlayersCanBreakEventPumpkin() {
        return playersCanBreakEventPumpkin;
    }

    public void setPlayersCanBreakEventPumpkin(boolean playersCanBreakEventPumpkin) {
        this.playersCanBreakEventPumpkin = playersCanBreakEventPumpkin;
    }

    public String getCannotBreakBlock() {
        return cannotBreakBlock;
    }

    public void setCannotBreakBlock(String cannotBreakBlock) {
        this.cannotBreakBlock = cannotBreakBlock;
    }

    public String getRemovedPumpkin(final String type) {
        return removedPumpkin.replace("%type%", type);
    }

    public void setRemovedPumpkin(String removedPumpkin) {
        this.removedPumpkin = removedPumpkin;
    }

    public String getBreakPumpkinPermission() {
        return breakPumpkinPermission;
    }

    public void setBreakPumpkinPermission(String breakPumpkinPermission) {
        this.breakPumpkinPermission = breakPumpkinPermission;
    }

    public double getLightningPumpkinDistance() {
        return lightningPumpkinDistance;
    }

    public void setLightningPumpkinDistance(double lightningPumpkinDistance) {
        this.lightningPumpkinDistance = lightningPumpkinDistance;
    }

    public boolean isLagTorch() {
        return lagTorch;
    }

    public void setLagTorch(boolean lagTorch) {
        this.lagTorch = lagTorch;
    }

    public List<ItemStack> getChestItems() {
        return chestItems;
    }

    public int getPickAmount() {
        return pickAmount;
    }

    public void setPickAmount(int pickAmount) {
        this.pickAmount = pickAmount;
    }

    public String getUnknownFailure() {
        return unknownFailure;
    }

    public void setUnknownFailure(String unknownFailure) {
        this.unknownFailure = unknownFailure;
    }

    public String getNoSpaceFailure() {
        return noSpaceFailure;
    }

    public void setNoSpaceFailure(String noSpaceFailure) {
        this.noSpaceFailure = noSpaceFailure;
    }

    public int getBatAttackSpawnAmount() {
        return batAttackSpawnAmount;
    }

    public void setBatAttackSpawnAmount(int batAttackSpawnAmount) {
        this.batAttackSpawnAmount = batAttackSpawnAmount;
    }

    public String getFeatureUnavailable() {
        return featureUnavailable;
    }

    public void setFeatureUnavailable(String featureUnavailable) {
        this.featureUnavailable = featureUnavailable;
    }

    public String getPlayerNotInWorld() {
        return playerNotInWorld;
    }

    public void setPlayerNotInWorld(String playerNotInWorld) {
        this.playerNotInWorld = playerNotInWorld;
    }

    public String getCannotPlaceHere() {
        return cannotPlaceHere;
    }

    public void setCannotPlaceHere(String cannotPlaceHere) {
        this.cannotPlaceHere = cannotPlaceHere;
    }

    public List<String> getInvalidWorlds() {
        return invalidWorlds;
    }
}
