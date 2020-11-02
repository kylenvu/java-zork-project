package com.company.rooms;

import com.company.commands.*;

public abstract class MapSite {
    protected String name;
    public abstract void Enter(Player player);
    public String getName() { return name; }
}

class Wall extends MapSite {
    public Wall() { this.name = "BLANK WALL"; }
    public void Enter(Player player) { System.out.println("You cannot enter a wall."); }
    public String toString() { return "a " + getName(); }
}



