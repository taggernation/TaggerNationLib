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
    private Update update = null;
    private String message = null;
    private String permission = null;
    private boolean opNotify = false;

    /**
     * Initialize the update checker for the plugin.
     * @param plugin The plugin to check for an update for
     * @param url Json file URL to check for an update
     * @param interval The interval to check for an update in ticks
     */
    public UpdateChecker(Plugin plugin, URL url, int interval) {
        this.plugin = plugin;
        this.url = url;
        this.interval = interval;
        this.instance = this;
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

        return new Logger(plugin).adventureFormat(message);
    }

    private String getDefaultMessage() {
        return "<st>          </st> <red>✢</red> <gradient:#cf1a91:#fbdbf4><bold>{pluginName}</gradient> <red>✢</red> <st>            </st>"
                + "<newline>"
                + "<gray>     There is a new version of <aqua>{pluginName}</aqua> available!</gray>"
                + "<newline>"
                + "<gray>   Your version: <aqua>{pluginVersion}</aqua></gray>"
                + "<newline>"
                + "<gray>   New version: <aqua>{version}</aqua></gray>"
                + "<newline>"
                + "<newline>"
                + "<gray> <red>✢</red> Changelog <red>✢</red>\n"
                + "<newline>"
                + "<gray>{changeLog}</gray>"
                + "<newline>"
                + "<gray>   Update link: <aqua>{updateLink}</aqua></gray>"
                + "<newline>"
                + "<st>          </st> <red>✢</red> <gradient:#cf1a91:#fbdbf4><bold>{pluginName}</gradient> <red>✢</red> <st>";
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
     * @throws IOException If the URL is invalid
     */
    public void setup() throws IOException {
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(url.openStream());
        update = gson.fromJson(reader, Update.class);
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
                    new Logger(plugin).info(message, false);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, interval);
    }


}
