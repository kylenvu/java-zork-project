package com.company.commands;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LoadCommand extends Command {
    private final TextParser parser;
    public LoadCommand(TextParser parser) { this.parser = parser; }
    public void execute(ArrayList<String> words) {
        Scanner scan = new Scanner(System.in);
        System.out.print("What is the file name of your previous save?\n> ");
        String fileName = "src/com/company/" + scan.next();
        Path filePath = Path.of(fileName);
        if (filePath.toFile().exists()) {
            try {
                InputStream fileInput = Files.newInputStream(filePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
                String s;
                while ((s = reader.readLine()) != null) {
                    parser.parse(s);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("%s successfully loaded.\n", fileName);
        } else {
            System.out.println("Previous save could not be loaded.  Please check that file exists.");
        }
    }
    public void execute(String fileName) {
        Path filePath = Path.of(fileName);
        if (filePath.toFile().exists()) {
            try {
                InputStream fileInput = Files.newInputStream(filePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
                String s;
                while ((s = reader.readLine()) != null) {
                    parser.parse(s);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("%s successfully loaded.\n", fileName);
        } else {
            System.out.println("Previous save could not be loaded.  Please check that file exists.");
        }
    }
}
