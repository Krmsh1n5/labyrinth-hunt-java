package items;

public class Ammo extends Item {
    private String weaponType;
    private int quantity;

    public Ammo(String name, Point position, Room location, String weaponType, int quantity) {
        super(name, position, location);
        this.weaponType = weaponType;
        this.quantity = quantity;
    }

    // Getters
    public String getWeaponType() { return weaponType; }
    public int getQuantity() { return quantity; }
    
    public void useAmmo(int amount) {
        quantity = Math.max(0, quantity - amount);
    }
}