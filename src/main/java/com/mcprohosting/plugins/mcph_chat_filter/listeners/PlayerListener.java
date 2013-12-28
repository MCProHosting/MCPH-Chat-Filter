package com.mcprohosting.plugins.mcph_chat_filter.listeners;

import com.mcprohosting.plugins.mcph_chat_filter.MCPHChatFilter;
import com.mcprohosting.plugins.mcph_chat_filter.regex.RegexManager;
import com.mcprohosting.plugins.mcph_chat_filter.utils.FontFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerListener implements Listener {

	private PluginManager pm = Bukkit.getServer().getPluginManager();
	private RegexManager rm = MCPHChatFilter.getRegexManager();


	public PlayerListener() {
		pm.registerEvents(this, MCPHChatFilter.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}

		final Player player = event.getPlayer();

		// If player has bypass chat permission then skip everything.
		if (player.hasPermission("mcf.bypass.chat")) {
			return;
		}

		String message = event.getMessage();

		// Check if player has bypass spam permission
		/*if (!player.hasPermission("mcf.bypass.spam")) {
			String previousMessage = MCPHChatFilter.getPreviousMessage(player);
			// Check if previousMessage equals message
			if (previousMessage.equalsIgnoreCase(message)) {
				event.setCancelled(true);
                return;
			}
			MCPHChatFilter.setPreviousMessage(player, message);
		}*/

		// Check if player has permission to use color codes
		if (!player.hasPermission("mcf.colors")) {
			message = FontFormat.stripColor(message);
		}


		// booleans
		boolean cancel = false;
		boolean kick = false;
		boolean warn = false;
		boolean console = false;
		boolean consolechain = false;
		boolean matched = false;
		boolean log = false;
		boolean aborted = false;
		boolean valid = false;

		// Strings
		String consolecmd = "";
		String regex = "";
		String matchedMsg = "";
		String matchedLogMsg = "";

		// More Strings (for warn, kick, etc)
		String warnMsg = "Please watch your language!";
		String kickMsg = "You have been kicked for inappropriate language";

		// Apply valid regex rules
		for (String line : MCPHChatFilter.getRegexManager().getRules()) {
			if (aborted) {
				break;
			}

			valid = false;

			if (line.startsWith("match ")) {
				regex = line.substring(6);
				matched = rm.matchPattern(FontFormat.stripColor(message.replaceAll("&([0-9a-fk-or])", "\u00A7&1")), regex);
				if (matched) {
					matchedMsg = message;
					matchedLogMsg = "MATCH <" + player.getName() + "> " + event.getMessage();
				}
				valid = true;
 			}

			if (matched) {
				// Check for any ignore statements
				if (line.startsWith("ignore ")) {
					if (line.startsWith("ignore user ")) {
						String users = line.substring(12);
						valid = true;
						for (String check : users.split(" ")) {
							if (player.getName().equalsIgnoreCase(check)) {
								matched = false;
								break;
							}
						}
					}
					if (line.startsWith("ignore permission ")) {
						String perms = line.substring(18);
						valid = true;
						for (String check : perms.split(" ")) {
							if (player.hasPermission(check)) {
								matched = false;
								break;
							}
						}
					}
					if (line.startsWith("ignore string ")) {
						String ignore = line.substring(14);
						valid = true;
						for (String check : ignore.split("\\|")) {
							if (FontFormat.stripColor(message.replaceAll("&([0-9a-fk-or])", "\u00A7$1")).toUpperCase().indexOf(check.toUpperCase()) != -1) {
								matched = false;
								break;
							}
						}
					}
				}
				// Check for any require statements
				if (line.startsWith("require ")) {
					if (line.startsWith("require user ")) {
						String users = line.substring(13);
						valid = true;
						boolean found = false;
						for (String check : users.split(" ")) {
							if (player.getName().equalsIgnoreCase(check)) {
								found = true;
								break;
							}
						}
						matched = found;
					}
					if (line.startsWith("require permission ")) {
						String perms = line.substring(19);
						valid = true;
						boolean found = false;
						for (String check : perms.split(" ")) {
							if (player.hasPermission(" ")) {
								found = true;
								break;
							}
						}
						matched = found;
					}
				}
				// Finally check for any then statements
				if (line.startsWith("then")) {
					if (line.startsWith("then replace ")) {
						message = FontFormat.stripColor(message.replaceAll("&([0-9a-fk-or])", "\u00A7$1"));
						message = rm.replacePattern(message, regex, line.substring(13));
						message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
						valid = true;
					}
					if (line.matches("then replace")) {
						message = FontFormat.stripColor(message.replaceAll("&([0-9a-fk-or])", "\u00A7$1"));
						message = rm.replacePattern(message, regex, "");
						message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
						valid = true;
					}
					if (line.startsWith("then rewrite ")) {
						message = rm.replacePattern(message, regex, line.substring(13));
						message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
						valid = true;
					}
					if (line.matches("then rewrite")) {
						message = rm.replacePattern(message, regex, "");
						message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
						valid = true;
					}
					if (line.startsWith("then randrep ")) {
						message = FontFormat.stripColor(message.replaceAll("&([0-9a-fk-or])", "\u00A7$1"));
						message = rm.replacePatternRandom(message, regex, line.substring(13));
						message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
						valid = true;
					}
					if (line.startsWith("then lower")) {
						message = FontFormat.stripColor(message.replaceAll("&([0-9a-fk-or])", "\u00A7$1"));
						message = rm.replacePatternLower(message, regex);
						message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
						valid = true;
					}
					if (line.startsWith("then deny")) {
						cancel = true;
						valid = true;
					}

					// aliasing, command and console
					if (line.startsWith("then console ")) {
						consolecmd = line.substring(13);
						consolechain = true;
						valid = true;
					}
					if (line.startsWith("then conchain ")) {
						consolecmd = line.substring(13);
						consolechain = true;
						valid = true;
					}

					// cancel
					if (line.startsWith("then cancel ")) {
						cancel = true;
					}

					// Punishment stuffs start here
					if (line.startsWith("then warn ")) {
						warnMsg = line.substring(10);
						warn = true;
						valid = true;
					}
					if (line.matches("then warn")) {
						warn = true;
						valid = true;
					}
					if (line.startsWith("then kick ")) {
						kickMsg = line.substring(10);
						kick = true;
						valid = true;
					}
					if (line.matches("then kick")) {
						kick = true;
						valid = true;
					}

					// abort, log, debug stuff
					if (line.startsWith("then abort")) {
						aborted = true;
						valid = true;
					}
					if (line.matches("then log")) {
						log = true;
						valid = true;
					}
					if (line.matches("then debug")) {
						System.out.println("[MCPH-Chat-Filter] Debug match: " + regex);
						System.out.println("[MCPH-Chat-Filter] Debug original: " + event.getMessage());
						System.out.println("[MCPH-Chat-Filter] Debug matched: " + matchedMsg);
						System.out.println("[MCPH-Chat-Filter] Debug current: " + message);
						System.out.println("[MCPH-Chat-Filter] Debug log: " + (log?"yes":"no"));
						System.out.println("[MCPH-Chat-Filter] Debug deny: " + (cancel?"yes":"no"));
						valid = true;
					}
				}
				if (valid == false) {
					MCPHChatFilter.getPlugin().getLogger().warning("Ignored syntax error in rules.txt: " + line);
				}
			}
		}
		// Perform flagged actions
		if (log) {
			rm.logToFile(matchedLogMsg);
			if (cancel) {
				rm.logToFile("SENT <" + player.getName() + "> message cancelled by deny rule.");
			}
			rm.logToFile("SENT <" + player.getName() + "> " + message);
		}
		// Check if the chat event is to be cancelled
		if (cancel) {
			event.setCancelled(true);
		} else {
			event.setMessage(message);
		}
		// Check if a console command should be run
		if (console) {
			consolecmd = consolecmd.replaceAll("&player", player.getName());
			consolecmd = consolecmd.replaceAll("&string", message);
			rm.logToFile("Sending console command: " + consolecmd);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), consolecmd);
		}
		// Check if multiple commands should be run
		if (consolechain) {
			consolecmd = consolecmd.replaceAll("&player", player.getName());
			consolecmd = consolecmd.replaceAll("&string", message);
			String conchain[] = consolecmd.split("\\|");
			for (String cmds : conchain) {
				rm.logToFile("Sending console command: " + cmds);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds);
			}
		}
		// Check if the user should be warned
		if (warn) {
			warnMsg = warnMsg.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
			final Player fplayer = player;
			final String fwarning = warnMsg;
			rm.logToFile("Warned " + fplayer.getName() + ": " + fwarning);
			fplayer.sendMessage(fwarning);
		}
		// Check if the user should be kicked
		if (kick) {
			kickMsg = kickMsg.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
			final Player fplayer = player;
			final String freason = kickMsg;
			rm.logToFile("Kicked " + fplayer.getName() + ": " + freason);
			fplayer.kickPlayer(freason);
		}
	}

}
