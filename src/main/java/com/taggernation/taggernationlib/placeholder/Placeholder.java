package com.taggernation.taggernationlib.placeholder;

import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.List;

import static com.taggernation.taggernationlib.TaggerNationLib.*;
public class Placeholder {

    /**
     * Parse a string for placeholders
     * @param text The text to parse
     * @param player The player to parse for
     * @return The parsed text
     */
    public String parse(String text, Player player) {
        return PlaceholderAPI.setPlaceholders(player, text);
    }

    /**
     * Parse List of String for placeholders
     * @param text The text List to parse
     * @param player The player to parse for
     * @return The parsed text List
     */
    public List<String> parse(List<String> text, Player player) {
        List<String> newText = new ArrayList<>();
        for (String s : text) {
            newText.add(parse(s, player));
        }
        return newText;
    }

}
