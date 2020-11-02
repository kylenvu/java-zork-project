package com.company.items;

public class ReadableItem extends Item {
    private String contents;
    private String inspect = "There is something written on it.";
    public ReadableItem(String name, String description) { super(name, description); }
    public void setContents(String contents) { this.contents = contents; }
    public void setInspect(String inspect) { this.inspect = inspect; }
    public void read() { System.out.printf("The %s reads: %s\n", toString(), contents); }
    public String inspect() {
        return toString() + " " + inspect;
    }
}
