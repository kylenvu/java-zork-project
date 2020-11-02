package com.company.commands;

import java.io.*;
import java.util.*;

public class SaveCommand extends Command {

    public void execute(ArrayList<String> words) {
        String fileName;
        fileName = "src/com/company/" + parseAgain();
        File file = new File(fileName);
        try {
            FileWriter writer = new FileWriter(file);
            for (String s : TextParser.getQueueCommands()) {
                writer.write(s);
                writer.write(System.lineSeparator());
            }
            writer.close();
            System.out.printf("Game successfully saved to %s\n", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String parseAgain() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Save to what file...?\n> ");
        return scan.nextLine().trim();
    }
}
