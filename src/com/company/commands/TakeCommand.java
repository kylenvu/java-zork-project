package com.company.commands;

import java.util.ArrayList;

public class TakeCommand extends Command {
    private final Player player;

    public TakeCommand(Player player) {
        this.player = player;
    }

    public void execute(ArrayList<String> words) {
        String word1;
        if (words.isEmpty()) word1 = parseAgain();
        else word1 = words.remove(0);

        String word2;
        String word3;
        if (!words.isEmpty()) {
            word2 = words.remove(0);
            if (TextParser.getValidPrepositions().contains(word2)) {
                if (!words.isEmpty()) word3 = words.remove(0);
                else word3 = parseForDirectObject(word2);
                player.take(word1, word3);
            } else {
                words.add(word1);
                words.add(word2);
                player.take(words);
            }
        } else {
            player.take(word1);
        }
    }
    public static String parseAgain() {
        System.out.print("Take what...\n> ");
        return Command.parseAgain().toUpperCase();
    }
    public static String parseForDirectObject(String word) {
        System.out.printf("Take %s what...?\n> ", word);
        return Command.parseAgain();
    }
}
