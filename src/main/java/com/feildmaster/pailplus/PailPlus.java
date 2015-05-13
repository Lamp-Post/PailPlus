package com.feildmaster.pailplus;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.feildmaster.pailplus.monitor.Util;
import com.feildmaster.pailplus.pail.MainWindow;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import javax.swing.JFrame;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(name="PailPlus", id="pailplus", version="1.0.0")
public class PailPlus {
	
    protected static PailPlus instance;

    @Inject
    protected Game game;

    @Inject
    protected Logger logger;
    
    private MainWindow ai;
    private Settings config;
    public Settings settings;
    
    public static PailPlus getInstance() {
        return Preconditions.checkNotNull(instance);
    }
    
    public static Logger getLogger() {
        return getInstance().logger;
    }

    public static Game getGame() {
        return getInstance().game;
    }
    
    public static Server getServer() {
    	return getGame().getServer();
    }

    // Order means everything.
    @Subscribe
    public void onInitialization(InitializationEvent event) {
        instance = this;
        
        if (Util.getPail() == null) {
            Util.warning("Requires Pail.");
            return;
        }

        // Hook into Pail!
        Util.getPail().loadInterfaceComponent("Advanced", ai = new MainWindow());
        Util.getTray().addIcon();
        if (Util.getTray().isActive()) {
            Util.getPail().getMainWindow().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Util.getPail().getMainWindow().removeWindowListener(Util.getPail().getWindowListener());
        }
        
        Util.setDataFolder(new File("PailPlus"));
        if (!Util.getDataFolder().exists()) {
            Util.getDataFolder().mkdirs();
        }

        //settings = new Settings();
        if (!Settings.fileExists()) {
            Settings.load();
        }

        // Register Events
        //new JoinListener(this);

        // Preload Info
        //Util.addPermissionNodes();
        //Util.preloadUsers();

        // Schedule Tasks
        getGame().getAsyncScheduler().runRepeatingTaskAfter(this, ai.monitor, TimeUnit.MILLISECONDS, 10L, 20L); // CPU monitor
    }
    
    @Subscribe
    public void onShutdown(ServerStoppingEvent event) {
        //Util.save();
        Util.getTray().removeIcon();
    }

    public Settings getConfig() {
        if (config == null) {
            config = new Settings();
        }
        return config;
    }

    public MainWindow getMainWindow() {
        return ai;
    }
}
