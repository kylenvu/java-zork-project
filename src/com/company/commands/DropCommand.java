package com.company.commands;

import java.util.ArrayList;

public class DropCommand extends Command {
    private final Player player;

    public DropCommand(Player player) {
        this.player = player;
    }

    public void execute(ArrayList<String> words) {
        if (words.isEmpty()) {
            words.add(parseAgain());
        } else {
            player.drop(words);
        }
    }
    public static String parseAgain() {
        System.out.print("Drop what...?\n> ");
        return Command.parseAgain().toUpperCase();
    }
}
