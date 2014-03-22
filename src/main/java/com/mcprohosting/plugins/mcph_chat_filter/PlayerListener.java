package com.mcprohosting.plugins.mcph_chat_filter;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        MCPHChatFilter.getChatters().put(event.getPlayer().getName(), new Chatter());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuite(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        MCPHChatFilter.getChatters().remove(MCPHChatFilter.getChatters().get(event.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Chatter chatter = MCPHChatFilter.getChatters().get(event.getPlayer().getName());

        if (event.getPlayer().hasPermission("mcphchatfilter.bypassall") == false) {
			if (chatter.isMuted()) {
				event.getPlayer().sendMessage(ChatColor.RED + "Your chat is currently muted at this time to prevent spam.");
				event.setCancelled(true);
				return;
			}

			if (chatter.getLastMessageSent().contains("%")) {
				event.getPlayer().sendMessage(ChatColor.RED + "There's currently a bug with using % signs in messages, please don't! Sorry.");
				event.setCancelled(true);
				return;
			}

            if (System.currentTimeMillis() - chatter.getTimeLastMessageSent() < 5* 1000) {
                event.getPlayer().sendMessage(ChatColor.RED + "Chat is restricted to one message per person every 5 seconds. Thank you for understanding! :D");
                event.setCancelled(true);
                return;
            }

            if (chatter.getLastMessageSent().equalsIgnoreCase(event.getMessage())) {
                event.getPlayer().sendMessage(ChatColor.RED + "You already sent that message. Perhaps you should try saying something new! ;)");
                event.setCancelled(true);
                return;
            }

			String message = event.getMessage();
			if ((FilterUtil.failCharacterSpam(message) || FilterUtil.failCurse(message) || FilterUtil.failLink(message) || FilterUtil.failIP(message) || FilterUtil.failCaps(message))) {
				event.getPlayer().sendMessage(ChatColor.RED + "Your message looks like spam, please rephrase it.");
				event.setCancelled(true);
				return;
			}
        }

        chatter.setTimeLastMessageSent(System.currentTimeMillis());
        chatter.setLastMessageSent(event.getMessage());
    }
}
