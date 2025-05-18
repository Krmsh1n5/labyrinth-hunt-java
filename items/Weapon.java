package items;

import util.Point;
import world.Room;

public class Weapon extends Item {
    private int strength;
    private String type;
    private int ammoCapacity;

    public Weapon(String name, Point position, Room location, int strength, int ammoCapacity) {
        super(name, position, location);
        this.strength = strength;
        this.ammoCapacity = ammoCapacity;
    }

    // Getters
    public int getStrength() { return strength; }
    public int getAmmoCapacity() { return ammoCapacity; }
    public String getType() { return type; }
    // Setters
    public void setStrength(int strength) { this.strength = strength; }
    public void setAmmoCapacity(int ammoCapacity) { this.ammoCapacity = ammoCapacity; }
    public void setType(String type) { this.type = type; }

    // Methods
    public void shoot() {
        if (ammoCapacity > 0) {
            System.out.println("Shooting with " + getName() + "!");
            ammoCapacity--;
        } else {
            System.out.println("Out of ammo!");
        }
    }

}