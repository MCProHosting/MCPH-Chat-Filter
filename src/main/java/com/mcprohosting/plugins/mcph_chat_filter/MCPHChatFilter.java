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

    public void onEnable() {
        // Allow this to be accessed statically
        plugin = this;

        // Register commands
        Bukkit.getPluginCommand("setchatmode").setExecutor(new SetChatMode());

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void onDisable() {

    }

    public static MCPHChatFilter getPlugin() {
		return plugin;
	}

	public ChatMode getChatMode() {
		return this.chatMode;
	}

	public void setChatMode(ChatMode mode) {
		this.chatMode = mode;

		boolean mute = false;
		if (this.getChatMode().equals(ChatMode.MUTED) || this.getChatMode().equals(ChatMode.SHUTUP)) {
			mute = true;
		}

		for (Chatter chatter : this.chatters.values()) {
			chatter.setMuted(mute);
		}
	}

	public static Map<String, Chatter> getChatters() {
		return chatters;
	}
}
