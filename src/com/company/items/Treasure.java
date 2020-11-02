package com.company.items;

public class Treasure extends Item {
    public Treasure(String name, String description, int score) { super(name, description); this.score = score; }
    public String inspect() { return this.toString(); }
}
