package com.mcprohosting.plugins.mcph_chat_filter;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcprohosting.plugins.mcph_chat_filter.commands.CommandChatDelay;
import com.mcprohosting.plugins.mcph_chat_filter.commands.SetChatMode;

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
        getCommand("setchatmode").setExecutor(new SetChatMode());

        TabExecutor commandChatDelay = new CommandChatDelay(this);
        getCommand("setchatdelay").setExecutor(commandChatDelay);
        getCommand("setchatdelay").setTabCompleter(commandChatDelay);

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
