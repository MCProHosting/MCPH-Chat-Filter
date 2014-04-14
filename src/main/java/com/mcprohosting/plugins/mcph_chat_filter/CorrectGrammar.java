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

            double percentage = ((double) failedCount / letterCount) * 100;
            return percentage >= 30;
        }

        return false;
    }
}
