package com.mcprohosting.plugins.mcph_chat_filter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

public class MCPHChatFilter extends JavaPlugin {

    private static MCPHChatFilter plugin;
    private FilterConfig config;

    public void onEnable() {
        // Allow this to be accessed statically
        plugin = this;

        // Initialize Config
        try {
            config = new FilterConfig(this);
            config.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        // Register commands
        Bukkit.getPluginCommand("addprofanity").setExecutor(new AddProfanity());

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void onDisable() {
        saveConf();
    }

    public static MCPHChatFilter getPlugin() {
		return plugin;
	}

    public FilterConfig getConf() {
        return config;
    }

    public void saveConf() {
        try {
            config.save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
