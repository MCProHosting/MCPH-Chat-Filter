package com.mcprohosting.plugins.mcph_chat_filter.commands;

import com.mcprohosting.plugins.mcph_chat_filter.ChatMode;
import com.mcprohosting.plugins.mcph_chat_filter.MCPHChatFilter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetChatMode implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission("mcphchatfilter.setchatmode") == false) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command");
			return true;
		}

		if (args.length != 1) {
			return false;
		}

		if (args[0].equalsIgnoreCase("shutup")) {
			MCPHChatFilter.getPlugin().setChatMode(ChatMode.SHUTUP);
			sender.sendMessage(ChatColor.GREEN + "Set chat mode to: " + ChatColor.DARK_RED + ChatMode.SHUTUP);
			return true;
		} else if (args[0].equalsIgnoreCase("mute")) {
			MCPHChatFilter.getPlugin().setChatMode(ChatMode.MUTED);
			sender.sendMessage(ChatColor.GREEN + "Set chat mode to: " + ChatColor.YELLOW + ChatMode.MUTED);
			return true;
		} else if (args[0].equalsIgnoreCase("free")) {
			MCPHChatFilter.getPlugin().setChatMode(ChatMode.FREE);
			sender.sendMessage(ChatColor.GREEN + "Set chat mode to: " + ChatColor.DARK_GREEN + ChatMode.FREE);
			return true;
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You did not enter a valid chat mode (shutup/mute/free)");
			return false;
		}
	}
}
