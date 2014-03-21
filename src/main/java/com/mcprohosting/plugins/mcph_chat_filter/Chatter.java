package com.mcprohosting.plugins.mcph_chat_filter;

public class Chatter {

    private long timeLastMessageSent;
    private String lastMessageSent;

    public Chatter() {
        timeLastMessageSent = 0;
        lastMessageSent = "";
    }

    public long getTimeLastMessageSent() {
        return timeLastMessageSent;
    }

    public void setTimeLastMessageSent(long time) {
        timeLastMessageSent = time;
    }

    public String getLastMessageSent() {
        return lastMessageSent;
    }

    public void setLastMessageSent(String message) {
        lastMessageSent = message;
    }

}
