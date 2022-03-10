package com.taggernation.taggernationlib.logger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static com.taggernation.taggernationlib.TaggerNationLib.*;

public class Logger {
    private final LegacyComponentSerializer legecySerializer = LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build();
    String pluginName = adventureFormat("<gradient:#cf1a91:#fbdbf4>[ </gradient>" + " <white><bold>" + "<pluginName>" + " </white></bold>" + "<gradient:#cf1a91:#fbdbf4> ]</gradient>  <red>➜  </red>");

    Plugin plugin;

    public Logger(Plugin plugin) {
        this.plugin = plugin;
    }


    /**
     * Send info message to console
     * @param message Message to send
     * @param adventureFormat Whether to use adventure format
     */
    public void info(String message, boolean adventureFormat) {
        if (!adventureFormat) {
            Bukkit.getLogger().info(pluginName.replace("<pluginName>", plugin.getName()) + ChatColor.translateAlternateColorCodes('&', message));
        }
        Bukkit.getLogger().info(adventureFormat(message));
    }
    /**
     * Send String List of info message to console
     * @param message Message to send
     * @param adventureFormat Whether to use adventure format
     */
    public void info(List<String> message,boolean adventureFormat) {
        for (String msg : message) {
            info(msg,adventureFormat);
        }
    }

    /**
     * Send warning message to console
     * @param message Message to send
     * @param adventureFormat Whether to use adventure format
     */
    public void warn(String message, boolean adventureFormat) {
        if (!adventureFormat) {
            Bukkit.getLogger().info(pluginName.replace("<pluginName>", plugin.getName()) + ChatColor.YELLOW + ChatColor.translateAlternateColorCodes('&', message));
        }
        Bukkit.getLogger().info(adventureFormat(message));
    }
    /**
     * Send String List of warning message to console
     * @param message Message to send
     * @param adventureFormat Whether to use adventure format
     */
    public void warn(List<String> message,boolean adventureFormat) {
        for (String msg : message) {
            warn(msg,adventureFormat);
        }
    }

    /**
     * Send error message to console
     * @param message Message to send
     * @param adventureFormat Whether to use adventure format
     */
    public void error(String message, boolean adventureFormat) {
        if (!adventureFormat) {
            Bukkit.getLogger().info(pluginName.replace("<pluginName>", plugin.getName()) + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', message));
        }
        Bukkit.getLogger().info(adventureFormat(message));
    }
    /**
     * Send String List of error message to console
     * @param message Message to send
     * @param adventureFormat Whether to use adventure format
     */
    public void error(List<String> message,boolean adventureFormat) {
        for (String msg : message) {
            error(msg,adventureFormat);
        }
    }

    /**
     * Get adventure formatted message
     * @param message Message to format
     * @return Formatted message
     */
    public String adventureFormat(String message) {
        Component component = miniMessage.deserialize(message);
        return legecySerializer.serialize(component);
    }
}
