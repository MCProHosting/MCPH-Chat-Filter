package com.mcprohosting.plugins.mcph_chat_filter.listeners;

import com.mcprohosting.plugins.mcph_chat_filter.MCPHChatFilter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerListener implements Listener {

	public PluginManager pm = Bukkit.getServer().getPluginManager();


	public PlayerListener() {
		pm.registerEvents(this, MCPHChatFilter.getPlugin());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}

		final Player player = event.getPlayer();

		// If player has bypass chat permission then skip everything.
		if (player.hasPermission("mcf.bypass.chat")) {
			return;
		}

		String message = event.getMessage();

		// Check if player has bypass spam permission
		if (!player.hasPermission("mcf.bypass.spam")) {
			String previousMessage = MCPHChatFilter.getPreviousMessage(player);
			// Check if previousMessage equals message
			if (previousMessage.equalsIgnoreCase(message)) {
				event.setCancelled(true);
				return;
			}
			MCPHChatFilter.setPreviousMessage(player, message);
		}
	}

}
