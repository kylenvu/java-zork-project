package com.company.commands;

import java.util.*;

public class AttackCommand extends Command {
    private final Player player;

    public AttackCommand(Player player) { this.player = player; }
    public void execute(ArrayList<String> words) {
        String word1;

        if (words.isEmpty()) word1 = parseAgain();
        else word1 = words.remove(0);

        if (!words.isEmpty()) {
            String word2 = words.remove(0);
            if (TextParser.getValidPrepositions().contains(word2)) {
                String word3;
                if (words.isEmpty()) {
                    word3 = parseForDirectObject(word2);
                } else {
                    word3 = words.remove(0);
                }
                player.attack(word1, word3);
            }
        } else {
            player.attack(word1);
        }
    }
    public static String parseAgain() {
        System.out.print("Attack what...?\n> ");
        return Command.parseAgain();
    }
    public String parseForDirectObject(String word) {
        System.out.printf("Attack %s what...?\n> ", word);
        return Command.parseAgain();
    }
}
