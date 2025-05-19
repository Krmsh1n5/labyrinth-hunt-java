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
    }
}