package util;

import java.util.Scanner;
import entities.Mob;
import entities.Player;
import items.Weapon;
import items.Item;
import items.Ammo;
import items.Bandage;

public class FightSequence {
    private static Scanner scanner = new Scanner(System.in);
    private static Mob targetMob;
    private static Player player;

    public FightSequence(Player player, Mob targetMob) {
        this.player = player;
        this.targetMob = targetMob;
    }

    // public static void main(String[] args) {
    //     Player player = new Player("Hero", 100, new Item[10], new Point(0, 0), new Room(), 10);
    //     player.setInventory(new Item[] {
    //         new Bandage("Basic Bandage", 5, 15),
    //         new Ammo("Pistol", 10),
    //         new Weapon("Pistol", 5),
    //         new Ammo("AK-47", 30),
    //         new Weapon("AK-47", 10),
    //     });
    //     player.setCurrentWeapon(new Weapon("AK-47", 10));
    //     Mob targetMob = new Mob("Goblin", 50, new Item[5], new Point(1, 1), new Room(), 5);
    //     targetMob.setInventory(new Item[] {
    //         new Ammo("Pistol", 5),
    //     });

    //     startCombatLoop();
    // }

    public static void startCombatLoop() {
        while(player.getHealth() > 0 && targetMob.getHealth() > 0) {
            printCombatStatus();
            handlePlayerTurn();
            
            if(targetMob.getHealth() > 0) {
                handleMobAttack();
            }
        }
        concludeFight();
    }

    private static void printCombatStatus() {
        Weapon currentWeapon = player.getCurrentWeapon();
        Ammo currentAmmo = player.getAmmoFromInventory(currentWeapon.getType());
        Bandage currentBandage = player.getBandages();


        System.out.println("\n=== Combat Status ===");
        System.out.println("Your Health: " + player.getHealth());
        System.out.println("Mob Health: " + targetMob.getHealth());
        System.out.println("Current Weapon: " + currentWeapon.getName() + 
                         " (Ammo: " + currentAmmo.getQuantity() + ")");
        System.out.println("Bandages: " + currentBandage.getQuantity());
    }

    private static void handlePlayerTurn() {
        System.out.println("\nChoose action:");
        System.out.println("A. Attack");
        System.out.println("H. Heal");
        
        String choice = scanner.nextLine().toUpperCase();
        
        switch(choice) {
            case "A":
                handleAttackSequence();
                break;
            case "H":
                handleHeal();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void handleAttackSequence() {
        Weapon currentWeapon = player.getCurrentWeapon();
        Ammo currentAmmo = player.getAmmoFromInventory(currentWeapon.getType());

        while(true) {
            System.out.println("\n=== Attack Options ===");
            System.out.println("1. Attack with " + currentWeapon.getName());
            System.out.println("2. Change Weapon");
            
            int choice = Integer.parseInt(scanner.nextLine());
            
            if(choice == 1) {
                if(!currentAmmo.isEmpty()) {
                    executeAttack();
                    return;
                } else {
                    System.out.println("No ammo! Choose another weapon:");
                    handleWeaponChange();
                }
            } else if(choice == 2) {
                handleWeaponChange();
            }
        }
    }

    private static void handleWeaponChange() {
        Weapon currentWeapon = player.getCurrentWeapon();
        System.out.println("\nAvailable Weapons:");
        Weapon[] weapons = player.getWeapons();
        
        for(Weapon w : weapons) {
            int i = 1;
            Ammo ammo = player.getAmmoFromInventory(w.getType());
            System.out.println((i) + ". " + w.getName() + 
                             " (Dmg: " + w.getDamage() + 
                             ", Ammo: " + ammo.getQuantity() + ")");
        }
        
        int choice = Integer.parseInt(scanner.nextLine()) - 1;
        currentWeapon = weapons[choice];
        System.out.println("Switched to " + currentWeapon.getName());
        executeAttack();
    }

    private static void executeAttack() {
        Weapon currentWeapon = player.getCurrentWeapon();
        Ammo currentAmmo = player.getAmmoFromInventory(currentWeapon.getType());
        int damage = currentWeapon.shoot();
        currentAmmo.useAmmo(1);
        
        targetMob.takeDamage(damage);
        System.out.println("You dealt " + damage + " damage!");
        
        if(currentAmmo.isEmpty()) {
            System.out.println("Weapon is now out of ammo!");
        }
    }

    private static void handleHeal() {
        Bandage currentBandage = player.getBandages();
        if(currentBandage.getQuantity() > 0) {
            player.heal(currentBandage);
            System.out.println("Healed " + currentBandage.getHealingAmount() + " health!");
        } else {
            System.out.println("No bandages remaining!");
        }
    }

    private static void handleMobAttack() {
        int damage = targetMob.attack();
        player.takeDamage(damage);
        System.out.println("\nMob attacks! You take " + damage + " damage!");
    }

    private static void concludeFight() {
        if(player.getHealth() <= 0) {
            System.out.println("You have been defeated!");
            player.die();
        } else {
            System.out.println("You have defeated the mob!");
            System.out.println("Looting the mob...");
            Item[] loot = targetMob.getInventory();
            player.loot(targetMob);
            for(Item item : loot) {
                System.out.println("Looted: " + item.getName());
            }
            targetMob.die();
        }
    }
}