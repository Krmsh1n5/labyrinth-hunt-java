package items;

import util.Point;
import world.Room;

public class Ammo extends Item {
    private final String weaponType;
    private int quantity;

    public Ammo(String name, String weaponType, int quantity) {
        super(name);
        this.weaponType = weaponType;
        this.quantity = quantity;
    }

    // Getters
    public String getWeaponType() { return weaponType; }
    public int getQuantity() { return quantity; }

    // Setters
    // public void setWeaponType(String weaponType) { this.weaponType = weaponType; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Methods
    public void useAmmo(int amount) {
        quantity = Math.max(0, quantity - amount);
        if (quantity == 0) {
            System.out.println("Out of ammo!");
        } else {
            System.out.println("Ammo used. Remaining: " + quantity);
        }
    }

    public void addAmmo(int amount) {
        quantity += amount;
        System.out.println("Ammo added. Total: " + quantity);
    }

    public boolean isEmpty() {
        System.out.println("No ammo left.");
        return quantity <= 0;
    }
}