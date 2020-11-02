package com.company.rooms;

import com.company.Directions;
import com.company.items.*;

import java.util.*;


abstract class MazeBuilder {
    public abstract void buildMaze();

    public abstract void addRoom(Room room);
    public abstract void addItem(Room room, Item item);

    public abstract Door buildDoor(int roomFrom, int roomTo);
    public abstract void buildLockedDoor(int roomFrom, int roomTo, final String ID);

    public abstract Maze getMaze();

    protected MazeBuilder() {
    }

}

public class StandardMazeBuilder extends MazeBuilder implements Directions {
    private Maze currentMaze;

    public StandardMazeBuilder() { currentMaze = null; }
    public void buildMaze() { currentMaze = new Maze(); }
    public void addItem(Room room, Item item) { room.getItems().put(item.getHashKey(), item); }
    public void addRoom(Room room) {
        if (!currentMaze.getMazeMap().containsValue(room)) {
            currentMaze.addRoom(room);
        }
    }
    public Door buildDoor(int roomFrom, int roomTo) {
        Room room1 = currentMaze.roomNo(roomFrom);
        Room room2 = currentMaze.roomNo(roomTo);
        Door door = new Door(room1, room2);

        room1.setSide(commonWall(room1, room2), door);
        room2.setSide(commonWall(room2, room1), door);
        return door;
    }
    public void buildLockedDoor(int roomFrom, int roomTo, final String ID) {
        Room room1 = currentMaze.roomNo(roomFrom);
        Room room2 = currentMaze.roomNo(roomTo);
        Door door = new DoorWithKey(room1, room2, ID);

        room1.setSide(commonWall(room1, room2), door);
        room2.setSide(commonWall(room2, room1), door);
    }

    public Direction commonWall(Room r1, Room r2) {
        Direction commonSide = null;
        for (Map.Entry<Direction, MapSite> entry : r1.getAllSides().entrySet()) {
            if (entry.getValue() == r2) {
                commonSide = entry.getKey();
            }
        }
        return commonSide;
    }
    public Maze getMaze() { return currentMaze; }

}