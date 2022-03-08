package com.taggernation.taggernationlib;

import com.taggernation.taggernationlib.placeholder.Placeholder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class TaggerNationLib extends JavaPlugin {

    private String papi;

    public static TaggerNationLib plugin;
    public static Placeholder papiHook;

    public void papiExist() {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papi = "HOOKED";
        } else {
            papi = "SKIPPED";
        }
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        papiHook = new Placeholder();

        papiExist();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
