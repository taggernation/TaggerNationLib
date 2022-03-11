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
import java.util.ArrayList;
import java.util.List;

import static com.taggernation.taggernationlib.TaggerNationLib.messageFramework;

public class UpdateChecker {
    private final Plugin plugin;
    private int interval;
    private final UpdateChecker instance;
    private final Update update;
    private List<String> message = new ArrayList<>();
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
        this.interval = interval;
        this.instance = this;
        plugin.getServer().getPluginManager().registerEvents(new UpdateListener(this), plugin);
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(url.openStream());
        this.update = gson.fromJson(reader, Update.class);
    }

    private void processMessage() {
        List<String> formatter = new ArrayList<>();
        for (String list : update.message) {
            if (list.contains("{pluginName}")) {
                list = list.replace("{pluginName}", plugin.getName());
            }
            if (list.contains("{pluginVersion}")) {
                list = list.replace("{pluginVersion}", plugin.getDescription().getVersion());
            }
            if (list.contains("{updateLink}")) {
                list = list.replace("{updateLink}", update.updateLink);
            }
            if (list.contains("{latestVersion}")) {
                list = list.replace("{latestVersion}", update.version);
            }
            if (list.contains("{changeLog}")) {
                list = list.replace("{changeLog}", String.join("\n", update.changeLog));
            }
            if (list.contains("{type}")) {
                list = list.replace("{type}", getUpdateType());
            }
            formatter.add(list);
        }
        message = formatter;
    }
    private String getUpdateType() {
        if (update.hotFix) {
            return "Hotfix";
        }else if (update.bugFix) {
            return "Bugfix";
        }else if (update.newFeature) {
            return "New Feature";
        }else {
            return "Update";
        }

    }
    public List<String> getMessage() {
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
    public UpdateChecker setup() {
        processMessage();
        checkForUpdate();
        return this;
    }
    /**
     * Check for an update.
     */
    public void checkForUpdate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (Bukkit.getOnlinePlayers().size() > 0 && player.hasPermission("greetings.update")) {
                        messageFramework.sendMessage(player,message);
                    }
                }
                new Logger(plugin).info(message, true);
            }
        }.runTaskTimerAsynchronously(plugin, 0, interval);
    }


}
