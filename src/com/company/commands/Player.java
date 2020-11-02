package com.company.commands;

import com.company.Directions;
import com.company.items.*;
import com.company.rooms.*;
import java.util.*;
import java.lang.*;

abstract class Entity {
    protected String name;
    protected String description;
    protected Room currentRoom;
    protected int hitPoints;
    protected int damageDealt;
    public int getHitPoints() { return hitPoints; }
    public void setHitPoints(int damage) { hitPoints -= damage; }
}

public class Player extends Entity implements Directions {
    /* ==================================
                ATTRIBUTES
    ===================================== */
    private final int MAX_INVENTORY = 9;
    private final double MAX_HIT_POINTS;
    private final HashMap<String, Item> inventory = new HashMap<>();
    private int numOfItems = 0;
    private int numOfMoves = 0;
    private int score = 0;
    private boolean isCheating = false;
    private boolean isBrief = false;
    private boolean isSuperBrief = false;
    private boolean hasLight = false;

    /* =======================================
                    CONSTRUCTOR
    ========================================== */
    public Player(Maze maze, int roomNo) {
        currentRoom = maze.roomNo(roomNo);
        this.name = "You";
        this.description = "";
        this.hitPoints = 10;
        MAX_HIT_POINTS = hitPoints;
        this.damageDealt = 5;
    }

    /* ==================================
             ACCESSORS AND MUTATORS
    ===================================== */
    public Room getCurrentRoom() { return currentRoom; }
    public void setCurrentRoom(Room room) { currentRoom = room; }

    public boolean getCheatMode() { return isCheating; }
    public boolean getSuperBrief() { return isSuperBrief; }
    public boolean getBrief() { return isBrief; }

    public int getScore() { return score; }

    public void increaseScore(int amount) { score+= amount; }
    public void heal(int amount) {
        hitPoints+= amount;
        if (hitPoints > MAX_HIT_POINTS) hitPoints = (int)MAX_HIT_POINTS;
    }

    public boolean getHasLight() {
        for (Map.Entry<String, Item> entry : inventory.entrySet()) {
            if (entry.getValue() instanceof Light) {
                hasLight = true;
                break;
            }
        }
        return hasLight;
    }

    public void makeMove() { ++numOfMoves; }
    public int getNumOfMoves() { return numOfMoves; }

    public String damageMessage() {
        double currentHP = hitPoints;
        String message;
        if (currentHP == 10) message = "You are healthy.";
        else if (currentHP >= 7) message = "You are relatively healthy.";
        else if (currentHP >= 5) message = "You are moderately wounded.";
        else if (currentHP > 0) message = "You are mortally wounded.";
        else message = "You have died.";

        return message;
    }

    /* ==================================
                COMMANDS
    ===================================== */
    public void attack(String enemyName) {
        attack(enemyName, damageDealt);
    }
    public void attack(String enemyName, int damageDealt) {
        makeMove();
        Enemy theEnemy = currentRoom.getEnemy(enemyName);
        if (theEnemy != null) {
            if (damageDealt == this.damageDealt) {
                System.out.printf("You attacked the %s with your bare hands.\n", enemyName);
            }
            theEnemy.setHitPoints(damageDealt);
            if (theEnemy.getHitPoints() <= 0) {
                currentRoom.removeEnemy(theEnemy);
                System.out.printf("You defeated the %s.\n", enemyName);
            } else {
                System.out.printf("The %s attacked you in retaliation.\n", enemyName);
                setHitPoints(theEnemy.attack());
                System.out.println(damageMessage());
            }
        } else {
            System.out.printf("There is no %s in this room.\n", enemyName);
        }
    }
    public void attack(String enemyName, String itemName) {
        if (inventory.containsKey(itemName)) {
            if (inventory.get(itemName).attack() > 0)
                attack(enemyName, damageDealt + inventory.get(itemName).attack());
            else {
                System.out.printf("You cannot attack with %s.  You attack with your bare hands instead.\n", itemName);
                attack(enemyName);
            }
        } else {
            System.out.printf("You do not have a %s in your inventory.  You attack with your bare hands instead.\n", itemName);
            attack(enemyName);
        }
    }
    public void changeGameMode(String mode) {
        switch (mode) {
            case "CHEAT", "CHEATS" -> {
                isCheating = !isCheating;
                System.out.println((isCheating ? "Cheat mode enabled." : "Cheat mode disabled."));
            }
            case "SUPERBRIEF" -> {
                isSuperBrief = !isSuperBrief;
                isBrief = false;
                System.out.println(isSuperBrief ? "SuperBrief mode enabled." : "SuperBrief mode disabled.");
                if (!isSuperBrief) System.out.println("Verbose mode enabled by default.");
            }
            case "BRIEF" -> {
                isBrief = !isBrief;
                isSuperBrief = false;
                System.out.println(isBrief ? "Brief mode enabled." : "Brief mode disabled.");
                if (!isBrief) System.out.println("Verbose mode enabled by default.");
            }
            case "VERBOSE" -> {
                isBrief = false;
                isSuperBrief = false;
                System.out.println("Verbose mode enabled");
            }
            default -> System.out.println("There is no such mode as " + mode);
        }
    }
    public void diagnose() { System.out.println(damageMessage()); }
    public void displayInventory() {
        makeMove();
        if (inventory.isEmpty()) System.out.println("You're not carrying anything.");
        else {
            for (Map.Entry<String, Item> entry : inventory.entrySet()) {
                System.out.println("You have a " + entry.getValue().getHashKey());
            }
        }
    }
    public void drop(ArrayList<String> words) {
        makeMove();
        if (inventory.isEmpty()) {
            System.out.println("You have nothing to drop.");
        }
        String itemToDrop = "null";
        for (Map.Entry<String, Item> item : inventory.entrySet()) {
            if (!words.isEmpty()) itemToDrop = words.remove(0);
            if (itemToDrop.equals("ALL")) {
                currentRoom.getItems().putAll(inventory);
                inventory.clear();
                System.out.println("You dropped everything in your inventory.");
                numOfItems = 0;
            } else if (itemToDrop.equals(item.getKey())) {
                currentRoom.getItems().put(item.getKey(), item.getValue());
                inventory.remove(item.getKey());
                System.out.printf("You dropped %s.\n", item.getKey());
                --numOfItems;
            }
        }
    }
    public void go(ArrayList<String> words) {
        makeMove();
        heal(1);
        String word1;
        if (words.isEmpty()) word1 = GoCommand.parseAgain();
        else word1 = words.remove(0);

        while (!Directions.directionMap.containsKey(word1.toUpperCase())) {
            word1 = GoCommand.parseAgain();
        }
            Direction d = Directions.directionMap.get(word1.toUpperCase());
            if (currentRoom.getAllSides().get(d) != null) currentRoom.getAllSides().get(d).Enter(this);
            else System.out.println("There is nothing of importance there.");
    }
    public void look(ArrayList<String> words) { makeMove(); currentRoom.look(words, this); }
    public void open(Direction d) {
        MapSite mapSite = currentRoom.getSide(d);
        if (mapSite != null) {
            if (mapSite instanceof Door) {
                ((Door) mapSite).open();
            }
        } else {
            System.out.println("Nothing to open here.");
        }
    }
    public void open(String word) {
        boolean openSomething = false;
        if (word.equals("DOOR")) {
            int numOfDoors = 0;
            Direction d = null;
            for (Map.Entry<Direction, MapSite> entry : currentRoom.getAllSides().entrySet()) {
                if (entry.getValue() instanceof Door) {
                    d = entry.getKey();
                    ++numOfDoors;
                }
            }
            if (numOfDoors > 1) {
                Scanner scan = new Scanner(System.in);
                System.out.print("Which door do you want to unlock (specify direction)?\n> ");
                d = Direction.valueOf(scan.next().toUpperCase());
                open(d);
                openSomething = true;
            } else if (numOfDoors == 1){
                open(d);
                openSomething = true;
            } else {
                System.out.println("No doors here.");
            }
        } else {
            if (currentRoom.getItems().containsKey(word)) {
                Item theItem = currentRoom.getItems().get(word);
                if (theItem instanceof ItemWithItems) {
                    ((ItemWithItems) theItem).open();
                    openSomething = true;
                }
            } else {
                for (Map.Entry<Direction, MapSite> entry : currentRoom.getAllSides().entrySet()) {
                    if (entry.getValue().getName().equals(word) && entry.getValue() instanceof Door) {
                        ((Door) entry.getValue()).open();
                        openSomething = true;
                    }
                }
            }
        }
        if (!openSomething) System.out.println("Nothing to open here.");
    }
    public void read(String input) {
        if (inventory.isEmpty()) {
            System.out.println("You have nothing in your inventory.");
        } else if (inventory.get(input) == null) {
            System.out.println("You do not have " + input + " in your inventory.");
        } else {
            Item theItem = inventory.get(input);
            if (theItem instanceof ReadableItem) {
                ((ReadableItem) theItem).read();
            } else {
                System.out.println("You cannot read this.");
            }
        }
    }
    public void take(ArrayList<String> words) {
        makeMove();
        if (currentRoom.getItems().isEmpty()) {
            System.out.println("The room is empty.  There is nothing to take.");
        } else {
            String itemToTake;
            HashMap<String, Item> itemMap = currentRoom.getItems();

            if (words.isEmpty()) words.add(TakeCommand.parseAgain());
            while (!words.isEmpty()) {
                itemToTake = words.remove(0);
                if (itemMap.get(itemToTake) != null) {
                    Item takingItem = itemMap.get(itemToTake);
                    if (takingItem.getScore() > 0) {
                        increaseScore(takingItem.getScore());
                        System.out.println(takingItem.take());
                        itemMap.remove(itemToTake);
                    } else if (numOfItems < MAX_INVENTORY) {
                        if (takingItem.getCanPickUp()) {
                            ++numOfItems;
                            System.out.println(takingItem.take());
                            inventory.put(takingItem.getHashKey(), takingItem);
                            itemMap.remove(takingItem.getHashKey());
                        } else {
                            takingItem.take();
                        }
                    } else {
                        System.out.println("Your inventory is full.");
                        break;
                    }
                }
            }
        }
    }
    public void take(String word) {
        if (!currentRoom.getItems().isEmpty()) {
            if (word.equals("ALL")) {
                ArrayList<String> removedItems = new ArrayList<>();
                HashMap<String, Item> itemMap = currentRoom.getItems();
                for (Map.Entry<String, Item> entry : itemMap.entrySet()) {
                    if (entry.getValue().getScore() > 0) {
                        increaseScore(entry.getValue().getScore());
                        System.out.println(entry.getValue().take());
                        removedItems.add(entry.getKey());
                    } else if (numOfItems < MAX_INVENTORY) {
                        if (entry.getValue().getCanPickUp()) {
                            ++numOfItems;
                            inventory.put(entry.getKey(), entry.getValue());
                            System.out.println(entry.getValue().take());
                            removedItems.add(entry.getKey());
                        } else {
                            System.out.println(entry.getValue().take());
                        }
                    } else {
                        System.out.println("Your inventory is full.\n");
                        break;
                    }
                }
                for (String s : removedItems) {
                    itemMap.remove(s);
                }
            } else if (currentRoom.getItems().get(word) != null) {
                Item theItem = currentRoom.getItems().get(word);
                if (theItem.getScore() > 0 ) {
                    increaseScore(theItem.getScore());
                    System.out.println(theItem.take());
                    currentRoom.getItems().remove(word);
                } else if (numOfItems < MAX_INVENTORY) {
                    ++numOfItems;
                    inventory.put(word, theItem);
                    System.out.println(theItem.take());
                    currentRoom.getItems().remove(word);
                } else {
                    System.out.println("Your inventory is full.\n");
                }
            } else {
                for (Map.Entry<String, Item> entry : currentRoom.getItems().entrySet()) {
                    if (entry.getValue() instanceof ItemWithItems) {
                        if (entry.getValue().getInsideOfItem().containsKey(word)) {
                            Item theItem = entry.getValue().getInsideOfItem().get(word);
                            if (theItem.getScore() > 0) {
                                increaseScore(theItem.getScore());
                                System.out.println(theItem.take());
                                entry.getValue().getInsideOfItem().get(word);
                            } else if (numOfItems < MAX_INVENTORY) {
                                if (theItem.getCanPickUp()) {
                                    ++numOfItems;
                                    inventory.put(word, theItem);
                                    System.out.println(theItem.take());
                                    entry.getValue().getInsideOfItem().remove(word);
                                } else {
                                    System.out.println(theItem.take());
                                }
                            } else {
                                System.out.println("Your inventory is full.");
                            }
                        }
                    }
                }
            }
        }
    }
    public void take(String word1, String word3) {
        HashMap<String, Item> itemMap = currentRoom.getItems();
        ArrayList<String> removedItems = new ArrayList<>();
        if (itemMap.isEmpty()) {
            System.out.println("The room is empty, there is nothing to take.");
        } else {
            if (itemMap.get(word3) != null) {
                Item directObject = itemMap.get(word3);
                if (word1.equals("ALL")) {
                    for (Map.Entry<String, Item> entry : directObject.getInsideOfItem().entrySet()) {
                        if (entry.getValue().getScore() > 0) {
                            increaseScore(entry.getValue().getScore());
                            System.out.println(entry.getValue().take());
                            removedItems.add(entry.getKey());
                        } else if (numOfItems < MAX_INVENTORY) {
                            if (entry.getValue().getCanPickUp()) {
                                ++numOfItems;
                                inventory.put(entry.getKey(), entry.getValue());
                                System.out.println(entry.getValue().take());
                                removedItems.add(entry.getKey());
                            }
                        } else {
                            System.out.println("Your inventory is full.\n");
                        }
                    }
                    for (String s : removedItems) {
                        directObject.getInsideOfItem().remove(s);
                    }
                } else {
                    if (directObject.getInsideOfItem().get(word1) != null) {
                        Item indirectObject = directObject.getInsideOfItem().get(word1);
                        if (indirectObject.getScore() > 0) {
                            increaseScore(indirectObject.getScore());
                            System.out.println(indirectObject.take());
                            directObject.getInsideOfItem().remove(word1);
                        } else if (numOfItems < MAX_INVENTORY) {
                            if (indirectObject.getCanPickUp()) {
                                ++numOfItems;
                                inventory.put(indirectObject.getHashKey(), indirectObject);
                                System.out.println(indirectObject.take());
                                directObject.getInsideOfItem().remove(word1);
                            } else {
                                indirectObject.take();
                            }
                        } else {
                            System.out.println("Your inventory is full.\n");
                        }
                    }
                }
            }
        }
    }
    public void unlock(String word1, String word3) {
        makeMove();
        boolean hasLockedDoor = false;
        boolean hasUnlockItem = false;
        if (!currentRoom.getItems().isEmpty()) {
//            System.out.println("Room is not empty");
            if (currentRoom.getItems().containsKey(word1)) {
                Item theItem = currentRoom.getItems().get(word1);
//                System.out.println("Room has " + word1);
                if (theItem instanceof ItemWithItems) {
                    hasUnlockItem = true;
                    if (inventory.containsKey(word3)) {
                        if (inventory.get(word3) instanceof Key) {
//                            System.out.println("Final stretch");
                            ((ItemWithItems) theItem).unlock(((Key) inventory.get(word3)).getID());
                        } else {
                            System.out.println("You cannot unlock with this item.");
                        }
                    } else {
                        System.out.println("You do not have that item.");
                    }
                } else {
                    System.out.println("You cannot unlock this.");
                }
            }
        }
        if (inventory.containsKey(word3)) {
            for (Map.Entry<Direction, MapSite> entry : currentRoom.getAllSides().entrySet()) {
                if (inventory.get(word3) instanceof Key) {
                    if (entry.getValue() instanceof DoorWithKey && entry.getValue().getName().equals(word1)) {
                        ((DoorWithKey) entry.getValue()).unlock(((Key) inventory.get(word3)).getID());
                        hasLockedDoor = true;
                    }
                } else {
                    System.out.println("You cannot unlock with this item.");
                }
            }
        } else {
            System.out.printf("You do not have a %s in your inventory.\n", word3);
        }
        if (!hasLockedDoor && !hasUnlockItem) {
            System.out.println("There's nothing to unlock here.");
        }
    }
    public void unlock(Direction d, String word3) {
        makeMove();
        MapSite mapSite = currentRoom.getSide(d);
        if (mapSite != null) {
            if (mapSite instanceof DoorWithKey) {
                if (inventory.containsKey(word3)) {
                    if (inventory.get(word3) instanceof Key) {
                        ((DoorWithKey) mapSite).unlock(((Key) inventory.get(word3)).getID());
                    }
                } else {
                    System.out.printf("You do not have a %s in your inventory.\n", word3);
                }
            } else {
                System.out.println("There is no locked door here.");
            }
        }
    }
}
