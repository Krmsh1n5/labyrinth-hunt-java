package world;

import items.Key;
import items.Crowbar;

public class Door {
    private boolean isLocked;
    private Key requiredKey;
    private int strength;
    private Room room1;
    private Room room2;

    public Door(int strength, Room room1, Room room2) {
        this.strength = strength;
        this.room1 = room1;
        this.room2 = room2;
    }

    public void unlock(Key key) {
        if(key.getAssociatedDoor() == this) {
            isLocked = false;
            System.out.println("Door unlocked!");
        }
    }

    public boolean attemptBreak(Crowbar crowbar) {
        return crowbar.canBreakDoor(this);
    }

    public void open() {
        if(this.isLocked) {
            this.isLocked = true;
            System.out.println("Door is locked!");
        } else {
            System.out.println("Door's already opened!");
        }
    }

    public int getStrength() {
        return strength;
    }
}