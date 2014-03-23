package com.mcprohosting.plugins.mcph_chat_filter;

public class CorrectGrammar {
	public static String correctGrammar(String message) {
		message = correctBasics(message);
		message = fullStop(message);
		message = capitalise(message);

		return message;
	}

	private static String correctBasics(String message) {
		if (message.contains(" i ")) {
			message = message.replace(" i ", " I ");
		}

		if (message.toLowerCase().contains(" im ") || message.toLowerCase().contains("isnt") || message.contains("dont")) {
			message = message.replace(" im ", " I'm ");
			message = message.replace("isnt", "isn't");
			message = message.replace("dont", "don't");
		}

		if (message.toLowerCase().contains("antvenoms") || message.toLowerCase().contains("matts")) {
			message = message.replace("antvenoms", "AntVenom's");
			message = message.replace("matts", "Matt's");
		}

		if (message.contains("matt") || message.contains("antvenom") || message.contains(" ant")) {
			message = message.replace("matt", "Matt");
			message = message.replace("antvenom", "AntVenom");
			message = message.replace(" ant", " AntVenom");
		}

		if (message.contains("lol") || message.contains("nvm") || message.contains("brb") || message.contains("gtg") | message.contains("omg")) {
			message = message.replace("lol", "LOL");
			message = message.replace("nvm", "NVM");
			message = message.replace("brb", "BRB");
			message = message.replace("gtg", "GTG");
			message = message.replace("omg", "OMG");
		}

		if (message.toLowerCase().contains(" u ") || message.toLowerCase().contains(" r ")) {
			message = message.replace(" u ", " you ");
			message = message.replace(" r ", " are ");
		} else if (message.startsWith("u ")) {
			message = message.replace("u ", "You ");
		}

		if (message.toLowerCase().contains("youtube")) {
			message = message.replace("youtube", "YouTube");
			message = message.replace("Youtube", "YouTube");
		}

		if (message.contains("alot")) {
			message = message.replace("alot", "a lot");
		}

		if (message.toLowerCase().contains(" ip ")) {
			message = message.replace(" ip ", " IP ");
		}

		return message;
	}

	private static String capitalise(String message) {
		if (message.contains(" ")) {
			String[] sentences = message.split("(?<=[!?\\.])\\s");
			String tempMessage = "";

			for (String sentence : sentences) {
				sentence = sentence.substring(0, 1).toUpperCase() + sentence.substring(1);

				tempMessage += sentence + " ";
			}

			return tempMessage.trim();
		} else {
			return message;
		}
	}

	private static String fullStop(String message) {
		if (message.contains(" ")) {
			String lastChar = message.substring(message.length() - 1);

			if (lastChar.matches("(?i)[a-z]")) {
				if (message.toLowerCase().contains("where to") || message.contains("how do") || message.contains("why") || message.contains("are you") || message.contains("who here") || message.toLowerCase().contains("what is") || message.toLowerCase().contains("can you") || message.toLowerCase().contains("how long") || message.toLowerCase().contains("how did") || message.toLowerCase().contains("how soon") || message.toLowerCase().contains("who is") || message.toLowerCase().contains("when will") || message.toLowerCase().contains(" where ") || message.toLowerCase().contains("remember ") || message.toLowerCase().contains("is this")) {
					message += "?";
				} else {
					message += ".";
				}
			}
		}

		return message;
	}
}
