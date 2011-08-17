package feildmaster.AdvancedPail.Monitors;

import feildmaster.AdvancedPail.AdvPail;
import feildmaster.AdvancedPail.Config.PermsConf;
import feildmaster.AdvancedPail.Pail.MainWindow;
import feildmaster.AdvancedPail.Pail.PermissionPanel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.escapeNT.pail.Pail;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

public class Util {
    private static final Server server = Bukkit.getServer();
    private static final PluginManager pm = server.getPluginManager();
    private static Map<String, Boolean> pluginStatus = new HashMap<String, Boolean>();
    private static Pail pail;
    private static AdvPail aPail;
    
    
    public static Server getServer() {
        return server;
    }
    public static PluginManager pm() {
        return pm;
    }
    
    public static Pail getPail() {
        if(pail == null) pail = (Pail)pm.getPlugin("Pail");
        return pail;
    }
    public static AdvPail getAdvPail() {
        if(aPail == null) aPail = (AdvPail)pm.getPlugin("AdvancedPail");
        return aPail;
    }
    public static MainWindow getAI() {
        return getAdvPail().getAI();
    }
    public static PermsConf getPermsConfig() {
        return getAdvPail().getPermsConfig();
    }
    public static PermissionPanel getPermPanel() {
        return getAdvPail().getAI().getPermissionPanel();
    }
    
    public static Player[] getPlayers() {
        return server.getOnlinePlayers();
    }
    
    public static Plugin[] getPlugins() {
        return pm.getPlugins();
    }
    
    public static Map<String, Boolean> getPluginStatus() {
        if(pluginStatus.isEmpty()) return updatePluginStatus();
        return pluginStatus;
    }
    public static Map<String, Boolean> updatePluginStatus() {
        for(Plugin p : getPlugins()) 
            pluginStatus.put(p.getDescription().getName(), p.isEnabled());
        return pluginStatus;
    }
    
    public static List<World> getWorlds() {
        return server.getWorlds();
    }

    public static void setPlayerPermission(String player, String perm, Boolean value) {
        // Set config
        getAdvPail().getPermsConfig().setPermission(player, perm, value);
        Player p = server.getPlayer(player);
        if(p != null)
            loadPermissions(p);
        else
            getPermPanel().reloadPermissions();
    }
    
    public static void loadPermissions(Player player) {
        if(player == null)return;
        
        PermissionAttachment attachment = getAdvPail().getAttachment(player);
        if(attachment==null) {
            System.out.println("Attachment not found!");
            return;
        }
        
        for (String key : attachment.getPermissions().keySet())
            attachment.unsetPermission(key);
        
        for(Map.Entry<String, Object> entry : getAdvPail().getPermsConfig().getPlayerPermissions(player).entrySet()) {
            if (entry.getValue() != null && entry.getValue() instanceof Boolean)
                attachment.setPermission(entry.getKey(), (Boolean)entry.getValue());
        }
        
        player.recalculatePermissions();
        getPermPanel().reloadPermissions();
    }
    
    public static Configuration getConfig() {
        return aPail.getConfiguration();
    }

    public static void preloadUsers() {
        for(String name : getPermsConfig().getRegisteredPlayers()) {
            getAdvPail().registerPlayer(getServer().getPlayer(name));
            getPermPanel().addPlayer(name);
        }
    }

    public static void addPermissionNodes() {
        getAI().getPermissionPanel().getPannel().addBoxes(getPermsConfig().getRegisteredNodes());
    }
}
