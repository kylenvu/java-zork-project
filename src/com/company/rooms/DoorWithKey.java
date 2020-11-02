package com.company.rooms;

import com.company.commands.Player;

public class DoorWithKey extends Door {
    private final String ID;
    private boolean isLocked = true;

    public DoorWithKey(Room room1, Room room2, String ID) {
        super(room1, room2);
        this.ID = ID;
    }

    public void unlock(String keyID) {
        if (isLocked) {
            if (keyID.equals(ID)) {
                isLocked = false;
                System.out.println("The key slides into the lock, and you unlock the door.");
            } else System.out.println("They key does not seem to fit the door's lock.");
        } else {
            System.out.println("The door is already unlocked.");
        }
    }

    public void open() {
        if (isLocked) {
            System.out.println("The door is locked, and cannot be opened");
        } else {
            super.open();
        }
    }
    public void setName(String name) { this.name = name; }

    public void Enter(Player player) {
        if (!isLocked) super.Enter(player);
        else  {
            if (!ID.equals("")) {
                System.out.println("The door is locked, but looks like it can be unlocked with a key.");
            } else {
                System.out.println("The door cannot be opened.");
            }
        }
    }

    public String toString() {
        String output;
        if (isLocked) output = "There is a locked " + name;
        else output = super.toString();
        return output;
    }
}
