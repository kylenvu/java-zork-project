package com.company.commands;

import com.company.Directions;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TextParser implements Directions {
    /* ======================================
                    ATTRIBUTES
    ========================================= */
    private final HashMap<String, Command> validCommands = new HashMap<>();
    private final ArrayList<String> validEnemies = new ArrayList<>();
    private final ArrayList<String> validAdjectives = new ArrayList<>();
    private final ArrayList<String> validItems = new ArrayList<>();
    private final static ArrayList<String> validPrepositions = new ArrayList<>();
    private final static Queue<String> queueCommands = new LinkedList<>();
    /* =====================================
                Game Mode Array List
    ======================================== */
    private final ArrayList<String> validGameModes = createValidGameModes();
    private ArrayList<String> createValidGameModes() {
        ArrayList<String> list = new ArrayList<>();
        list.add("CHEAT");
        list.add("CHEATS");
        list.add("VERBOSE");
        list.add("BRIEF");
        list.add("SUPERBRIEF");
        return list;
    }
    private boolean isQuitting;

    /* =======================================
                CONSTRUCTOR
    ========================================== */
    public TextParser() { }

    /* ======================================
                ACCESSORS & MUTATORS
    ========================================= */
    public ArrayList<String> getList(String listName) {
        return switch (listName.toLowerCase()) {
            case "enemies" -> validEnemies;
            case "adjectives" -> validAdjectives;
            case "items" -> validItems;
            case "prepositions" -> validPrepositions;
            default -> null;
        };
    }
    public static ArrayList<String> getValidPrepositions() { return validPrepositions; }
    public static Queue<String> getQueueCommands() { return queueCommands; }

    /* ======================================
                ADD DICTIONARIES
    ========================================= */
    public boolean userIsQuitting() { return isQuitting; }
    public void addCommand(String word, Command command) {
        if (!validCommands.containsKey(word)) validCommands.put(word, command);
    }
    public void addDictionary(String fileName, Command command) {
        Path filePath = Paths.get("src/com/company/" + fileName);
        try {
            InputStream fileInput = Files.newInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
            String s;
            while ((s = reader.readLine()) != null) {
                validCommands.put(s, command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addDictionary(String fileName, ArrayList<String> list) {
        Path filePath = Paths.get("src/com/company/" + fileName);
        try {
            InputStream fileInput = Files.newInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
            String s;
            while ((s = reader.readLine()) != null) list.add(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ========================================
                    PARSE METHODS
    =========================================== */
    public void parse(String input) {
        boolean hasCommand = false;
        String[] sentenceArray = input.trim().split("\\s");
        String firstWord = sentenceArray[0].toUpperCase();
        if (firstWord.equals("QUIT") || firstWord.equals("Q")) {
            System.out.println("Quitting game...");
            isQuitting = true;
        } else {
            if (!firstWord.equals("SAVE") && !firstWord.equals(" ")) {
                queueCommands.add(input);
            }
            ArrayList<String> validWords = new ArrayList<>();
            Command command = null;
            String word;
            for (int i = 0; i < sentenceArray.length; ++i) {
                word = sentenceArray[i].toUpperCase();
                if (!hasCommand) {
                    if (validCommands.containsKey(word)) {
                        if (Directions.directionMap.containsKey(word))
                            validWords.add(word);
                        command = validCommands.get(word);
                        hasCommand = true;
                    }
                }
                if (validAdjectives.contains(word)) {
                    if (i + 1 < sentenceArray.length) {
                        validWords.add(word + " " + sentenceArray[i+1].toUpperCase());
                        ++i;
                    } else {
                        validWords.add(word + " " + parseItemAgain(word));
                    }
                } else if (validItems.contains(word)) {
                    validWords.add(word);
                } else if (Directions.directionMap.containsKey(word)) {
                    validWords.add(word);
                } else if (validGameModes.contains(word)) {
                    validWords.add(word);
                } else if (validEnemies.contains(word)) {
                    validWords.add(word);
                } else if (validPrepositions.contains(word)) {
                    validWords.add(word);
                }
            }
            if (!hasCommand) {
                System.out.printf("Sorry, I don't know the command %s.\n", sentenceArray[0]);
            } else {
                command.execute(validWords);
            }
        }
    }
    public String parseItemAgain(String adjective) {
        String word = "NotAnItem";
        while (!validItems.contains(word.toUpperCase())) {
            System.out.printf("%s what...?\n> ", adjective);
            Scanner scan = new Scanner(System.in);
            word = scan.next().toUpperCase();
        }
        return word;
    }
}
