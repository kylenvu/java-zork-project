package com.company.items;

public class Light extends Item {
    public Light(String name, String description) { super(name, description); }
    public String inspect() {
        return toString() + ".  It is perpetually on.";
    }
}
