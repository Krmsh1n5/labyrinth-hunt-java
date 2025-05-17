package items;

import world;

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