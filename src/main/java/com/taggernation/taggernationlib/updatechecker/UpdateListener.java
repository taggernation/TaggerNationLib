package com.taggernation.taggernationlib.updatechecker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.taggernation.taggernationlib.TaggerNationLib.messenger;

public class UpdateListener implements Listener {

  private final UpdateChecker instance;

  public UpdateListener(UpdateChecker instance) {
    this.instance = instance;
  }

  @EventHandler
  public void playerJoinNotification(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if ((player.isOp() && instance.isOpNotificationEnabled()) ||
            (instance.getNotificationPermission() != null &&
                    player.hasPermission(instance.getNotificationPermission()))) {
      for (final String message : instance.getMessage()) {
        messenger.sendMessage(player, message);
      }
    }
  }
}
