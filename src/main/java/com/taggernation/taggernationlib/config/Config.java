package com.taggernation.taggernationlib.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public class Config {

    private final File file;
    private final FileConfiguration config;
    private final Plugin plugin;

    /**
     * Initializes the Config.
     * @param plugin Instance of the plugin you want to initialize the config for
     * @param fileName String
     * @param force boolean enable/disable force file update
     * @param copy boolean either copy the file from the plugin or not
     */
    public Config(Plugin plugin, String fileName, boolean force, boolean copy) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
        this.config = YamlConfiguration.loadConfiguration(this.file);
        if (copy) {
            copy(force);
        }
    }

    /**
     * Initializes the Config. in given path
     * @param plugin Instance of the plugin you want to initialize the config for
     * @param fileName String
     * @param path String path you want to initialize the config in
     * @param force boolean enable/disable force file update
     * @param copy boolean either copy the file from the plugin or not
     */
    public Config(Plugin plugin, String fileName, String path, boolean force, boolean copy) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder() + File.separator + path, fileName);
        this.config = YamlConfiguration.loadConfiguration(this.file);
        if (copy) {
            copy(force, path);
        }
    }

    /**
     * Get Initialized file
     * @return File object
     */
    public File getFile() {
        return file;
    }

    /**
     * Get Initialized config.
     * @return FileConfiguration
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Save the config.
     */
    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload the config.
     * @throws IOException IOException
     * @throws InvalidConfigurationException InvalidConfigurationException
     */
    public void reload() throws IOException, InvalidConfigurationException {
        config.load(file);
    }

    /**
     * Copy the config.
     * @param force boolean enable/disable force copy
     */
    public void copy( boolean force) {
        if (!file.exists()) {
            plugin.saveResource(file.getName(), force);
        }
    }
    /**
     * Copy the config to the given path.
     * @param force boolean enable/disable force copy
     * @param path String path to save the resource
     */
    public void copy( boolean force, String path) {
        plugin.saveResource(path, force);
    }

    /**
     * Update the Config with the newer version of the file
     * @param currentVersion String
     * @param versionPath String
     * @return boolean true if updated
     * @throws IOException IOException
     * @throws InvalidConfigurationException InvalidConfigurationException
     */
    public boolean updateConfig(@NotNull String currentVersion, @NotNull String versionPath) throws IOException, InvalidConfigurationException {
        String version = this.getString(versionPath);
        if (version.equals(currentVersion)) {
            File newFile = new File(file.getParentFile(), "old_" + version + "_" + getFile().getName());
            File oldFile = getFile();
            if (oldFile.renameTo(newFile)) {
                plugin.saveResource(getFile().getName(), true);
                reload();
                return true;
            }
        }
        return false;
    }


    /**
     * update the config's existing path with given value.
     * @param path String
     * @param value Object
     * @throws IllegalArgumentException IOException
     */
    public void update(String path, Object value) {
        if (config.contains(path)) {
            config.set(path, value);
            save();
        }else {
            throw new IllegalArgumentException(path + " does not exist" + " in " + file.getName());
        }
    }

    /**
     * Set the given path with given value.
     * @param path String
     * @param value Object
     */
    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    /**
     * Get the given path.
     * @param path String
     * @return Object
     */
    public Object get(String path) {
        return config.get(path);
    }

    /**
     * Get the given path as a list.
     * @param path String
     * @return List
     */
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    /**
     * Get the given path as a boolean.
     * @param path String
     * @return boolean
     */
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    /**
     * Get the given path as an int.
     * @param path String
     * @return int
     */
    public int getInt(String path) {
        return config.getInt(path);
    }

    /**
     * Get the given path as a double.
     * @param path String
     * @return double
     */
    public double getDouble(String path) {
        return config.getDouble(path);
    }

    /**
     * Get the given path as a long.
     * @param path String
     * @return long
     */
    public String getString(String path) {
        return config.getString(path);
    }

}
