package items;

import java.util.Arrays;
import java.util.UUID;
import util.Activatable;
import util.Point;
import items.Item;
import world.Room;

public class Chest extends Item implements Activatable {
    private final UUID id = UUID.randomUUID();
    private final UUID keyId;
    private Object[] inventory;
    private boolean isLocked;

    public Chest(String name, Point position, Room location, UUID keyId, boolean isLocked) {
        super(name, position, location);
        this.keyId = keyId;
        this.isLocked = isLocked;
        this.inventory = new Object[0];
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getKeyId() { return keyId; }
    public Object[] getInventory() { return inventory; }
    public boolean isLocked() { return isLocked; }
    public Object getItem(int index) {
        if (this.inventory.length == 0) {
            System.out.println("Chest is empty");
            return null;
        }

        if (index >= 0 && index < inventory.length) {
            return inventory[index];
        } else {
            System.out.println("Invalid index");
            return null;
        }
    }

    // Setters
    public void setInventory(Object[] inventory) { this.inventory = inventory; }
    public void setUnlocked() { isLocked = false; }
    public void setLocked() { isLocked = true; }    

    // Methods
    @Override
    public void activate() {
        if(!isLocked) {
            System.out.println("Opening chest: " + getName());
            Arrays.stream(inventory).forEach(item -> 
                System.out.println("Contains: " + item));
        } else {
            System.out.println("Chest is locked!");
        }
    }

    public void addItem(Object item) {
        inventory = Arrays.copyOf(inventory, inventory.length + 1);
        inventory[inventory.length - 1] = item;
    }

}