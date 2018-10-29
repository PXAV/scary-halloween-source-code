package de.pxav.halloween;

import de.pxav.halloween.commands.HalloweenCommand;
import de.pxav.halloween.configuration.SettingsHandler;
import de.pxav.halloween.events.*;
import de.pxav.halloween.events.manual.TrickOrTreatEvent;
import de.pxav.halloween.items.PumpkinPlaceInventory;
import de.pxav.halloween.items.ScarePlayerInventory;
import de.pxav.halloween.listener.*;
import de.pxav.halloween.pumpkins.PumpkinDistanceScheduler;
import de.pxav.halloween.pumpkins.PumpkinHandler;
import de.pxav.halloween.pumpkins.PumpkinLauncherScheduler;
import de.pxav.halloween.utils.MathUtils;
import de.pxav.halloween.utils.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The project SpigotMC.org - HalloweenEventPlugin is updated and developed by pxav.
 *
 * This is the main class of the plugin. You can find
 * the #onEnable() method here, where all listeners and commands
 * will be registered and all files will be loaded.
 *
 * @author pxav.
 * (c) 2018
 */

public class Halloween extends JavaPlugin {

    // Plugin instance
    private static Halloween instance;

    // Other class instances
    private final SettingsHandler settingsHandler = new SettingsHandler();
    private final MathUtils mathUtils = new MathUtils();
    private final ScarePlayerInventory scarePlayerInventory = new ScarePlayerInventory();
    private final PlayerHandler playerHandler = new PlayerHandler();
    private final JumpScareEvent jumpScareEvent = new JumpScareEvent();
    private final BatAttackEvent batAttackEvent = new BatAttackEvent();
    private final ScarySoundEvent scarySoundEvent = new ScarySoundEvent();
    private final FakeLightningEvent fakeLightningEvent = new FakeLightningEvent();
    private final SpawnGhostEvent spawnGhostEvent = new SpawnGhostEvent();
    private final AirBoostEvent airBoostEvent = new AirBoostEvent();
    private final FlyingJackEvent flyingJackEvent = new FlyingJackEvent();
    private final PumpkinPlaceInventory pumpkinPlaceInventory = new PumpkinPlaceInventory();
    private final PumpkinHandler pumpkinHandler = new PumpkinHandler();
    private final PumpkinDistanceScheduler pumpkinDistanceScheduler = new PumpkinDistanceScheduler();
    private final PumpkinLauncherScheduler pumpkinLauncherScheduler = new PumpkinLauncherScheduler();
    private final TrickOrTreatEvent trickOrTreatEvent = new TrickOrTreatEvent();
    private final LocationString locationString = new LocationString();

    /**
     * This method is executed by the server when the server
     * is starting up the plugin. Put all methods which need to
     * be executed on enable here.
     */
    @Override
    public void onEnable() {
        // initialize the plugin instance.
        setInstance(this);

        // register all listeners and commands of the plugin
        this.registerEvents();
        this.registerCommands();

        // create and load all files
        this.saveDefaultConfig();
        this.getSettingsHandler().loadFile();

        this.getPumpkinHandler().loadPumpkinFiles();
        this.getPumpkinHandler().loadPumpkins();

        // schedulers
        this.getPumpkinDistanceScheduler().startScheduler();
        this.getPumpkinLauncherScheduler().startScheduler();


        // start events if they are activated

        if(this.getSettingsHandler().isRandomPumpkinJumpScares()) {
            this.getJumpScareEvent().prepare();
            this.getJumpScareEvent().startScheduler();
        }

        if(this.getSettingsHandler().isBatAttackEvent()) {
            this.getBatAttackEvent().prepare();
            this.getBatAttackEvent().startScheduler();
        }

        if(this.getSettingsHandler().isScarySoundEvent()) {
            this.getScarySoundEvent().prepare();
            this.getScarySoundEvent().startScheduler();
        }

        if(this.getSettingsHandler().isFakeLightningEvent()) {
            this.getFakeLightningEvent().prepare();
            this.getFakeLightningEvent().startScheduler();
        }

        if(this.getSettingsHandler().isSpawnGhostEvent()) {
            this.getSpawnGhostEvent().prepare();
            this.getSpawnGhostEvent().startScheduler();
        }

        if(this.getSettingsHandler().isAirBoostEvent()) {
            this.getAirBoostEvent().prepare();
            this.getAirBoostEvent().startScheduler();
        }

        if(this.getSettingsHandler().isFlyingJackEvent()) {
            this.getFlyingJackEvent().prepare();
            this.getFlyingJackEvent().startScheduler();
        }

        // prepare inventories
        this.getScarePlayerInventory().prepare();
        this.getPumpkinPlaceInventory().prepare();

    }

    /**
     * This method is executed by the server when the server
     * is unloading the plugin to prevent data loss.
     * Put all methods here, which disable the plugin's
     * features.
     */
    @Override
    public void onDisable() {
        setInstance(null);

        // disable events
        this.getTrickOrTreatEvent().disable();

        // stop event schedulers
        this.getJumpScareEvent().stopScheduler();
        this.getBatAttackEvent().stopScheduler();
        this.getScarySoundEvent().stopScheduler();
        this.getAirBoostEvent().stopScheduler();
        this.getSpawnGhostEvent().stopScheduler();
        this.getFakeLightningEvent().stopScheduler();
        this.getPumpkinDistanceScheduler().stopScheduler();
        this.getPumpkinLauncherScheduler().stopScheduler();

    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CreatureSpawnListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new EntityDeathListener(), this);
        pluginManager.registerEvents(new PlayerEntityInteractListener(), this);
        pluginManager.registerEvents(new PlayerCommandPreProcessListener(), this);
    }

    private void registerCommands() {
        new HalloweenCommand("halloween");
    }

    public static Halloween getInstance() {
        return instance;
    }

    private static void setInstance(final Halloween newInstance) {
         instance = newInstance;
    }

    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public MathUtils getMathUtils() {
        return mathUtils;
    }

    public JumpScareEvent getJumpScareEvent() {
        return jumpScareEvent;
    }

    public BatAttackEvent getBatAttackEvent() {
        return batAttackEvent;
    }

    public ScarySoundEvent getScarySoundEvent() {
        return scarySoundEvent;
    }

    public ScarePlayerInventory getScarePlayerInventory() {
        return scarePlayerInventory;
    }

    public FakeLightningEvent getFakeLightningEvent() {
        return fakeLightningEvent;
    }

    public SpawnGhostEvent getSpawnGhostEvent() {
        return spawnGhostEvent;
    }

    public AirBoostEvent getAirBoostEvent() {
        return airBoostEvent;
    }

    public FlyingJackEvent getFlyingJackEvent() {
        return flyingJackEvent;
    }

    public PumpkinPlaceInventory getPumpkinPlaceInventory() {
        return pumpkinPlaceInventory;
    }

    public PumpkinHandler getPumpkinHandler() {
        return pumpkinHandler;
    }

    public PumpkinDistanceScheduler getPumpkinDistanceScheduler() {
        return pumpkinDistanceScheduler;
    }

    public PumpkinLauncherScheduler getPumpkinLauncherScheduler() {
        return pumpkinLauncherScheduler;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public TrickOrTreatEvent getTrickOrTreatEvent() {
        return trickOrTreatEvent;
    }

    public LocationString getLocationString() {
        return locationString;
    }
}
