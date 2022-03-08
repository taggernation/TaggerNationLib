package com.taggernation.taggernationlib.logger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import static com.taggernation.taggernationlib.TaggerNationLib.*;

public class Logger {
    String pluginName = adventureFormat("<gradient:#cf1a91:#fbdbf4>[ </gradient>" + " <white><bold>" + "<pluginName>" + " </white></bold>" + "<gradient:#cf1a91:#fbdbf4> ]</gradient>  <red>➜  </red>");

    /**
     * Send info message to console
     * @param message Message to send
     * @param plugin Plugin to send message from
     * @param adventureFormat Whether to use adventure format
     */
    public void info(String message, Plugin plugin, boolean adventureFormat) {
        if (!adventureFormat) {
            Bukkit.getLogger().info(pluginName.replace("<pluginName>", plugin.getName()) + ChatColor.translateAlternateColorCodes('&', message));
        }
        plugin.getLogger().info(adventureFormat(message));
    }

    /**
     * Send warning message to console
     * @param message Message to send
     * @param plugin Plugin to send message from
     * @param adventureFormat Whether to use adventure format
     */
    public void warn(String message, Plugin plugin, boolean adventureFormat) {
        if (!adventureFormat) {
            Bukkit.getLogger().info(pluginName.replace("<pluginName>", plugin.getName()) + ChatColor.YELLOW + ChatColor.translateAlternateColorCodes('&', message));
        }
        plugin.getLogger().info(adventureFormat(message));
    }

    /**
     * Send error message to console
     * @param message Message to send
     * @param plugin Plugin to send message from
     * @param adventureFormat Whether to use adventure format
     */
    public void error(String message, Plugin plugin, boolean adventureFormat) {
        if (!adventureFormat) {
            Bukkit.getLogger().info(pluginName.replace("<pluginName>", plugin.getName()) + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', message));
        }
        plugin.getLogger().info(adventureFormat(message));
    }

    /**
     * Get adventure formatted message
     * @param message Message to format
     * @return Formatted message
     */
    public String adventureFormat(String message) {
        Component component = miniMessage.deserialize(message);
        return GsonComponentSerializer.gson().serialize(component);
    }
}
