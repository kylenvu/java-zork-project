package com.company.commands;

import java.util.ArrayList;

public class ReadCommand extends Command {
    private final Player player;
    public ReadCommand(Player player) { this.player = player; }
    public void execute(ArrayList<String> words) {
        String input;
        if (words.isEmpty()) input = parseAgain();
        else input = words.remove(0);
        player.read(input);
    }

    public static String parseAgain() {
        System.out.print("Read what...?\n> ");
        return Command.parseAgain();
    }
}
