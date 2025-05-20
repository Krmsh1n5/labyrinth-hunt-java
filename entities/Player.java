package entities;

import java.util.Arrays;
import world.Room;
import world.Chest;
import world.Door;
import util.Point;
import items.Weapon;
import items.Key;
import items.Item;

public class Player extends Entity {
    private Weapon currentWeapon;

    public Player(String name, int health, Item[] inventory, Point position, Room location, int strength) {
        super(name, health, inventory, position, location, strength);
    }

    // Getters
    protected Weapon getCurrentWeapon() {
        return currentWeapon;
    }
    // Setters
    protected void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public Point getPlayerPosition() {
        return getPosition();
    }

    // Methods
    @Override
    public void die() {
        System.out.println("Player died!");
    }

    @Override
    public int attack() {
        if(currentWeapon != null) {
            currentWeapon.shoot();
        } else {
            System.out.println("Player attacking with bare hands");
        }
        return currentWeapon != null ? currentWeapon.getDamage() : 0;
    }

    public boolean move(char direction, int xMatrix, int yMatrix) {
        Point current = getPosition();
        int newX = current.getX();
        int newY = current.getY();

        switch(Character.toLowerCase(direction)) {
            case 'a': newX--; break;
            case 'd': newX++; break;
            case 'w': newY--; break;
            case 's': newY++; break;
            default: return false; // Invalid direction
        }

        // Check boundaries (assuming 2x2 grid)
        if(newX >= 1 && newX < (xMatrix-1) && newY >= 1 && newY < (yMatrix-1)) {
            current.setX(newX);
            current.setY(newY);
            return true;
        }
        return false;
    }
    
    public void loot(Chest chest) {
        Item[] items = chest.getItems();
        Item[] playerInventory = getInventory();
        int newSize = playerInventory.length + items.length;
        Item[] newInventory = Arrays.copyOf(playerInventory, newSize);
        
        System.arraycopy(items, 0, newInventory, playerInventory.length, items.length);
        setInventory(newInventory);
        
        // Clear the chest after looting
        chest.setItems(new Item[0]);
    }

    public void loot(Mob mob) {
        Item[] items = mob.lootMob();
        Item[] playerInventory = getInventory();
        int newSize = playerInventory.length + items.length;
        Item[] newInventory = Arrays.copyOf(playerInventory, newSize);
        
        System.arraycopy(items, 0, newInventory, playerInventory.length, items.length);
        setInventory(newInventory);

        // Clear the mob's inventory after looting
        mob.setInventory(new Item[0]);
    }

    public boolean open(Door door) {
        if(door.isLocked()) {
            UUID key = findKeyForDoor(door);
            if(key != null) {
                door.unlockWithKey(key);
                return true;
            } else if(door.canBeUnlockedByCrowbar()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true
        }
    }

    public boolean open(Chest chest) {
        if(chest.isLocked()) {
            UUID key = findKeyForChest(chest);
            if(key != null) {
                chest.unlock(key);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private UUID findKeyForChest(Chest chest) {
        for(Item item : getInventory()) {
            if(item instanceof Key) {
                Key key = (Key) item;
                if(key.getId().equals(chest.getKeyId())) {
                    return key.getId();
                }
            }
        }
        return null;
    }

    private UUID findKeyForDoor(Door door) {
        for(Item item : getInventory()) {
            if(item instanceof Key) {
                Key key = (Key) item;
                if(key.getId().equals(door.getKeyId())) {
                    return key.getId();
                }
            }
        }
        return null;
    }

    public void heal() { /* Implementation */ }
    public void chooseWeapon(Weapon weapon) { 
        this.currentWeapon = weapon;
    } 
}