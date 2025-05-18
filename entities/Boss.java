package entities;

import util.Point;
import world.Room;

public class Boss extends Mob {
    public Boss(String name, int health, Object[] inventory, Point position, Room location, int strength) {
        super(name, health, inventory, position, location, strength);
    }

    public void heal() {
        System.out.println("Boss healing");
    }

    public Object[] getInventory() {
        return super.getInventory();
    }
}