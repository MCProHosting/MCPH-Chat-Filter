package com.mcprohosting.plugins.mcph_chat_filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUtil {
	public static boolean failCharacterSpam(String message) {
		message = message.toLowerCase();

		int occurances = 0;
		char lastCharacter = 'a'; //Just start at a because I (Matt) is lazy.

		for (char curCharacter : message.toCharArray()) {
			if (curCharacter == lastCharacter) {
				occurances++;
			}
			lastCharacter = curCharacter;
		}

		double percentage = ((double) occurances / message.length())*100;
		return percentage >= 50 || message.length() <= 1;
	}



	public static boolean failCurse(String message) {
		message = message.toLowerCase();

		if (message.contains("fuck") || message.contains(" sucks ") || message.contains(" dick ") || message.contains(" shit ") || message.contains("nigger") || message.contains("[Owner]") || message.contains(" cunt ") || message.contains(" ass ")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean failLink(String message) {
		message = message.toLowerCase();

		if (message.contains("mcprohosting.com") || message.contains("antvenom.com")) {
			return false;
		} else if (message.contains("http") || message.contains(".com") || message.contains(".net") || message.contains(".org") || message.contains("www.") || message.contains(".xxx")) {
			return true;
		}

		return false;
	}

	public static boolean failIP(String message) {
		message = message.toLowerCase();

		String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
}
