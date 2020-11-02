package com.company.commands;

import java.util.ArrayList;

public class LookCommand extends Command {
    private final Player player;

    public LookCommand(Player player) {
        this.player = player;
    }

    public void execute(ArrayList<String> words) {
        player.look(words);
    }
}
