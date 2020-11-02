package com.company.rooms;

import com.company.commands.*;
import com.company.Directions;
import com.company.items.*;
//
//import java.util.ArrayList;
//import java.util.Map;

public class MazeGame implements Directions {
    public Maze createMaze(MazeBuilder builder) {
        builder.buildMaze();

        // Make Enemies
        Enemy troll = new Enemy("TROLL", "It looks very strong", 15, 30);
        Enemy grue = new Enemy("GRUE", "It looks very hefty and strong", 15, 30);
        // Make Rooms
        Room westOfHouse =
                new Room
                        .RoomBuilder(0, "WEST OF HOUSE", "An open field west of a white house, with a boarded front door.")
                        .setCheatString("(Go south)")
                        .side().build();

        Room livingRoom = new Room.RoomBuilder(1, "LIVING ROOM", "The living room of the house.")
                .setCheatString("(Go into the forest to find a key for the chest) [Do not go DOWN until you have the sword]")
                .side().side(Direction.WEST, westOfHouse).build();

        Room kitchenRoom = new Room.RoomBuilder(2, "KITCHEN", "The kitchen of the house.")
                .side().side(Direction.WEST, livingRoom).build();

        Room atticRoom = new Room.RoomBuilder(3, "ATTIC", "The attic.  It is not dark in here.")
                .side().side(Direction.DOWN, kitchenRoom).setDark().build();

        Room southOfHouse = new Room.RoomBuilder(4, "SOUTH OF HOUSE", "An open field south of the house.")
                .setCheatString("(Go east)").side().side(Direction.WEST, westOfHouse).build();

        Room behindHouse = new Room.RoomBuilder(5, "BEHIND HOUSE", "An open field behind the house.")
                .setCheatString("(Open window and GO WEST)")
                .side().side(Direction.SOUTH, southOfHouse).side(Direction.WEST, kitchenRoom).build();

        Room northOfHouse = new Room.RoomBuilder(6, "NORTH OF HOUSE", "An open field north of the house")
                .side().side(Direction.WEST, westOfHouse).side(Direction.EAST, behindHouse).build();

        Room forest1 = new Room.RoomBuilder(7, "DENSE FOREST", "A dense forest full of trees.")
                .side().setCheatString("(Forest 1) [Just don't even bother trying to navigate the forest]").build();

        Room clearing = new Room.RoomBuilder(8, "CLEARING", "An open clearing.")
                .side().side(Direction.SW, behindHouse).build();

        Room forest2 = new Room.RoomBuilder(9, "DENSE FOREST", "A dense forest full of trees.")
                .side().side(Direction.NE, clearing)
                .setCheatString("(Forest 2) [Just don't even bother trying to navigate the forest]").build();

        Room forest3 = new Room.RoomBuilder(10, "DENSE FOREST", "A dense forest full of trees.")
                .side().side(Direction.NE, clearing).side(Direction.EAST, forest2).side(Direction.WEST, forest1)
                .setCheatString("(Forest 3) [Just don't even bother trying to navigate the forest]").build();

        Room underGround1 = new Room.RoomBuilder(11, "UNDERGROUND", "An underground tunnel.")
                .side().side(Direction.UP, livingRoom).side(Direction.SOUTH, forest3).setDark()
                .setCheatString("(Go EAST)").build();

        Room underGround2 = new Room.RoomBuilder(12, "UNDERGROUND", "An underground tunnel.")
                .side().side(Direction.WEST, underGround1).setDark()
                .setCheatString("(GO EAST)").build();

        Room underGround3 = new Room.RoomBuilder(13, "UNDERGROUND", "An underground tunnel.")
                .side().side(Direction.WEST, underGround2).enemy(grue).setDark()
                .setCheatString("(Go DOWN) [Do not fight the grue without the LONG SWORD]").build();

        Room underGround4 = new Room.RoomBuilder(14, "UNDERGROUND", "An underground tunnel.")
                .side().side(Direction.WEST, underGround3).setDark().build();

        Room finalRoom = new Room.RoomBuilder(15, "OASIS", "A beautiful oasis full of life.")
                .side().side(Direction.SOUTH, forest1).build();

        Room trollRoom = new Room
                .RoomBuilder(16, "TROLL ROOM", "An otherwise empty room with a large troll.")
                .side().side(Direction.SOUTH, northOfHouse).enemy(troll)
                .setCheatString("[Do not fight the troll withouth the LONG SWORD]").build();


        // Connect Rooms
        westOfHouse.setSide(Direction.SOUTH, southOfHouse);
        westOfHouse.setSide(Direction.NORTH, northOfHouse);
        westOfHouse.setSide(Direction.EAST, livingRoom);
        westOfHouse.setSide(Direction.WEST, forest1);

        livingRoom.setSide(Direction.DOWN, underGround1);
        livingRoom.setSide(Direction.EAST, kitchenRoom);

        kitchenRoom.setSide(Direction.EAST, behindHouse);
        kitchenRoom.setSide(Direction.UP, atticRoom);

        southOfHouse.setSide(Direction.EAST, behindHouse);

        behindHouse.setSide(Direction.EAST, clearing);
        behindHouse.setSide(Direction.NORTH, northOfHouse);

        northOfHouse.setSide(Direction.NORTH, trollRoom);

        forest1.setSide(Direction.EAST, westOfHouse);
        forest1.setSide(Direction.SOUTH, forest3);
        forest1.setSide(Direction.NORTH, forest1);
        forest1.setSide(Direction.NW, finalRoom);

        clearing.setSide(Direction.SOUTH, forest2);

        forest2.setSide(Direction.WEST, forest3);
        forest2.setSide(Direction.NORTH, forest2);

        forest3.setSide(Direction.DOWN, underGround1);

        underGround1.setSide(Direction.EAST, underGround2);

        underGround2.setSide(Direction.NORTH, underGround2);
        underGround2.setSide(Direction.SOUTH, underGround2);
        underGround2.setSide(Direction.EAST, underGround3);

        underGround3.setSide(Direction.NORTH, underGround3);
        underGround3.setSide(Direction.EAST, underGround3);
        underGround3.setSide(Direction.SOUTH, underGround3);
        underGround3.setSide(Direction.DOWN, underGround4);

        // Add rooms to maze
        builder.addRoom(westOfHouse);
        builder.addRoom(livingRoom);
        builder.addRoom(kitchenRoom);
        builder.addRoom(atticRoom);
        builder.addRoom(southOfHouse);
        builder.addRoom(behindHouse);
        builder.addRoom(northOfHouse);
        builder.addRoom(forest1);
        builder.addRoom(clearing);
        builder.addRoom(forest2);
        builder.addRoom(forest3);
        builder.addRoom(underGround1);
        builder.addRoom(underGround2);
        builder.addRoom(underGround3);
        builder.addRoom(underGround4);
        builder.addRoom(finalRoom);
        builder.addRoom(trollRoom);

        // Make Items
        ReadableItem mailBoxLetter = new ReadableItem("LETTER", "");
        mailBoxLetter.setContents("Welcome to Zork!  Try and win.");

        ReadableItem welcomeRug = new ReadableItem("MAT", "RUBBER");
        welcomeRug.setContents("Welcome to Zork-Mini!");
        welcomeRug.setCanPickUp(false);
        welcomeRug.setStrPickUp("You cannot pick up the rug.");
        welcomeRug.setInspect(" saying Welcome to Zork-Mini! lies by the door.");

        ReadableItem oldLetter = new ReadableItem("LETTER", "OLD");
        oldLetter.setContents("It's dangerous to enter the underground alone.  Take this LONG SWORD.");

        Key rustyKey = new Key("KEY", "RUSTY", "000");
        Key ornateKey = new Key("KEY", "ORNATE", "001");
        Key finalKey = new Key("KEY", "SILVER", "002");

        Weapon sword = new Weapon("SWORD", "LONG", 10);

        Treasure goldenEgg = new Treasure("EGG", "GOLDEN", 100);
        Treasure crystalSkull = new Treasure("SKULL", "CRYSTAL", 400);

        Light litLamp = new Light("LAMP", "LIT");

        ItemWithItems rustyChest = new ItemWithItems("CHEST", "RUSTY", "000");
        ItemWithItems ornateChest = new ItemWithItems("CHEST", "ORNATE", "001");
        ItemWithItems finalChest = new ItemWithItems("CHEST", "SILVER", "002");
        ItemWithItems mailBox = new ItemWithItems("MAILBOX");

        // Add Inside of Items
        rustyChest.addInsideOfItem(ornateKey);

        mailBox.addInsideOfItem(mailBoxLetter);

        rustyChest.addInsideOfItem(ornateKey);
        rustyChest.addInsideOfItem(goldenEgg);

        ornateChest.addInsideOfItem(sword);
        ornateChest.addInsideOfItem(oldLetter);

        finalChest.addInsideOfItem(crystalSkull);

        // Add Items to Rooms
        builder.addItem(westOfHouse, mailBox);
        builder.addItem(westOfHouse, welcomeRug);

        builder.addItem(livingRoom, ornateChest);

        builder.addItem(kitchenRoom, litLamp);

        builder.addItem(atticRoom, rustyKey);

        builder.addItem(clearing, rustyChest);

        builder.addItem(underGround4, finalKey);

        builder.addItem(finalRoom, finalChest);

        // Build Doors
        builder.buildLockedDoor(0, 1, "");
        builder.buildDoor(2, 5).setName("WINDOW");
        builder.buildDoor(1, 11).setName("GRATE");
        builder.buildDoor(10, 11).setName("HOLE");
        builder.buildDoor(6, 16);
        // Return
        return builder.getMaze();
    }
}
