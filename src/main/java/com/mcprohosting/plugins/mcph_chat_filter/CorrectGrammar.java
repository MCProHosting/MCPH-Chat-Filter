package com.mcprohosting.plugins.mcph_chat_filter;

import java.util.ArrayList;
import java.util.HashMap;

public class CorrectGrammar {
	private static HashMap<String, String> corrections = new HashMap<String, String>();
	private static ArrayList<String> questions = new ArrayList<String>();

	public static String correctGrammar(String message) {
		if (isCapsMessage(message)) {
			message = message.toLowerCase();
		}

		message = correctBasics(message);
		message = fullStop(message);
		message = capitalise(message);

		return message;
	}

	static {
		//Contractions
		corrections.put(" i ", " I ");
		corrections.put(" im ", " I'm ");
		corrections.put(" id ", " I'd ");
		corrections.put(" ive ", " I've ");
		corrections.put(" ill ", " I'll ");
		corrections.put("isnt", "isn't");
		corrections.put("dont", "don't");
		corrections.put("cant", "can't");
		corrections.put("wont", "won't");
		corrections.put(" weve ", " we've ");
		corrections.put("shouldnt", "shouldn't");
		corrections.put("wouldnt", "wouldn't");
		corrections.put("arent", "aren't");
		corrections.put("didnt", "didn't");
		corrections.put("wheres", "where's");

		//Names
		corrections.put("antvenom", "AntVenom");
		corrections.put("Antvenom", "AntVenom");
		corrections.put("antVenom", "AntVenom");
		corrections.put(" ant ", " Ant ");
		corrections.put("matt", "Matt");

		//Possessive Names
		corrections.put("AntVenoms", "AntVenom's");
		corrections.put("Ants", "Ant's");
		corrections.put("Matts", "Matt's");

		//Acronyms
		corrections.put("lol", "LOL");
		corrections.put("nvm", "NVM");
		corrections.put("brb", "BRB");
		corrections.put("gtg", "GTG");
		corrections.put("omg", "OMG");
		corrections.put(" ip ", " IP ");

		//Idiot kid protection
		corrections.put(" u ", " you ");
		corrections.put(" r ", " are ");

		//Particular capitalization structure of stuff
		corrections.put("youtube", "YouTube");
		corrections.put("Youtube", "YouTube");
		corrections.put("utube", "YouTube");
		corrections.put("mc pro hosting", "MCProHosting");
		corrections.put("MC Pro Hosting", "MCProHosting");
		corrections.put("McProHosting", "MCProHosting");
		corrections.put("mcprohosting", "MCProHosting");
		corrections.put("minecraft", "Minecraft");

		//Alot of grammar: http://hyperboleandahalf.blogspot.com/2010/04/alot-is-better-than-you-at-everything.html
		corrections.put("alot", " a lot");


		//Questions
		questions.add("are you");
		questions.add("do you");
		questions.add("can you");
		questions.add("how do");
		questions.add("how long");
		questions.add("how did");
		questions.add("how soon");
		questions.add("how are");
		questions.add("who here");
		questions.add("why");
		questions.add("who is");
		questions.add("what is");
		questions.add("when will");
		questions.add("where");
		questions.add("where's");


		questions.add("is this");
	}

	private static String correctBasics(String message) {
		for (String key : corrections.keySet()) {
			if (message.contains(key)) {
				message = message.replace(key, corrections.get(key));
			}
		}

		if (message.startsWith("im")) {
			message = "I'm" + message.substring(2);
		}

		return message;
	}

	private static String capitalise(String message) {
		String[] sentences = message.split("(?<=[!?\\.])\\s");
		String tempMessage = "";

		for (String sentence : sentences) {
			sentence = sentence.substring(0, 1).toUpperCase() + sentence.substring(1);

			tempMessage += sentence + " ";
		}

		return tempMessage.trim();
	}

	private static String fullStop(String message) {
		if (message.contains(" ")) {
			String lastChar = message.substring(message.length() - 1);
			boolean wasQuestion = false;

			if (!(lastChar.equals("?") || lastChar.equals("!") || lastChar.equals("."))) {
				for (String questionText : questions) {
					if (message.contains(questionText)) {
						wasQuestion = true;
						break;
					}
				}

				if (wasQuestion) {
					message += "?";
				} else {
					message += ".";
				}
			}
		}

		return message;
	}

	public static boolean isCapsMessage(String message) {
		if (message.length() >= 4) {
			int letterCount = 0;
			int failedCount = 0;

			for (Character c : message.toCharArray()) {
				if (Character.isLetter(c)) {
					letterCount++;
					if (Character.isUpperCase(c)) {
						failedCount++;
					}
				}
			}

			double percentage = ((double) failedCount / letterCount)*100;
			return percentage >= 30;
		}

		return false;
	}
}
