package entities;

import world;
import util;

public class Player extends Entity {
    private Weapon currentWeapon;

    public Player(String name, int health, Object[] inventory, Point position, Room location, int strength) {
        super(name, health, inventory, position, location, strength);
    }

    @Override
    public void die() {
        System.out.println("Player died!");
    }

    @Override
    public void attack() {
        if(currentWeapon != null) {
            currentWeapon.use();
        } else {
            System.out.println("Player attacking with bare hands");
        }
    }

    public void unlock(Door door, Key key) {
        if(Arrays.asList(getInventory()).contains(key)) {
            door.unlock(key);
        }
    }

    public void move() { /* Implementation */ }
    public void heal() { /* Implementation */ }
    public void open(Chest chest) { /* Implementation */ }
    public void chooseWeapon(Weapon weapon) { 
        this.currentWeapon = weapon;
    } 
}