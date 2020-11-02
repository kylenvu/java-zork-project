package com.company.commands;


public class Enemy extends Entity {
    public Enemy(String name, String description, int hitPoints, int damageDealt) {
        this.name = name.toUpperCase();
        this.description = description;
        this.hitPoints = hitPoints;
        this.damageDealt = damageDealt;
    }
    public int attack() { return damageDealt; }
    public String getName() { return name; }
    public String toString() { return String.format("A %s.  %s", name, description); }
}
