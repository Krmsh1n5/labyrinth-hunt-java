package entities;

import java.util.Arrays;
import world.Room;
import world.Chest;
import world.Door;
import util.Point;
import items.Weapon;
import items.Key;
import items.Item;
import items.Bandage;


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

    public void open(Chest chest) { /* Implementation */ }
    public void chooseWeapon(Weapon weapon) { 
        this.currentWeapon = weapon;
    } 
}