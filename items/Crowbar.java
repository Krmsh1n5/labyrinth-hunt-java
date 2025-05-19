package items;

import world.Room;
import world.Door;
import util.Point;

public class Crowbar extends Item {
    public Crowbar(String name, int pryStrength) {
        super(name); 
    }

    // Getters
    // public int getPryStrength() { return pryStrength; }
    // Setters
    // public void setPryStrength(int pryStrength) { this.pryStrength = pryStrength; }

    // Methods
    public boolean breakDoor(Door door) {
        if (door.canBeUnlockedByCrowbar()) {
            door.unlockWithCrowbar();
            return true;
        } else {
            return false;
        }
    }
}