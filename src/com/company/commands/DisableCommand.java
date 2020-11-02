//package com.company.commands;
//
//import java.util.ArrayList;
//
//public class DisableCommand extends Command {
//    private final Player player;
//    public DisableCommand(Player player) { this.player = player; }
//
//    public void execute(ArrayList<String> words) { player.enableDisable(words, false); }
//    public static String parseAgain() {
//        System.out.print("Disable what...?\n> ");
//        return Command.parseAgain().toUpperCase();
//    }
//}
