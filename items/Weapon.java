package items;

import util.Point;
import world.Room;

public class Weapon extends Item {
    private int damage;
    private String type;

    public Weapon(String name, String type, int damage) {
        super(name);
        this.type = type;
        this.damage = damage;
    }

    // Getters
    public int getDamage() { return damage; }
    public String getType() { return type; }
    // Setters
    // public void setStrength(int strength) { this.strength = strength; }
    // public void setType(String type) { this.type = type; }

    // Methods
    public int shoot() {
        // Implement shooting logic here
        System.out.println("Shooting with " + getType() + "!");
        return this.getDamage();
    }
}