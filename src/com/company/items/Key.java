package com.company.items;


public class Key extends Item {
    private final String ID;
    public Key(String name, String description, String ID) { super(name, description); this.ID = ID; }

    public String getID() { return ID; }
    public String inspect() { return super.toString(); }
}
