package items;

public class Weapon extends Item {
    private int strength;
    private int ammoCapacity;

    public Weapon(String name, Point position, Room location, int strength, int ammoCapacity) {
        super(name, position, location);
        this.strength = strength;
        this.ammoCapacity = ammoCapacity;
    }

    public void shoot() {
        if (ammoCapacity > 0) {
            System.out.println("Shooting with " + getName() + "!");
            ammoCapacity--;
        } else {
            System.out.println("Out of ammo!");
        }
    }

    // Getters
    public int getStrength() { return strength; }
    public int getAmmoCapacity() { return ammoCapacity; }
}