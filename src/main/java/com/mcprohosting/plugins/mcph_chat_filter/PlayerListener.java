package com.mcprohosting.plugins.mcph_chat_filter;

import com.gmail.favorlock.util.text.FontFormat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerListener implements Listener {

    private Map<String, Chatter> chatters = new HashMap<>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        chatters.put(event.getPlayer().getName(), new Chatter());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuite(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        chatters.remove(chatters.get(event.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Chatter chatter = chatters.get(event.getPlayer().getName());

        if (event.getPlayer().hasPermission("mcphchatfilter.bypassall") == false) {
            if (System.currentTimeMillis() - chatter.getTimeLastMessageSent() < 10 * 1000) {
                event.getPlayer().sendMessage(FontFormat.RED + "Chat is restricted to one message per person every 10 seconds. Thank you for understanding! :D");
                event.setCancelled(true);
            }

            if (FilterUtil.failCurse(event.getMessage())) {
                event.getPlayer().sendMessage(FontFormat.RED + "Our chat filter has detected profanity in your message. Please watch your language! :)");
                event.setCancelled(true);
            }

            if (chatter.getLastMessageSent().equalsIgnoreCase(event.getMessage())) {
                event.getPlayer().sendMessage(FontFormat.RED + "You already sent that message. Perhaps you should try saying something new! ;)");
                event.setCancelled(true);
            }
        }

        chatter.setTimeLastMessageSent(System.currentTimeMillis());
        chatter.setLastMessageSent(event.getMessage());
    }

}
