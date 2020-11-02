package com.company.rooms;

import java.util.HashMap;

public class Maze {
    private final HashMap<Integer, Room> mazeMap = new HashMap<>();
    public Maze() { }
    public void addRoom(Room room) { mazeMap.put(room.roomNo(), room); }
    public Room roomNo(int num) { return mazeMap.get(num); }
    public HashMap<Integer, Room> getMazeMap() { return mazeMap; }
}
