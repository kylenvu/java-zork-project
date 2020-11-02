package com.company;

import com.company.commands.*;
import com.company.rooms.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int WINNING_SCORE = 500;

        MazeGame mazeGame = new MazeGame();
        StandardMazeBuilder mazeBuilder = new StandardMazeBuilder();
        Maze theMaze = mazeGame.createMaze(mazeBuilder);
        Player player = new Player(theMaze, 0);
        // Make Text Parser
        TextParser parser = new TextParser();
        // Make Commands
        Command attackCommand = new AttackCommand(player);
        Command briefCommand = new GameModeCommand(player, "BRIEF");
        Command cheatCommand = new GameModeCommand(player, "CHEAT");
        Command diagnoseCommand = new DiagnoseCommand(player);
//        Command disableCommand = new DisableCommand(player);
        Command dropCommand = new DropCommand(player);
//        Command enableCommand = new EnableCommand(player);
        Command goCommand = new GoCommand(player);
        Command inventoryCommand = new InventoryCommand(player);
        LoadCommand loadCommand = new LoadCommand(parser);
        Command lookCommand = new LookCommand(player);
        Command openCommand = new OpenCommand(player);
        Command readCommand = new ReadCommand(player);
        Command saveCommand = new SaveCommand();
        Command superBriefCommand = new GameModeCommand(player, "SUPERBRIEF");
        Command takeCommand = new TakeCommand(player);
        Command unlockCommand = new UnlockCommand(player);
        Command verboseCommand = new GameModeCommand(player, "VERBOSE");
        // Testing Only
        parser.addCommand("ATTACK", attackCommand);
        parser.addCommand("BRIEF", briefCommand);
        parser.addCommand("CHEAT", cheatCommand);
        parser.addCommand("CHEATS", cheatCommand);
        parser.addCommand("DIAGNOSE", diagnoseCommand);
//        parser.addCommand("DISABLE", disableCommand);
        parser.addCommand("DROP", dropCommand);
//        parser.addCommand("ENABLE", enableCommand);
        parser.addCommand("INVENTORY", inventoryCommand);
        parser.addCommand("I", inventoryCommand);
        parser.addCommand("LOOK", lookCommand);
        parser.addCommand("L", lookCommand);
        parser.addCommand("OPEN", openCommand);
        parser.addCommand("READ", readCommand);
        parser.addCommand("SAVE", saveCommand);
        parser.addCommand("SUPERBRIEF", superBriefCommand);
        parser.addCommand("TAKE", takeCommand);
        parser.addCommand("UNLOCK", unlockCommand);
        parser.addCommand("VERBOSE", verboseCommand);

        // Adding Dictionaries
        parser.addDictionary("go.txt", goCommand);
        parser.addDictionary("adjectives.txt", parser.getList("adjectives"));
        parser.addDictionary("items.txt", parser.getList("items"));
        parser.addDictionary("prepositions.txt", parser.getList("prepositions"));
        parser.addDictionary("enemies.txt", parser.getList("enemies"));
        // Starting game

        String input;
        Scanner scan = new Scanner(System.in);
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.print("\nWould you like to load a previous save file? (Y/N)\n");
            System.out.println("Note: You can only load a previous save at the start of the game.");
            System.out.print("OR You can enter \"win\" to automatically win the game.\n> ");
            input = scan.next();
            input = input.toUpperCase();
            switch (input) {
                case "Y" -> {
                    loadCommand.execute(new ArrayList<>());
                    isValidInput = true;
                }
                case "N" -> {
                    isValidInput = true;
                    theMaze.roomNo(0).initialLook(player);
                }
                case "WIN" -> {
                    loadCommand.execute("src/com/company/win.txt");
//                    System.out.println("WIN command has not been implemented yet.");
                    isValidInput = true;
                }
                default -> System.out.println("Please enter a valid option.");
            }
        }
        scan.nextLine();
        while (!parser.userIsQuitting() && player.getScore() < WINNING_SCORE && player.getHitPoints() > 0) {
            player.getHasLight();
            System.out.printf("\nNo. of moves: %d %7s %3d %12s %s",
                    player.getNumOfMoves(),
                    "Score:", player.getScore(),
                    "Location: ", player.getCurrentRoom().getName());
            System.out.print("\n> ");
            input = scan.nextLine();
            parser.parse(input);
        }
        if (player.getScore() >= WINNING_SCORE) {
            System.out.println("Congratulations!  You have beat the game!");
        }
    }
}
