package com.company.commands;

import java.util.ArrayList;

public class DiagnoseCommand extends Command {
    private final Player player;
    public DiagnoseCommand(Player player) { this.player = player; }
    public void execute(ArrayList<String> words) { player.diagnose(); }
}
