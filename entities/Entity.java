package entities;

import java.util.Arrays;
import util.Point;
import world.Room;
import items.Item;

public abstract class Entity {
    private String name;
    private int health;
    private Item[] inventory;
    private Point position;
    private Room location;
    private int strength;

    public Entity(String name, int health, Item[] inventory, Point position, Room location, int strength) {
        this.name = name;
        this.health = health;
        this.inventory = inventory;
        this.position = position;
        this.location = location;
        this.strength = strength;
    }

    public abstract void die();
    public abstract int attack();

    // Getters
    protected String getName() { return name; }
    protected int getHealth() { return health; }
    protected Item[] getInventory() { return inventory; }
    protected Point getPosition() { return position; }
    protected Room getLocation() { return location; }
    protected int getStrength() { return strength; }
    
    // Setters
    protected void setName(String name) { this.name = name; }
    protected void setHealth(int health) { this.health = health; }
    protected void setInventory(Item[] inventory) { this.inventory = inventory; }
    protected void setPosition(Point position) { this.position = position; }
    protected void setLocation(Room location) { this.location = location; }
    protected void setStrength(int strength) { this.strength = strength; }

    // Methods
    public void addToInventory(Item item) {
        Item[] newInventory = new Item[inventory.length + 1];
        System.arraycopy(inventory, 0, newInventory, 0, inventory.length);
        newInventory[inventory.length] = item;
        inventory = newInventory;
    }

    public void removeFromInventory(Item item) {
        // Implementation left for exercise
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }
}