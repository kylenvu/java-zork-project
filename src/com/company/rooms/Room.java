package com.company.rooms;

import com.company.Directions;
import com.company.commands.*;
import com.company.items.*;

import java.lang.*;
import java.util.*;

public class Room extends MapSite implements Directions {

    /* =================================
                Attributes
    ================================== */
    private final int roomNo;
    private final String name;
    private final String description;
    private final String strCheats;
    private final HashMap<String, Item> items = new HashMap<>(); // Need to implement Item class
    private final HashMap<Direction, MapSite> sides = new HashMap<>();
    private final HashMap<String, Enemy> enemiesInRoom = new HashMap<>();
    private final boolean isDark;



    /* =================================
                Constructors
    ================================== */
    private Room(RoomBuilder builder) {
        roomNo = builder.roomNo;
        name = builder.name;
        description = builder.description;
        strCheats = builder.strCheats;
        sides.putAll(builder.sides);
        enemiesInRoom.putAll(builder.enemiesInRoom);
        isDark = builder.isDark;
    }


    /* =================================
            ACCESSORS & MUTATORS
    ================================== */

    public void Enter(Player player) {
        if (isDark) {
            if (player.getHasLight()) {
                player.setCurrentRoom(this);
                initialLook(player);
            } else {
                System.out.println("It's too dark.  You cannot go there without a light.");
            }
        } else {
            player.setCurrentRoom(this);
            initialLook(player);
        }
    }

    public void setSide(Direction direction, MapSite mapSite) {
        if (sides.get(direction) != null) sides.replace(direction, mapSite);
        sides.put(direction, mapSite);
    }

    public MapSite getSide(Direction direction) {
        return sides.get(direction);
    }

    public int roomNo() {
        return roomNo;
    }

    public Enemy getEnemy(String theEnemy) {
        Enemy returnedEnemy;
        returnedEnemy = enemiesInRoom.get(theEnemy);
        return returnedEnemy;
    }
    public void removeEnemy(Enemy enemy) {
        enemiesInRoom.remove(enemy.getName());
    }
    public HashMap<Direction, MapSite> getAllSides() { return sides; }
    public HashMap<String, Item> getItems() { return items; }
    public String getName() { return name; }

    /* ============================================
            toSTRING & LOOK METHODS
    =============================================== */
    public String toString() {
        return String.format("%s...%s", name, description);
    }

    public void initialLook(Player player) {
        if (player.getCheatMode()) System.out.printf("Hints: %s\n", strCheats);
        if (player.getSuperBrief()) System.out.printf("You entered...%s\n", name);
        else if (player.getBrief()) System.out.printf("You entered...%s\n", this);
        else {
            lookSides();
            lookItems();
            lookEnemies();
        }
    }
    public void look(ArrayList<String> words, Player player) {
        if (words.isEmpty()) {
            System.out.println("Inside empty look command");
            if (player.getCheatMode()) System.out.printf("Hints: %s\n", strCheats);
            lookSides();
            lookItems();
            lookEnemies();
        } else {
            String word1 = words.remove(0);
            if (TextParser.getValidPrepositions().contains(word1)) {
                 String word2;
                 if (words.isEmpty()) {
                     word2 = LookCommand.parseAgain();
                 } else {
                     word2 = words.remove(0);
                 }
                 lookItems(word2);
            } else {
                lookItems(word1);
            }
        }
    }
    public void lookSides() {
        System.out.printf("You are in...%s\n", this);
        for (Map.Entry<Direction, MapSite> entry : sides.entrySet()) {
            if (!(entry.getValue() instanceof Wall))
                System.out.printf("To the %s, %s\n", entry.getKey(), entry.getValue());
        }
    }
    public void lookItems() {
        if (!items.isEmpty()) {
            System.out.println("In the room, there is a...");
            for (Map.Entry<String, Item> entry : items.entrySet()) {
                System.out.println(entry.getValue().inspect());
            }
        } else {
            System.out.println("The room has no items.");
        }
    }
    public void lookItems(String item) {
        if (!items.isEmpty()) {
            System.out.print("In the room, there is a ");
            for (Map.Entry<String, Item> entry : items.entrySet()) {
                if (item.equals(entry.getKey())) {
                    System.out.println(entry.getValue().inspect());
                }
            }
        } else {
            System.out.printf("There is no %s in the room.\n", item);
        }
    }
    public void lookEnemies() {
        if (!enemiesInRoom.isEmpty()) {
            for (Map.Entry<String, Enemy> entry : enemiesInRoom.entrySet()) {
                System.out.printf("There is a %s.\n", entry.getValue().toString());
            }
        }
    }

    /* =====================================
            ROOM BUILDER CLASS
    ======================================== */
    public static class RoomBuilder {
        private final int roomNo;
        private final String name;
        private final String description;
        private String strCheats = "None for this room.";
        private final HashMap<Direction, MapSite> sides = new HashMap<>();
        private final HashMap<String, Enemy> enemiesInRoom = new HashMap<>();
        private boolean isDark = false;


        public RoomBuilder(int roomNo, String name, String description) {
            this.roomNo = roomNo;
            this.name = name;
            this.description = description;
        }
        public RoomBuilder setCheatString(String strCheats) { this.strCheats = strCheats; return this;}
        public RoomBuilder side() {
            sides.put(Direction.NORTH, new Wall());
            sides.put(Direction.SOUTH, new Wall());
            sides.put(Direction.EAST, new Wall());
            sides.put(Direction.WEST, new Wall());
            return this;
        }
        public RoomBuilder side(Direction direction, MapSite mapSite) {

            if (sides.get(direction) == null) sides.put(direction, mapSite);
            else sides.replace(direction, mapSite);
            return this;
        }
        public RoomBuilder enemy(Enemy enemy) {
            this.enemiesInRoom.put(enemy.getName(), enemy);
            return this;
        }
        public RoomBuilder setDark() {
            this.isDark = true;
            return this;
        }
        public Room build() { return new Room(this); }
    }
}
