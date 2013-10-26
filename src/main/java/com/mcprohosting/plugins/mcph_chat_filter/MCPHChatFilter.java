package com.mcprohosting.plugins.mcph_chat_filter;

import com.mcprohosting.plugins.mcph_chat_filter.listeners.PlayerListener;
import com.mcprohosting.plugins.mcph_chat_filter.regex.RegexManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class MCPHChatFilter extends JavaPlugin {

	private static Plugin plugin;
	private static HashMap<Player, String> previousMessage;
	private static RegexManager regexManager;

	public void onEnable() {
		// Allow this to be accessed statically
		plugin = this;

		// Initialize previousMessage HashMap
		previousMessage = new HashMap<Player, String>();

		// Initialize RegexManager
		regexManager = new RegexManager();

		// Register Listeners
		registerListeners();
	}

	public void onDisable() {

	}

	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static String getPreviousMessage(Player player) {
		if (previousMessage.containsKey(player)) {
			return previousMessage.get(player);
		}
		return "";
	}

	public static void setPreviousMessage(Player player, String message) {
		previousMessage.put(player, message);
	}


	public static RegexManager getRegexManager() {
		return regexManager;
	}
}
