package items;

import util;
import java.util;

public class Chest extends Item implements Activatable {
    private Object[] inventory;
    private boolean isLocked;

    public Chest(String name, Point position, Room location, boolean isLocked) {
        super(name, position, location);
        this.isLocked = isLocked;
        this.inventory = new Object[0];
    }

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

    public boolean isLocked() { return isLocked; }
    public void setLocked(boolean locked) { isLocked = locked; }
}