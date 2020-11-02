package com.company.commands;

import java.util.*;

public class GameModeCommand extends Command {
    private final Player player;
    private final String commandType;
    public GameModeCommand(Player player, String commandType) {
        this.player = player;
        this.commandType = commandType;
    }
    public void execute(ArrayList<String> words) {
        player.changeGameMode(commandType);
    }
}
