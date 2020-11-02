package com.company;

import java.util.HashMap;

public interface Directions {
    enum Direction {
        NORTH, SOUTH, EAST, WEST, NE, NW, SE, SW, UP, DOWN
    }
    HashMap<String, Direction> directionMap = createMap();
    private static HashMap<String, Direction> createMap() {
        HashMap<String, Direction> thisMap = new HashMap<>();
        thisMap.put("NORTH", Direction.NORTH);
        thisMap.put("N", Direction.NORTH);
        thisMap.put("SOUTH", Direction.SOUTH);
        thisMap.put("S", Direction.SOUTH);
        thisMap.put("EAST", Direction.EAST);
        thisMap.put("E", Direction.EAST);
        thisMap.put("WEST", Direction.WEST);
        thisMap.put("W", Direction.WEST);
        thisMap.put("NE", Direction.NE);
        thisMap.put("NW", Direction.NW);
        thisMap.put("SE", Direction.SE);
        thisMap.put("SW", Direction.SW);
        thisMap.put("UP", Direction.UP);
        thisMap.put("U", Direction.UP);
        thisMap.put("DOWN", Direction.DOWN);
        thisMap.put("D", Direction.DOWN);
        return thisMap;
    }
}
