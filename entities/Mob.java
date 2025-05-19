package entities;

import util.Point;
import world.Room;
import items.Item;

public class Mob extends Entity {
    public Mob(String name, int health, Item[] inventory, Point position, Room location, int strength) {
        super(name, health, inventory, position, location, strength);
    }

    @Override
    public void die() {
        System.out.println("Mob died");
    }

    @Override
    public void attack() {
        System.out.println("Mob attacking with strength " + getStrength());
    }

    public void execute() {
        System.out.println("Mob executing special attack");
    }
}