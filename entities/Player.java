package entities;

import java.util.Arrays;
import java.util.UUID;
import world.Room;
import world.Chest;
import world.Door;
import util.Point;
import items.Weapon;
import items.Key;
import items.Item;
import items.Bandage;
import items.Ammo;
public class Player extends Entity {
    private Weapon currentWeapon; 
    private final int maxHealth = 100;

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

    @Override
    public char getSymbol() {
        return '@'; // or any symbol for Player
    }

    protected int getMaxHealth() {
        return maxHealth;
    }

    public void checkPlayerSurroundings(Room room) {
        // Check for nearby chests
        Point playerPos = getPosition();
        boolean chestNearby = false;
        boolean mobNearby = false;

        for (Chest chest : room.getChests()) {
            Point chestPos = chest.getPosition();
            int dx = Math.abs(playerPos.getX() - chestPos.getX());
            int dy = Math.abs(playerPos.getY() - chestPos.getY());

            // Check if chest is within 1 cell (3x3 area around player)
            if (dx <= 1 && dy <= 1) {
                chestNearby = true;
                break;
            }
        }

        for (Entity entity : room.getEntities()) {
            if (entity == this) continue; // Skip the player
            if (entity instanceof Mob) {
                Point mobPos = entity.getPosition();
                int dx = Math.abs(playerPos.getX() - mobPos.getX());
                int dy = Math.abs(playerPos.getY() - mobPos.getY());
                if (dx <= 1 && dy <= 1) {
                    mobNearby = true;
                    break;
                }
            }
        }

        if (chestNearby) System.out.println("There is a chest nearby! Press 'L' to loot.");
        if (mobNearby) System.out.println("There is a mob nearby! Press 'A' to attack.");
    }

    // Methods
    public boolean move(char direction, Room room) {
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

        char[][] grid = room.getGrid();
        char targetCell = grid[newY][newX];

        if(targetCell != '.') {
            System.out.println("There is an obstacle in the way!");
            return false;
        }

        current.setX(newX);
        current.setY(newY);
        return true;

    }
    
    public void loot(Chest chest) {
        Item[] items = chest.getInventory();
        Item[] playerInventory = getInventory();
        int newSize = playerInventory.length + items.length;
        Item[] newInventory = Arrays.copyOf(playerInventory, newSize);
        
        System.arraycopy(items, 0, newInventory, playerInventory.length, items.length);
        setInventory(newInventory);
        
        // Clear the chest after looting
        chest.setInventory(new Item[0]);
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
            return true;
        }
    }

    public boolean open(Chest chest) {
        if(chest.isLocked()) {
            UUID key = findKeyForChest(chest);
            if(key != null) {
                chest.unlockChest(key);
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

    
    public void heal(Bandage bandage) {
        if (bandage.getQuantity() <= 0) {
            System.out.println("No bandages left!");
            return;
        }
        int currentHealth = getHealth();
        int healingAmount = bandage.getHealingAmount();
        
        if (currentHealth + healingAmount > maxHealth) {
            healingAmount = maxHealth - currentHealth; // Heal only up to max health
            }
        
        currentHealth += healingAmount;
        setHealth(currentHealth);
        bandage.useBandage(1); // Use one bandage
    }

    public void changeWeapon(Weapon weapon) { 
        this.currentWeapon = weapon;
    } 













































































































    public void takeDamage(int damage) {
        int currentHealth = this.getHealth();
        currentHealth -= damage;
        this.setHealth(currentHealth);
        if (currentHealth < 0) {
            die();
        }
    }
    
    @Override
    public void die() {
        this.setHealth(0);
        this.setPosition(new Point(-1, -1));
        this.setLocation(null);
    }

    @Override
    public int attack() {
    // User gets the option to choose the weapon to attack and we set that weapon as the current weapon
    // areWeChangingWeapon function should be implemented in the main game loop
        if(currentWeapon != null) { // Check if the player has a weapon
            Ammo ammo = this.getAmmoFromInventory(currentWeapon.getType());
            if (ammo != null) {
                ammo.useAmmo(1);
            } else {
                return 0;
            }
            return currentWeapon.shoot();
        } else { // If no weapon is selected, use the default attack with fists
            return this.getStrength();
        }
    }

    public Ammo getAmmoFromInventory(String weaponType) {
        for (Item item : getInventory()) {
            if (item instanceof Ammo) {
                Ammo ammo = (Ammo) item;
                if (ammo.getWeaponType().equals(weaponType) && !ammo.isEmpty()) {
                    return ammo;
                }
                else {
                    return null;
                }
            }
        }
        return null; // No matching ammo found
    }
}