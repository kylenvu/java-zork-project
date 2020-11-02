package com.company.commands;

import com.company.Directions;

import java.util.ArrayList;

public class OpenCommand extends Command implements Directions{
    private final Player player;
    public OpenCommand(Player player) { this.player = player; }
    public void execute(ArrayList<String> words) {
        String word1;
        if (words.isEmpty()) word1 = parseAgain();
        else word1 = words.remove(0);

        if (Directions.directionMap.containsKey(word1)) {
            Direction d = Direction.valueOf(word1);
            player.open(d);
        } else {
            player.open(word1);
        }
    }
    public static String parseAgain() {
        System.out.print("Open what...?\n> ");
        return Command.parseAgain();
    }
}
