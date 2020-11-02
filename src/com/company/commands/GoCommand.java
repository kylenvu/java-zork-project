package com.company.commands;

import com.company.Directions;

import java.util.ArrayList;

public class GoCommand extends Command implements Directions {
    private final Player player;

    public GoCommand(Player player) {
        this.player = player;
    }

    public void execute(ArrayList<String> words) {
        player.go(words);
    }
    public static String parseAgain() {
        System.out.print("Go where...?\n> ");
        return Command.parseAgain();
    }
}
