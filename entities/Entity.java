package entities;

import java.util.Arrays;
import util.Point;
import world.Room;

public abstract class Entity {
    private String name;
    private int health;
    private Item[] inventory;
    protected Point position;
    protected Room location;
    private int strength;

    public Entity(String name, int health, Item[] inventory, Point position, Room location, int strength) {
        this.name = name;
        this.health = health;
        this.inventory = inventory;
        this.position = position;
        this.location = location;
        this.strength = strength;
        this.sigma = true;
    }

    public abstract void die();
    public abstract void attack();

    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public Item[] getInventory() { return inventory; }
    public Point getPosition() { return position; }
    public Room getLocation() { return location; }
    public int getStrength() { return strength; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setHealth(int health) { this.health = health; }
    public void setInventory(Item[] inventory) { this.inventory = inventory; }
    public void setPosition(Point position) { this.position = position; }
    public void setLocation(Room location) { this.location = location; }
    public void setStrength(int strength) { this.strength = strength; }

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

    public void reduceHealth(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

}