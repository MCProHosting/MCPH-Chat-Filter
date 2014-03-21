package com.mcprohosting.plugins.mcph_chat_filter;

public class FilterUtil {

    public static boolean failCurse(String message) {
        message = message.toLowerCase();

        for (String word : MCPHChatFilter.getPlugin().getConf().profanityList.keySet()) {
            if (message.contains(word.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

}
