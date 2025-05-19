package entities;

import java.util.Arrays;
import world.Room;
import world.Door;
import util.Point;
import items.Weapon;
import items.Key;
import items.Chest;
import items.Item;

public class Player extends Entity {
    private Weapon currentWeapon;

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

    // Methods
    @Override
    public void die() {
        System.out.println("Player died!");
    }

    @Override
    public void attack() {
        if(currentWeapon != null) {
            currentWeapon.shoot();
        } else {
            System.out.println("Player attacking with bare hands");
        }
    }

    public void move() {}
    public void heal() { /* Implementation */ }
    public void open(Chest chest) { /* Implementation */ }
    public void chooseWeapon(Weapon weapon) { 
        this.currentWeapon = weapon;
    } 
}