package entities;

import java.util.Arrays;
import util.Point;
import world.Room;
import items.Item;

public class Mob extends Entity {
    public Mob(String name, int health, Item[] inventory, Point position, Room location, int strength) {
        super(name, health, inventory, position, location, strength);
    }

    //Methods
    @Override
    public void die() {
        this.setHealth(0);
        this.setPosition(new Point(-1, -1));
        this.setLocation(null);
    }

    @Override
    public int attack() {
        return this.getStrength();
    }

    public void takeDamage(int damage) {
        int currentHealth = this.getHealth();
        currentHealth -= damage;
        this.setHealth(currentHealth);
        if (currentHealth < 0) {
            die();
        }
    }

    public Item[] lootMob() {
        int currentHealth = this.getHealth();
        Item[] inventory = this.getInventory();
        if(currentHealth < 0 && inventory.length > 0) {
            Item[] lootedItems = Arrays.copyOf(inventory, inventory.length);
            this.setInventory(new Item[0]);
            return lootedItems;
        }
        return new Item[0];
    }
}