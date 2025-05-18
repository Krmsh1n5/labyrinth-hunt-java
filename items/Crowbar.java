package items;

import world.Room;
import world.Door;
import util.Point;

public class Crowbar extends Item {
    private int pryStrength;

    public Crowbar(String name, Point position, Room location, int pryStrength) {
        super(name, position, location);
        this.pryStrength = pryStrength;
    }

    // Getters
    public int getPryStrength() { return pryStrength; }
    // Setters
    public void setPryStrength(int pryStrength) { this.pryStrength = pryStrength; }

    // Methods
    public boolean canBreakDoor(Door door) {
        return pryStrength > door.getStrength();
    }
}