package world;

import java.util.Arrays;
import java.util.UUID;
import items.Item;
import util.Activatable;
import util.Point;

public class Chest implements Activatable {
    private final UUID id = UUID.randomUUID();
    private final UUID keyId;
    private Point spawnPosition;
    private final Room spawnLocation;
    private Item[] inventory;
    private boolean isLocked;

    public Chest(String name, Point spawnPosition, Room spawnLocation, UUID keyId, boolean isLocked, Item[] inventory) {
        this.keyId = keyId;
        this.spawnPosition = spawnPosition;
        this.spawnLocation = spawnLocation;
        this.isLocked = isLocked;
        this.inventory = inventory;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getKeyId() { return keyId; }
    public Point getPosition() { return spawnPosition; }
    public Room getLocation() { return spawnLocation; }
    public Item[] getInventory() { return inventory; }
    public boolean isLocked() { return isLocked; }

    public void setPosition(Point position) {
        this.spawnPosition = position;
    }
    // Setters
    public void setInventory(Item[] inventory) { this.inventory = inventory; }
    // public void setUnlocked() { isLocked = false; }
    // public void setLocked() { isLocked = true; }    

    @Override
    public void activate(UUID keyId) {
        if (!isLocked) {
            System.out.println("Opening chest: " + getId());
            Arrays.stream(inventory)
                .forEach(item -> System.out.println("Contains: " + item));
        } else {
            System.out.println("Chest is locked!");
        }
    }

    // Methods
    // @Override
    // public void activate() {
    //     if(!isLocked) {
    //         System.out.println("Opening chest: " + getName());
    //         Arrays.stream(inventory).forEach(item -> 
    //             System.out.println("Contains: " + item));
    //     } else {
    //         System.out.println("Chest is locked!");
    //     }
    // }

    // public void addItem(Item item) {
    //     inventory = Arrays.copyOf(inventory, inventory.length + 1);
    //     inventory[inventory.length - 1] = item;
    // }

    public boolean unlockChest(UUID keyId) {
        if(this.keyId.equals(keyId)) {
            this.isLocked = false;
            return true;
        } else {
            return false;
        }
    }

    public Item[] lootChest() {
        if(!isLocked) {
            Item[] lootedItems = Arrays.copyOf(inventory, inventory.length);
            inventory = new Item[0]; // Empty the chest after looting
            return lootedItems;
        } else {
            System.out.println("Chest is locked!");
            return new Item[0];
        }
    }

    public void markAsLooted() {
    // Set position to an invalid position off-grid
        this.setPosition(new Point(-1, -1));
    }

}