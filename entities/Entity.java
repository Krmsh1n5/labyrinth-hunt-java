package entities;

import java.util.Arrays;
import util.Point;
import world.Room;

public abstract class Entity {
    private String name;
    private int health;
    private Object[] inventory;
    private Point position;
    private Room location;
    private int strength;

    public Entity(String name, int health, Object[] inventory, Point position, Room location, int strength) {
        this.name = name;
        this.health = health;
        this.inventory = inventory;
        this.position = position;
        this.location = location;
        this.strength = strength;
    }

    public abstract void die();
    public abstract void attack();

    // Inventory management
    public void addToInventory(Object item) {
        Object[] newInventory = new Object[inventory.length + 1];
        System.arraycopy(inventory, 0, newInventory, 0, inventory.length);
        newInventory[inventory.length] = item;
        inventory = newInventory;
    }

    public void removeFromInventory(Object item) {
        // Implementation left for exercise
    }

    // Getters/Setters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getStrength() { return strength; }
    public Object[] getInventory() { return inventory; }
    // ... other getters/setters
}