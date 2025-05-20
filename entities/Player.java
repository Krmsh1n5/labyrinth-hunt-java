package entities;

import java.util.Arrays;
import java.util.UUID;
import world.Room;
import world.Chest;
import world.Door;
import util.Pair;
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
    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }
    // Setters
    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public Point getPlayerPosition() {
        return getPosition();
    }

    
    public void setPlayerLocation(Room location) {
        setLocation(location);;
    }

    @Override
    public char getSymbol() {
        return '@'; // or any symbol for Player
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public String checkPlayerSurroundings(Room room) {
        // Check for nearby chests
        Point playerPos = getPosition();
        StringBuilder interactables = new StringBuilder();

        boolean chestNearby = false;
        boolean mobNearby = false;
        boolean doorNearby = false;

        for (Chest chest : room.getChests()) {
            Point chestPos = chest.getPosition();
            int dx = Math.abs(playerPos.getX() - chestPos.getX());
            int dy = Math.abs(playerPos.getY() - chestPos.getY());

            // Check if chest is within 1 cell (3x3 area around player)
            if (dx <= 1 && dy <= 1) {
                interactables.append("c");
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
                    interactables.append("m");
                    mobNearby = true;
                    break;
                }
            }
        }

        for (Door door : room.getDoors()) {
            Point doorPos = door.getPositionByRoomNumber(room.getRoomNumber());
            int dx = Math.abs(playerPos.getX() - doorPos.getX());
            int dy = Math.abs(playerPos.getY() - doorPos.getY());
            if (dx <= 1 && dy <= 1) {
                interactables.append("d");
                doorNearby = true;
                break;
            }
        }

        if(doorNearby) System.out.println("There is a door nearby! Press 'O' to open.");
        if (chestNearby) System.out.println("There is a chest nearby! Press 'L' to loot.");
        if (mobNearby) System.out.println("There is a mob nearby! Press 'A' to attack.");

        return interactables.toString();
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
        
        for (Item item : items) {
            if (item instanceof Bandage) {
                Bandage bandage = (Bandage) item;
                Bandage existingBandage = getBandages();
                if (existingBandage != null) {
                    existingBandage.setQuantity(bandage.getQuantity() + existingBandage.getQuantity());
                    continue;
                }
            } else if (item instanceof Ammo) {
                Ammo ammo = (Ammo) item;
                Ammo existingAmmo = getAmmoFromInventory(ammo.getWeaponType());
                if (existingAmmo != null) {
                    existingAmmo.setQuantity(ammo.getQuantity() + existingAmmo.getQuantity());
                    continue; 
                }
            }

            // Add the item to the inventory if it's not a Bandage or Ammo with an existing match
            playerInventory = Arrays.copyOf(playerInventory, playerInventory.length + 1);
            playerInventory[playerInventory.length - 1] = item;
        }
        
        setInventory(playerInventory);

        // Clear the chest's inventory after looting
        chest.setInventory(new Item[0]);
        
        // Mark the chest as looted by setting its position off-grid
        chest.markAsLooted();
        
        // Update the room's grid to reflect the change
        if (getLocation() != null) {
            getLocation().updateEntityPositions();
        }
    }

    public void loot(Mob mob) {
        Item[] items = mob.lootMob();
        Item[] playerInventory = getInventory();

        for (Item item : items) {
            if (item instanceof Bandage) {
                Bandage bandage = (Bandage) item;
                Bandage existingBandage = getBandages();
                if (existingBandage != null) {
                    existingBandage.setQuantity(bandage.getQuantity() + existingBandage.getQuantity());
                    continue;
                }
            } else if (item instanceof Ammo) {
                Ammo ammo = (Ammo) item;
                Ammo existingAmmo = getAmmoFromInventory(ammo.getWeaponType());
                if (existingAmmo != null) {
                    existingAmmo.setQuantity(ammo.getQuantity() + existingAmmo.getQuantity());
                    continue; 
                }
            }

            // Add the item to the inventory if it's not a Bandage or Ammo with an existing match
            playerInventory = Arrays.copyOf(playerInventory, playerInventory.length + 1);
            playerInventory[playerInventory.length - 1] = item;
        }

        setInventory(playerInventory);

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


    // In Player.java
    public Pair<Integer, Point> interactWithDoor(Door door, Room currentRoom) {
        if (this.open(door)) { // Check if the player can open the door
            // Find the door's endpoint that isn't the current room
            for (Pair<Integer, Point> end : door.getRoomsAndPositions()) {
                if (end.getLeft() != currentRoom.getRoomNumber()) {
                    return end; // Return the connected room's ID and position
                }
            }
        }
        return null; // Door couldn't be opened or no valid endpoint
    }


    // In Player.java
    public void moveToNewRoom(Room newRoom, Point newPosition) {
        // Remove from old room
        Room currentLocation = this.getLocation();
        if (currentLocation != null) {
            currentLocation.removeEntity(this);
        }

        // Update player's state
        this.setPlayerLocation(newRoom);
        this.getPosition().setX(newPosition.getX());
        this.getPosition().setY(newPosition.getY());

        // Add to new room
        newRoom.addEntity(this);
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
        // Comment
        Item[] inventory = this.getInventory();
        for (Item item : inventory) {
            if (item instanceof Ammo) {
                Ammo ammo = (Ammo) item;
                if (ammo.getWeaponType().equals(weaponType) && !ammo.isEmpty()) {
                    return ammo;
                }
                // Continue searching if the ammo doesn't match or is empty
            }
        }
        return null; // No matching ammo found
    }

    public Bandage getBandages() {
        for (Item item : getInventory()) {
            if (item instanceof Bandage) {
                return (Bandage) item;
            }
        }
        return null; 
    }

    public Weapon[] getWeapons() {
        Weapon[] weapons = new Weapon[getInventory().length];
        int index = 0;
        for (Item item : getInventory()) {
            if (item instanceof Weapon) {
                weapons[index++] = (Weapon) item;
            }
        }
        return Arrays.copyOf(weapons, index); // Return only the filled part of the array
    }

}