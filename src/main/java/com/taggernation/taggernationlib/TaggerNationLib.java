package com.taggernation.taggernationlib;

import com.taggernation.taggernationlib.placeholder.Placeholder;
import io.github.alenalex.adventurelib.spigot.impl.SpigotMessenger;
import lombok.Getter;
import lombok.SneakyThrows;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public class TaggerNationLib extends JavaPlugin {

    private String papi;

    public static TaggerNationLib plugin;
    public static Placeholder papiHook;
    public static SpigotMessenger messenger;
    public static MiniMessage miniMessage;

    public void papiExist() {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papi = "HOOKED";
        } else {
            papi = "SKIPPED";
        }
    }
    @SneakyThrows
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        papiHook = new Placeholder();
//        messageFramework = new FrameworkBuilder().setPlugin(this).withMiniMessageEngine().build();
        messenger = SpigotMessenger
                .builder()
                .setPlugin(this)
                .defaultToMiniMessageTranslator()
                .build();
        miniMessage = MiniMessage.builder().build();
        papiExist();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
