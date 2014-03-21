package com.mcprohosting.plugins.mcph_chat_filter;

import com.gmail.favorlock.util.text.FontFormat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddProfanity implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("mcphchatfilter.addprofanity") == false) {
            sender.sendMessage(FontFormat.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length != 2) {
            return false;
        }

        int severity;
        try {
            severity = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        MCPHChatFilter.getPlugin().getConf().profanityList.put(args[0], severity);

        return true;
    }
}
