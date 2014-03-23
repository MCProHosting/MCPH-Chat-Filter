package com.mcprohosting.plugins.mcph_chat_filter;

import com.mcprohosting.plugins.mcph_chat_filter.commands.SetChatMode;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MCPHChatFilter extends JavaPlugin {
	private static Map<String, Chatter> chatters = new HashMap<>();

	private static ChatMode chatMode = ChatMode.FREE;
    private static MCPHChatFilter plugin;
    private ChatConfig config;

    public void onEnable() {
        // Allow this to be accessed statically
        plugin = this;

        // Init config
        initConfig();
        
        // Register commands
        Bukkit.getPluginCommand("setchatmode").setExecutor(new SetChatMode());

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static MCPHChatFilter getPlugin() {
		return plugin;
	}

	public ChatMode getChatMode() {
		return chatMode;
	}
	
	public ChatConfig getConfigModel() {
		return config;
	}
	
	private void initConfig() {
        this.config = new ChatConfig(this);
        
        try {
        	config.init();
        } catch (Exception e) {
        	getLogger().severe("Somehow, the config failed to load!");
        }
	}

	public void setChatMode(ChatMode mode) {
		chatMode = mode;

		boolean mute = false;
		if (this.getChatMode().equals(ChatMode.MUTED) || this.getChatMode().equals(ChatMode.SHUTUP)) {
			mute = true;
		}

		for (Chatter chatter : chatters.values()) {
			chatter.setMuted(mute);
		}
	}

	public static Map<String, Chatter> getChatters() {
		return chatters;
	}
}
