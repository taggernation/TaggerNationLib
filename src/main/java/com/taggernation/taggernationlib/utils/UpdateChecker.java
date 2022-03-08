package com.taggernation.taggernationlib.utils;

import com.google.gson.Gson;
import com.taggernation.taggernationlib.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import static com.taggernation.taggernationlib.TaggerNationLib.*;

public class UpdateChecker {

    private int taskId;
    Logger logger = new Logger(plugin);

    private String readUpdateURL() throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL("https://pxllab.in/greetings/update.json");
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    static class Source {
        Float version;
        List<Data> data;
    }

    static class Data {
        String string;
    }

    public Source getUpdateInfo() throws Exception {
        String json = readUpdateURL();
        Gson gson = new Gson();
        return gson.fromJson(json, Source.class);
    }

    public boolean updateAvailable() throws Exception {
        float currentVersion = Float.parseFloat((plugin.getDescription().getVersion()));
        float latestVersion = getUpdateInfo().version;

        return currentVersion < latestVersion;
    }

    public void getChangelogs() throws Exception {
        for (Data list : getUpdateInfo().data) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Bukkit.getOnlinePlayers().size() > 0 && player.hasPermission("greetings.update")) {
                    messageFramework.sendMessage(player, list.string);
                }
                logger.info(list.string, false);
            }
        }
    }

    public void initUpdateCheck() throws Exception {
        int hour = 1;
        double minutes = hour * 60;
        double seconds = minutes * 60;
        long ticks = ((int) seconds) * 20L;
        stopUpdateCheck();
        if (ticks > 0) {
            taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::check4Update, ticks, ticks);
        } else {
            stopUpdateCheck();
        }

        if (updateAvailable()) {
            try {
                getChangelogs();
            } catch (Exception e) {
                logger.error("Couldn't fetch changelogs.", false);
                e.printStackTrace();
            }
        }
    }

    public void stopUpdateCheck() {
        Bukkit.getServer().getScheduler().cancelTask(taskId);
    }

    public void check4Update() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                initUpdateCheck();
            } catch (Exception e) {
                logger.error("Couldn't check for updates.", false);
                e.printStackTrace();
            }
        });
    }
}
