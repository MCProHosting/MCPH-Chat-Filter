package com.mcprohosting.plugins.mcph_chat_filter;

import com.gmail.favorlock.configuration.ConfigModel;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterConfig extends ConfigModel {

    public FilterConfig(Plugin plugin) {
        CONFIG_HEADER = "MCPH Chat Filter Config!";
        CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
    }

    public int filter_waitTime = 3;
    public ArrayList<String> filter_approvedLinks = new ArrayList<String>() {{
        add("mcprohosting.com");
        add("antvenom.com");
        add("antvenom.net");
    }};
    public Map<String, Integer> filter_profanityList = new HashMap<String, Integer>();

}
