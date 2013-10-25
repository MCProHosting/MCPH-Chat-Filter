package com.mcprohosting.plugins.mcph_chat_filter;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MCPHChatFilter extends JavaPlugin {

	private static Plugin plugin;

	public void onEnable() {
		// Allow this to be accessed statically
		plugin = this;
	}

	public void onDisable() {

	}

	public static Plugin getPlugin() {
		return plugin;
	}

}
