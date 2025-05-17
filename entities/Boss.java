package entities;

import util;
import world;

public class Boss extends Mob {
    public Boss(String name, int health, Object[] inventory, Point position, Room location, int strength) {
        super(name, health, inventory, position, location, strength);
    }

    public void heal() {
        System.out.println("Boss healing");
    }
}