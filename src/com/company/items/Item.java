package com.company.items;

import java.util.HashMap;

public abstract class Item {
    protected String name;
    protected String description;
    protected int damageDealt = 0;
    protected boolean canPickUp = true;
    protected String strPickUp;
    protected int score = 0;
    protected HashMap<String, Item> insideOfItem = new HashMap<>();

    /* ===================================
                  CONSTRUCTOR
    ====================================== */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.strPickUp = "You have picked up the " + getHashKey();
    }
    /* ===================================
            ACCESSORS & MUTATORS
    ====================================== */
    public boolean getCanPickUp() { return canPickUp; }
    public void setCanPickUp(boolean canPickUp) { this.canPickUp = canPickUp; }

    public void setStrPickUp(String strPickUp) { this.strPickUp = strPickUp; }

    public String getName() { return name; }
    public String getHashKey() {
        return (description.equals("") ? name : description + " " + name);
    }

    public int getScore() { return score; }

    public HashMap<String, Item> getInsideOfItem() { return insideOfItem; }
    public void addInsideOfItem(Item item) { insideOfItem.put(item.getHashKey(), item); }

    public String toString() { return String.format("%s %s", description.toUpperCase(), name.toUpperCase()); }
    public int attack() { return damageDealt; }
    public String take() { return strPickUp; }
    public abstract String inspect();
}
