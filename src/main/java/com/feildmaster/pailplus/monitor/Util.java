package com.feildmaster.pailplus.monitor;

import com.feildmaster.pailplus.PailPlus;
import com.feildmaster.pailplus.pail.MainWindow;
import com.feildmaster.pailplus.pail.PailTray;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.World;

import me.escapeNT.pail.Pail;

public class Util {
    private static final Game game = PailPlus.getGame();
    private static final Server server = PailPlus.getServer();
    private static final PailTray tray = new PailTray();
    private static final PluginManager pm = PailPlus.getGame().getPluginManager();
    private static Map<String, Boolean> pluginStatus = new HashMap<String, Boolean>();
    private static CommandSource source;
    private static Pail pail;
    private static PailPlus pailPlus;
	private static File dataFolder;

    public static Game getGame() {
        return game;
    }
    
    public static Server getServer() {
        return server;
    }

    // Log Functions
    public static void info(String msg) {
        log(Level.INFO, msg);
    }

    public static void warning(String msg) {
        log(Level.WARNING, msg);
    }

    private static void log(Level level, String msg) {
        getPailPlus().getLogger().log(level, msg);
    }

    public static Pail getPail() {
        if (pail == null) {
            pail = (Pail) getGame().getPluginManager().getPlugin("pail").get().getInstance();
        }
        return pail;
    }

    public static PailPlus getPailPlus() {
        if (pailPlus == null) {
            pailPlus = (PailPlus) getGame().getPluginManager().getPlugin("pailplus").get().getInstance();
        }
        return pailPlus;
    }

    public static MainWindow getAI() {
        return getPailPlus().getMainWindow();
    }

    public static Collection<Player> getPlayers() {
        return getServer().getOnlinePlayers();
    }

/*    public static Map<String, Boolean> getPluginStatus() {
        if (pluginStatus.isEmpty()) {
            updatePluginStatus();
        }
        return pluginStatus;
    }

    public static Map<String, Boolean> updatePluginStatus() {
        for (PluginContainer p : getGame().getPluginManager().getPlugins()) {
            pluginStatus.put(p.getName(), p.isEnabled());
        }
        return pluginStatus;
    }

    public static Collection<World> getWorlds() {
        return getServer().getWorlds();
    }

    public static void setPlayerPermission(String player, String world, String perm, Boolean value) {
        getPailPlus().getPermsConfig().setPermission(player, world, perm, value); // Set config
        Player p = getServer().getPlayer(player);
        if (p != null) {
            loadPermissions(p);
        } else {
            getPermPanel().reloadPermissions();
        }
    }

    public static void loadPermissions(Player player) {
        if(player == null) return;

        PermissionAttachment attachment = getPailPlus().getAttachment(player);
        if(attachment==null) return;

        for (String key : attachment.getPermissions().keySet())
            attachment.unsetPermission(key);

        for(Map.Entry<String, Object> entry : getPailPlus().getPermsConfig().getPlayerPermissions(player).entrySet())
            if (entry.getValue() != null && entry.getValue() instanceof Boolean)
                attachment.setPermission(entry.getKey(), (Boolean)entry.getValue());

        player.recalculatePermissions();
        getPermPanel().reloadPermissions();
    }

    public static void preloadUsers() {
        for(String name : getPermsConfig().getRegisteredPlayers()) {
            Player player = getServer().getPlayer(name);
            if(player == null)
                getPermPanel().addPlayer(name);
            else
                getPailPlus().registerPlayer(player);
        }
    }

    public static void addPermissionNodes() {
        getAI().getPermissionPanel().getPannel().addBoxes(getPermsConfig().getRegisteredNodes());
    }*/ //TODO

    public static PailTray getTray() {
        return tray;
    }

    public static void toggleWindow() {
        me.escapeNT.pail.GUIComponents.MainWindow window = getPail().getMainWindow();
        if (window.isVisible()) {
            hideWindow();
        } else {
            showWindow();
        }
    }

    public static void hideWindow() {
        me.escapeNT.pail.GUIComponents.MainWindow window = getPail().getMainWindow();
        if (!window.isVisible()) return;
        window.setVisible(false);
    }

    public static void showWindow() {
        me.escapeNT.pail.GUIComponents.MainWindow window = getPail().getMainWindow();
        if (window.isVisible() && !window.isActive()) {
            bringToFront();
        } else if (!window.isVisible()) {
            window.setVisible(true);
        }
    }

    public static void bringToFront() {
        final me.escapeNT.pail.GUIComponents.MainWindow window = getPail().getMainWindow();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                window.toFront();
                window.repaint();
            }
        });

    }

    public static void reload() {
        getGame().getCommandDispatcher().process(getSender(), "reload");
    }

    public static void stop() {
    	getGame().getCommandDispatcher().process(getSender(), "stop");
    }

    public static void saveAll() {
    	getGame().getCommandDispatcher().process(getSender(), "save-all");
    }

    public static CommandSource getSender() {
        if (source == null) {
            source = getServer().getConsole();
        }
        return source;
    }

	public static void setDataFolder(File file) {
		dataFolder = file;
	}

	public static File getDataFolder() {
		return dataFolder;
	}
}
