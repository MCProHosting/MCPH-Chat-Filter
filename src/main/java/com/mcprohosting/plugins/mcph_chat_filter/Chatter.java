package com.mcprohosting.plugins.mcph_chat_filter;

public class Chatter {
    private boolean muted;
    private long timeLastMessageSent;
    private String lastMessageSent;

    public Chatter() {
        timeLastMessageSent = 0;
        lastMessageSent = "";

        if (MCPHChatFilter.getPlugin().getChatMode().equals(ChatMode.MUTED)) {
            this.muted = true;
        } else if (MCPHChatFilter.getPlugin().getChatMode().equals(ChatMode.SHUTUP)) {
            this.muted = true;
        } else if (MCPHChatFilter.getPlugin().getChatMode().equals(ChatMode.FREE)) {
            this.muted = false;
        }
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

    public boolean isMuted() {
        return this.muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

}
