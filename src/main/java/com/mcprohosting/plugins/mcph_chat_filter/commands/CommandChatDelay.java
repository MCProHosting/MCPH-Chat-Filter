package com.mcprohosting.plugins.mcph_chat_filter.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.mcprohosting.plugins.mcph_chat_filter.MCPHChatFilter;

public class CommandChatDelay implements TabExecutor {

	private final MCPHChatFilter instance;
	
	public CommandChatDelay(MCPHChatFilter instance) {
		this.instance = instance;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		List<String> list = new ArrayList<String>();
		list.add("<#seconds>");
		return list;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (player.isOp()) {
				execute(player, args[0]);
				return true;
			} else if (player.hasPermission("mcphchatfilter.setchatdelay")) {
				execute(player, args[0]);
				return true;
			} else {
				// deny use
				return false;
			}
		} else {
			execute(sender, args[0]);
			return true;
		}
	}

	private void execute(CommandSender sender, String arg) {
		try {
			int delay = Integer.parseInt(arg);
			instance.getConfigModel().chatdelay = delay;
			sender.sendMessage(ChatColor.GRAY + "You have set the chat delay to " +
					ChatColor.YELLOW + delay + ChatColor.GRAY + " seconds.");
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Delay is not a number!");
		}
	}
}
