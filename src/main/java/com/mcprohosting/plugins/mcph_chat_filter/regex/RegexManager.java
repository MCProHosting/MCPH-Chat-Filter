package com.mcprohosting.plugins.mcph_chat_filter.regex;

import com.mcprohosting.plugins.mcph_chat_filter.MCPHChatFilter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;

public class RegexManager {

	public File getRulesFile() {
		File folder = MCPHChatFilter.getPlugin().getDataFolder();
		File file;

		// Ensure that directory exists.
		if (!folder.exists()) {
			if (folder.mkdirs()) {
				MCPHChatFilter.getPlugin().getLogger().info("Created directory '" + folder.getName() + "'");
			} else {
				return null;
			}
		}

		file = new File(folder, "rules.txt");
		// Does rules file exist? If not, create a basic file from template.
		if (!file.exists()) {
			try {
				file.createNewFile();
				BufferedInputStream input = new BufferedInputStream(MCPHChatFilter.getPlugin().getResource("rules.txt"));
				FileOutputStream output = new FileOutputStream(file);
				byte[] data = new byte[1024];
				int c;

				// Write lines from example resource to rules.txt
				while ((c = input.read(data, 0, 1024)) != -1) {
					output.write(data, 0, c);
				}

				input.close();
				output.close();
				MCPHChatFilter.getPlugin().getLogger().info("Created sample rules file '" + file.getName() + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

}
