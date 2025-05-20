package entities;

import util.Point;
import world.Room;
import items.Item;

public class Boss extends Mob {
    private int turnCount = 0; // Tracks number of attacks
    private static final int HEAL_TURNS = 3;

    public Boss(String name, int health, Item[] inventory, Point position, Room location, int strength) {
        super(name, health, inventory, position, location, strength);
    }

    public void heal() {
        System.out.println("Boss is healing!");
        // Bosses can heal itself
        if (this.getHealth() < 100) {
            this.setHealth(this.getHealth() + 5);
            System.out.println(this.getName() + " heals for 10 health!");
        } else {
            System.out.println(this.getName() + " is already at full health!");
        }
    }

    @Override
    public void die() {
        // Bosses have a special death message
        System.out.println("Boss has been defeated!");
        this.setHealth(0);
        this.setPosition(new Point(-1, -1));
        this.setLocation(null);
    }

    public boolean isDead() {
        return this.getHealth() <= 0;
    }

    @Override
    public int attack() {
        turnCount++; // Increment turn count

        // Heal every N turns before attacking
        if (turnCount % HEAL_TURNS == 0) {
            heal();
        }
        
        return this.getStrength() + 10; // Bosses deal more damage;
    }
}