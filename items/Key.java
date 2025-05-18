package items;

import world.Door;
import world.Room;
import util.Point;

public class Key extends Item {
    private Door doorAssociativity;

    public Key(String name, Point position, Room location, Door associatedDoor) {
        super(name, position, location);
        this.doorAssociativity = associatedDoor;
    }

    public Door getAssociatedDoor() {
        return doorAssociativity;
    }
}