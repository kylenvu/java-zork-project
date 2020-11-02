package com.company.items;

import java.util.Map;

public class ItemWithItems extends Item {
    private boolean isOpen = false;
    private boolean isLocked = false;
    private String ID;
    public ItemWithItems(String name) { super(name, ""); }
    public ItemWithItems(String name, String description, String ID) {
        super(name, description);
        this.ID = ID;
        canPickUp = false;
        strPickUp = "You cannot pick up the " + getHashKey();
        isOpen = false;
        isLocked = true;
    }
    public String inspect() {
        StringBuilder theOutput = new StringBuilder(this.toString());
        if (isLocked) {
            theOutput.append(".  It is locked.");
        } else if (!isOpen) {
            theOutput.append(".  It is closed.");
        } else {
            if (insideOfItem.isEmpty()) {
                theOutput.append(".  There is nothing inside the ").append(getHashKey());
            } else {
                theOutput.append(".  Inside, there is...\n");
                for (Map.Entry<String, Item> entry : insideOfItem.entrySet()) {
                    theOutput.append(String.format("a %s\n", entry.getKey()));
                }
            }
        }
        return theOutput.toString();
    }
    public void open() {
        if (isLocked) {
            System.out.printf("You cannot open the %s since it is locked.\n", getHashKey());
        } else if (isOpen){
            System.out.printf("The %s is already open.\n", getHashKey());
        } else {
            System.out.println("You opened the " + getHashKey());
            isOpen = true;
        }
    }
    public void unlock(String keyID) {
        if (isLocked) {
            if (keyID.equals(ID)) {
                System.out.println("The key slides in, and you unlock the " + getHashKey());
                isLocked = false;
            }
        } else {
            System.out.printf("The %s is already unlocked.\n", getHashKey());
        }
    }
}
