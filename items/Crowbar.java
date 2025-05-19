package items;

import world.Room;
import world.Door;
import util.Point;

public class Crowbar extends Item {
    private int pryStrength;

    public Crowbar(String name, int pryStrength) {
        super(name); 
        this.pryStrength = pryStrength;
    }

    // Getters
    // public int getPryStrength() { return pryStrength; }
    // Setters
    // public void setPryStrength(int pryStrength) { this.pryStrength = pryStrength; }

    // Methods
    public boolean breakDoor(Door door) {
        if (door.getStrength() <= pryStrength) {
            door.open();
            return true;
        } else {
            return false;
        }
    }
}