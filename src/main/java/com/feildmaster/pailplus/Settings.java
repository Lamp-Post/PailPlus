package com.feildmaster.pailplus;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.feildmaster.pailplus.monitor.Util;

import me.escapeNT.pail.Pail;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Settings {
	
    private static final File defaultConfig = new File(Util.getDataFolder(), "pailplus.conf");;

    private static final ConfigurationLoader<CommentedConfigurationNode> configManager = HoconConfigurationLoader.builder()
            .setFile(defaultConfig).build();
    public static ConfigurationNode rootNode;
    
    /**
     * Loads the configuration.
     */
    public static void load() {
        if (!defaultConfig.exists()) {
            rootNode = configManager.createEmptyNode(ConfigurationOptions.defaults());
        } else {
            try {
                rootNode = configManager.load();
            } catch (IOException e) {
                rootNode = null;
            }
        }
        defaults();
        save();
    }
    
    private static void defaults() {
        rootNode.getNode("always_show_tray").setValue(false);
        rootNode.getNode("minimize_to_tray").setValue(false);
        rootNode.getNode("close_action").setValue(0);
    }

    /**
     * Saves the configuration.
     */
    public static void save() {
        try {
            configManager.save(rootNode);
        } catch (IOException ex) {
            Pail.getLogger().log(Level.SEVERE, ex.toString());
        }
    }
    
    public static boolean fileExists() {
    	return defaultConfig.exists() ? true : false;
    }

    public boolean getAlwaysShowTray() {
        return rootNode.getNode("always_show_tray").getBoolean();
    }
    public void setAlwaysShowTray(boolean value) {
        rootNode.getNode("always_show_tray").setValue(value);
    }

    public boolean getMinimizeToTray() {
        return rootNode.getNode("minimize_to_tray").getBoolean();
    }
    public void setMinimizeToTray(boolean value) {
        rootNode.getNode("minimize_to_tray").setValue(value);
    }

    public int getCloseAction() {
        return rootNode.getNode("close_action").getInt();
    }
    public void setCloseAction(int value) {
        rootNode.getNode("close_action").setValue(value);
    }
}
