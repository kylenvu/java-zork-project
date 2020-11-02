package com.company.commands;

import java.util.*;

public abstract class Command {
    public abstract void execute(ArrayList<String> words);
    public static String parseAgain() {
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine().trim();
        TextParser.getQueueCommands().add(word);
        return word.toUpperCase();
    }
}
