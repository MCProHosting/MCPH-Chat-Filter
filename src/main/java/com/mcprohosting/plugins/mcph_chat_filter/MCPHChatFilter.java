package com.mcprohosting.plugins.mcph_chat_filter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MCPHChatFilter extends JavaPlugin {

    private static Plugin plugin;

    public void onEnable() {
        // Allow this to be accessed statically
        plugin = this;

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static Plugin getPlugin() {
		return plugin;
	}
}
