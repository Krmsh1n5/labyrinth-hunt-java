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

    public boolean canBreakDoor(Door door) {
        return pryStrength > door.getStrength();
    }

    public int getPryStrength() { return pryStrength; }
}