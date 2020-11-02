package com.company.rooms;

import com.company.commands.Player;



public class Door extends MapSite {
    protected Room room1;
    protected Room room2;
    protected boolean isOpen;

    public Door(Room room1, Room room2) { this(room1, room2, "DOOR"); }
    public Door(Room room1, Room room2, String name) {
        this.room1 = room1;
        this.room2 = room2;
        this.name = name;
    }

    public void Enter(Player player) {
        if (isOpen) {
            otherSideFrom(player.getCurrentRoom()).Enter(player);
        } else {
            System.out.println("The door is closed.");
        }
    }

    public Room otherSideFrom(Room room) {
        return (room == room1) ? room2 : room1;
    }

    public void open() {
        System.out.println("You opened the " + name);
        isOpen = true;
    }

    public void setName(String name) { this.name = name; }

    public String toString() {
        return isOpen ? "There is an open " + name : "There is a closed " + name;
    }
}