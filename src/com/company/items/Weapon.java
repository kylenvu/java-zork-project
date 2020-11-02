package com.company.items;

public class Weapon extends Item {
    private final int damageDealt;
    public Weapon(String name, String description, int damageDealt) {
        super(name, description);
        this.damageDealt = damageDealt;
    }
    public int attack() { return damageDealt; }
    public String inspect() { return super.toString(); }
}
