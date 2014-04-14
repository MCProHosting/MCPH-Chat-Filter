package com.mcprohosting.plugins.mcph_chat_filter;

import java.io.File;

import com.gmail.favorlock.commonutils.configuration.StrictModel;

public class ChatConfig extends StrictModel {

    public Number chatdelay = 5;

    protected ChatConfig(MCPHChatFilter instance) {
        CONFIG_FILE = new File(instance.getDataFolder() + "/config.yml");
        CONFIG_HEADER = "MCPHChatFilter configuration file";
    }
}
