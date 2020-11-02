package com.company.commands;

import com.company.Directions;

import java.util.ArrayList;

public class UnlockCommand extends Command implements Directions{
    private final Player player;
    public UnlockCommand(Player player) { this.player = player; }
    public void execute(ArrayList<String> words) {
        String word1;
        if (words.isEmpty()) word1 = parseAgain();
        else word1 = words.remove(0);
        String word2;
        String word3;
        if (!words.isEmpty()) {
            word2 = words.remove(0);
            if (TextParser.getValidPrepositions().contains(word2)) {
                if (words.isEmpty()) {
                    word3 = parseForDirectObject(word2);
                } else {
                    word3 = words.remove(0);
                }

            } else {
                word3 = parseForDirectObject("WITH");
            }
        } else {
            word3 = parseForDirectObject("WITH");
        }

        if (Directions.directionMap.containsKey(word1)) {
            Direction d = Direction.valueOf(word1);
            player.unlock(d, word3);
        } else {
            player.unlock(word1, word3);
        }
    }
    public static String parseAgain() {
        System.out.print("Unlock the what...?\n> ");
        return Command.parseAgain();
    }
    public static String parseForDirectObject(String word) {
        System.out.printf("Unlock %s what...?\n> ", word);
        return Command.parseAgain();
    }
}
