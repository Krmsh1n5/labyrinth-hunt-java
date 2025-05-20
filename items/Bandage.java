package items;

import util.Point;
import world.Room;

public class Bandage extends Item {
    private int quantity;
    private final int healingAmount; // Default healing amount

    public Bandage(String name, int quantity, int healingAmount) {
        super(name); // Call to Item(String, Point, Room)
        this.quantity = quantity;
        this.healingAmount = healingAmount;
    }

    // Getters
    public int getQuantity() {
        return quantity;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    // Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void useBandage(int amount){
        quantity = Math.max(0, quantity - amount);
        if (quantity == 0) {
            System.out.println("Out of bandages!");
        } else {
            System.out.println("Bandage used. Remaining: " + quantity);
        }
    }   

    public void addBandage(int amount){
        quantity += amount;
        System.out.println("Bandage added. Total: " + quantity);
    }

    public boolean isEmpty() {
        System.out.println("No bandage left.");
        return quantity <= 0;
    }
}
