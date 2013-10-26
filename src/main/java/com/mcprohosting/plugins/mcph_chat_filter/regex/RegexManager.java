package com.mcprohosting.plugins.mcph_chat_filter.regex;

import com.mcprohosting.plugins.mcph_chat_filter.MCPHChatFilter;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexManager {

	private CopyOnWriteArrayList<String> rules;
	private ConcurrentHashMap<String, Pattern> patterns;

	public RegexManager() {
		this.rules = new CopyOnWriteArrayList<String>();
		this.patterns = new ConcurrentHashMap<String, Pattern>();

		loadRules(getRulesFile());
	}

	private File getRulesFile() {
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

	private void loadRules(File file) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = input.readLine()) != null) {
				line = line.trim();
				if (!line.matches("^#.*") && !line.matches("")) {
					rules.add(line);
					if (line.startsWith("match ") || line.startsWith("catch ") || line.startsWith("replace ") || line.startsWith("rewrite ")) {
						String[] parts = line.split(" ", 2);
						compilePattern(parts[1]);
					}
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			MCPHChatFilter.getPlugin().getLogger().warning("Error reading the config file '" + file.getName() + "': " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void compilePattern(String rule) {
		if (patterns.get(rule) == null) {
			try {
				Pattern pattern = Pattern.compile(rule, Pattern.CASE_INSENSITIVE);
				patterns.put(rule, pattern);
				MCPHChatFilter.getPlugin().getLogger().fine("Successfully compiled regex: " + rule);
			} catch (PatternSyntaxException e) {
				MCPHChatFilter.getPlugin().getLogger().warning("Failed to compile regex: " + rule);
				MCPHChatFilter.getPlugin().getLogger().warning(e.getMessage());
			} catch (Exception e) {
				MCPHChatFilter.getPlugin().getLogger().severe("Unexpected error while compiling expression '" + rule + "'");
				e.printStackTrace();
			}
		}
	}

	public boolean matchPattern(String msg, String rule) {
		Pattern pattern = patterns.get(rule);
		if (pattern == null) {
			return false;
		}
		Matcher matcher = pattern.matcher(msg);
		return matcher.find();
	}

	public String replacePattern(String msg, String rule, String replacement) {
		Pattern pattern = patterns.get(rule);
		if (pattern == null) {
			return msg;
		}
		Matcher matcher = pattern.matcher(msg);
		return matcher.replaceAll(replacement);
	}

	public String replacePatternLower(String msg, String rule) {
		String replacement = msg;
		Matcher matcher = Pattern.compile(rule).matcher(replacement);
		StringBuilder builder = new StringBuilder();
		int last = 0;

		while (matcher.find()) {
			builder.append(replacement.substring(last, matcher.start()));
			builder.append(matcher.group(0).toLowerCase());
			last = matcher.end();
		}
		builder.append(replacement.substring(last));
		return builder.toString();
	}

	public String replacePatternRandom(String msg, String rule, String replacement) {
		Pattern pattern = patterns.get(rule);
		if (pattern == null) {
			return msg;
		}
		Matcher matcher = pattern.matcher(msg);
		String[] rand = replacement.split("\\|");
		Random random = new Random();
		int randInt = random.nextInt(rand.length);
		return matcher.replaceAll(rand[randInt]);
	}

	public void logToFile(String message) {
		try {
			File folder = MCPHChatFilter.getPlugin().getDataFolder();
			File file = new File(folder, "mcphchatfilter.log");

			if (!folder.exists()) {
				folder.mkdir();
			}

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(getDate() + " " + message);
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getDate() {
		String string;
		Format formatter;
		Date date = new Date();
		formatter = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]");
		string = formatter.format(date);
		return string;
	}

	public CopyOnWriteArrayList<String> getRules() {
		return this.rules;
	}

	public ConcurrentHashMap<String, Pattern> getPatterns() {
		return this.patterns;
	}

}
