package com.taggernation.taggernationlib.vanish;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class VanishCheck {
  /**
   * @param player Player to check if they're vanished or not.
   * @return true if the player is vanished, false if not.
   */
  public boolean isVanished(Player player) {
    for (final MetadataValue meta : player.getMetadata("vanished")) {
      if (meta.asBoolean()) return true;
    }
    return false;
  }
}
