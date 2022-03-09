package com.taggernation.taggernationlib.updatechecker;

import com.google.gson.Gson;
import com.taggernation.taggernationlib.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static com.taggernation.taggernationlib.TaggerNationLib.messageFramework;

public class UpdateChecker {
    private final Plugin plugin;
    private final URL url;
    private int interval;
    private UpdateChecker instance;
    private final Update update;
    private String message = null;
    private String permission = null;
    private boolean opNotify = false;

    /**
     * Initialize the update checker for the plugin.
     * @param plugin The plugin to check for an update for
     * @param url Json file URL to check for an update
     * @param interval The interval to check for an update in ticks
     */
    public UpdateChecker(Plugin plugin, URL url, int interval) throws IOException {
        this.plugin = plugin;
        this.url = url;
        this.interval = interval;
        this.instance = this;
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(url.openStream());
        this.update = gson.fromJson(reader, Update.class);
    }

    /**
     * Set the message to be displayed when an update is available.
     * @param message The message to be displayed
     */
    public UpdateChecker setMessage(String message) {
        this.message = processMessage(message);
        return this;
    }

    private String processMessage(String message) {
        if (message == null) {
            message = getDefaultMessage();
        }
        message = message.replace("{version}", update.version);
        message = message.replace("{pluginName}", plugin.getName());
        message = message.replace("{pluginVersion}", plugin.getDescription().getVersion());
        message = message.replace("{updateLink}", update.updateLink);
        message = message.replace("{changeLog}", String.join("\n", update.data));

        return message;
    }

    private String getDefaultMessage() {
        return "\n<st>          </st> <red>✢</red> <gradient:#cf1a91:#fbdbf4><bold>{pluginName}</gradient> <red>✢</red> <st>            </st>"
                + "\n"
                + "<gray>There is a new version of <aqua>{pluginName}</aqua> available!</gray>"
                + "\n"
                + "<gray> ● <gradient:#cf1a91:#fbdbf4><bold>Your version</gradient>: <aqua>{pluginVersion}</aqua></gray>"
                + "\n"
                + "<gray> ● <gradient:#cf1a91:#fbdbf4><bold>New Version</gradient>: <aqua>{version}</aqua></gray>"
                + "\n"
                + "\n"
                + "<gray><red>✢</red> Changelog <red>✢</red>\n"
                + "\n"
                + "<gray>{changeLog}</gray>"
                + "\n"
                + "<gray> ● <gradient:#cf1a91:#fbdbf4><bold>Update link</gradient>: <aqua>{updateLink}</aqua></gray>"
                + "\n"
                + "<st>          </st> <red>✢</red> <gradient:#cf1a91:#fbdbf4><bold>{pluginName}</gradient> <red>✢</red> <st>          </st>";
    }
    public String getMessage() {
        return message;
    }

    /**
     * Set Interval to check for an update in ticks.
     * @param interval The interval to check for an update in ticks
     */
    public UpdateChecker setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    /**
     * Set the permission required to receive a notification.
     * @param permission Permission required to receive a notification
     */
    public UpdateChecker setNotificationPermission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Set whether to notify op players without permission.
     * @param opNotify true to notify op players without permission, false to not
     */
    public UpdateChecker enableOpNotification(boolean opNotify) {
        this.opNotify = opNotify;
        return this;
    }

    /**
     * Is Op Notification enabled?
     * @return true if Op Notification is enabled, false if not
     */
    public boolean isOpNotificationEnabled() {
        return opNotify;
    }

    /**
     * Get the permission required to receive a notification.
     * @return Permission required to receive a notification
     */
    public String getNotificationPermission() {
        return permission;
    }

    /**
     * Set up the update checker.
     */
    public void setup() {
        if (message == null) {
            this.message = processMessage(null);
        }
        checkForUpdate();
    }
    /**
     * Check for an update.
     */
    public void checkForUpdate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (update == null) throw new IllegalStateException("Update has not been set");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (Bukkit.getOnlinePlayers().size() > 0 && player.hasPermission("greetings.update")) {
                        messageFramework.sendMessage(player,message);
                    }
                    new Logger(plugin).info(message, true);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, interval);
    }


}
