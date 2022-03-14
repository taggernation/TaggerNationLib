package com.taggernation.taggernationlib.placeholder;

import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Placeholder {

    /**
     * Replace placeholders in a String
     * @param text The text to replace
     * @param player The player to replace text for
     * @return The replaced text
     */
    public String replace(String text, Player player) {
        return PlaceholderAPI.setPlaceholders(player, text);
    }

    /**
     * Replace placeholders in a list of Strings
     * @param text The text list to replace
     * @param player The player to replace text for
     * @return The replaced text list
     */
    public List<String> replace(List<String> text, Player player) {
        List<String> newText = new ArrayList<>();
        for (String s : text) {
            newText.add(replace(s, player));
        }
        return newText;
    }
}
