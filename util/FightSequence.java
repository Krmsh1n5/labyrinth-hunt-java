package util;

import java.util.Scanner;
import entities.Mob;
import entities.Boss;
import entities.Player;
import items.Weapon;
import items.Item;
import items.Ammo;
import items.Bandage;

public class FightSequence {
    private static Scanner scanner = new Scanner(System.in);
    private static Mob targetMob;
    private static Player player;
    private static int turnCounter = 0;

    public FightSequence(Player currentPlayer, Mob currentMob) {
        FightSequence.player = currentPlayer;
        FightSequence.targetMob = currentMob;
        FightSequence.turnCounter = 0;
    }

    // Uncomment for testing
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
    //
    //     FightSequence fight = new FightSequence(player, targetMob);
    //     fight.startCombatLoop();
    // }

    public static void startCombatLoop() {
        // Special intro for boss fights
        if (targetMob instanceof Boss) {
            System.out.println("\n▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("       BOSS FIGHT: " + targetMob.getName().toUpperCase());
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("Prepare yourself! This enemy is much stronger than regular mobs!");
            System.out.println("Boss stats: Health: " + targetMob.getHealth() + " | Strength: " + 
                               targetMob.getStrength() + " | Special: Can heal itself");
        }
        
        while(player.getHealth() > 0 && targetMob.getHealth() > 0) {
            printCombatStatus();
            handlePlayerTurn();
            
            if(targetMob.getHealth() > 0) {
                handleMobAttack();
                
                // Check if boss is using its special ability
                if (targetMob instanceof Boss) {
                    Boss boss = (Boss) targetMob;
                    if (boss.isDead()) {
                        break; // Boss is defeated, exit combat loop
                    }
                }
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
        
        // Display special indicator if fighting a boss
        if(targetMob instanceof Boss) {
            System.out.println("⚠ BOSS FIGHT: " + targetMob.getName() + " ⚠");
        }
        
        System.out.println("Current Weapon: " + currentWeapon.getName() + 
                         " (Ammo: " + (currentAmmo != null ? currentAmmo.getQuantity() : 0) + ")");
        System.out.println("Bandages: " + (currentBandage != null ? currentBandage.getQuantity() : 0));
    }

    private static void handlePlayerTurn() {
        System.out.println("\nChoose action:");
        System.out.println("A. Attack");
        System.out.println("H. Heal");
        
        // If fighting a boss, show additional information
        if (targetMob instanceof Boss) {
            System.out.println("\nBoss Info: Bosses can heal themselves and deal more damage!");
        }
        
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
                // Recursively call to ensure player makes a valid choice
                handlePlayerTurn();
                break;
        }
    }

    private static void handleAttackSequence() {
        Weapon currentWeapon = player.getCurrentWeapon();
        Ammo currentAmmo = player.getAmmoFromInventory(currentWeapon.getType());

        boolean validActionTaken = false;
        
        while(!validActionTaken) {
            System.out.println("\n=== Attack Options ===");
            System.out.println("1. Attack with " + currentWeapon.getName());
            System.out.println("2. Change Weapon");
            System.out.println("3. Back to main menu");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch(choice) {
                    case 1:
                        if(currentAmmo != null && currentAmmo.getQuantity() > 0) {
                            executeAttack();
                            validActionTaken = true;
                        } else {
                            System.out.println("No ammo for " + currentWeapon.getName() + "! Choose another weapon or action.");
                        }
                        break;
                    case 2:
                        if(handleWeaponChange()) {
                            // Update weapon and ammo references after change
                            currentWeapon = player.getCurrentWeapon();
                            currentAmmo = player.getAmmoFromInventory(currentWeapon.getType());
                        }
                        break;
                    case 3:
                        handlePlayerTurn();
                        validActionTaken = true;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static boolean handleWeaponChange() {
        System.out.println("\nAvailable Weapons:");
        Weapon[] weapons = player.getWeapons();
        
        if(weapons == null || weapons.length == 0) {
            System.out.println("No weapons available!");
            return false;
        }
        
        int i = 1;
        for(Weapon w : weapons) {
            if(w != null) {
                Ammo ammo = player.getAmmoFromInventory(w.getType());
                System.out.println(i + ". " + w.getName() + 
                                " (Dmg: " + w.getDamage() + 
                                ", Ammo: " + (ammo != null ? ammo.getQuantity() : 0) + ")");
                i++;
            }
        }
        
        System.out.println(i + ". Cancel");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            // Check if user chose to cancel
            if(choice == i) {
                return false;
            }
            
            // Adjust for zero-based indexing
            choice--;
            
            // Validate choice
            if(choice < 0 || choice >= weapons.length || weapons[choice] == null) {
                System.out.println("Invalid weapon choice!");
                return false;
            }
            
            // Update the current weapon
            Weapon newWeapon = weapons[choice];
            player.setCurrentWeapon(newWeapon);
            System.out.println("Switched to " + newWeapon.getName());
            
            // Check if there's ammo for the new weapon
            Ammo newAmmo = player.getAmmoFromInventory(newWeapon.getType());
            if(newAmmo == null || newAmmo.getQuantity() <= 0) {
                System.out.println("Warning: No ammo for " + newWeapon.getName());
                return true;
            }
            
            return true;
        } catch(NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return false;
        }
    }

    private static void executeAttack() {
        Weapon currentWeapon = player.getCurrentWeapon();
        Ammo currentAmmo = player.getAmmoFromInventory(currentWeapon.getType());
        
        if(currentAmmo == null || currentAmmo.getQuantity() <= 0) {
            System.out.println("No ammo available for " + currentWeapon.getName() + "!");
            return;
        }
        
        int damage = currentWeapon.shoot();
        currentAmmo.useAmmo(1);
        
        // Apply special damage modifiers for boss encounters
        if (targetMob instanceof Boss) {
            // Bosses take 10% less damage as they're tougher
            damage = (int) Math.floor(damage * 0.9);
            System.out.println("Boss resists some of the damage!");
        }
        
        targetMob.takeDamage(damage);
        System.out.println("You dealt " + damage + " damage!");
        
        if(currentAmmo.getQuantity() <= 0) {
            System.out.println("Weapon is now out of ammo!");
        }
    }

    private static void handleHeal() {
        Bandage currentBandage = player.getBandages();
        if(currentBandage != null && currentBandage.getQuantity() > 0) {
            int healAmount = currentBandage.getHealingAmount();
            player.heal(currentBandage);
            System.out.println("Healed " + healAmount + " health!");
        } else {
            System.out.println("No bandages remaining!");
            // Give player another turn since healing failed
            handlePlayerTurn();
        }
    }

    private static void handleMobAttack() {
        int damage = targetMob.attack();
        
        // Special message for boss healing
        if(targetMob instanceof Boss) {
            Boss boss = (Boss) targetMob;
            // Check if boss is about to heal (Boss healing happens inside attack() method)
            // We just provide additional feedback here
            if(boss.getHealth() > targetMob.getHealth()) {
                System.out.println("\n" + boss.getName() + " is preparing a powerful attack!");
            }
        }
        
        player.takeDamage(damage);
        
        // Different messages for boss vs. regular mob
        if(targetMob instanceof Boss) {
            System.out.println("\n" + targetMob.getName() + " unleashes a powerful attack! You take " + damage + " damage!");
        } else {
            System.out.println("\nMob attacks! You take " + damage + " damage!");
        }
    }

    private static void concludeFight() {
        if(player.getHealth() <= 0) {
            System.out.println("You have been defeated!");
            player.die();
        } else {
            // Different victory messages based on mob type
            if (targetMob instanceof Boss) {
                System.out.println("VICTORY! You have defeated the boss " + targetMob.getName() + "!");
                System.out.println("This is a significant achievement that will be remembered!");
            } else {
                System.out.println("You have defeated the mob!");
            }
            
            System.out.println("Looting the mob...");
            Item[] loot = targetMob.getInventory();
            
            if(loot != null && loot.length > 0) {
                player.loot(targetMob);
                for(Item item : loot) {
                    if(item != null) {
                        // Special message for boss loot
                        if (targetMob instanceof Boss) {
                            System.out.println("Looted rare item: " + item.getName() + "!");
                        } else {
                            System.out.println("Looted: " + item.getName());
                        }
                    }
                }
            } else {
                System.out.println("No loot found.");
            }
            
            System.out.println("\nYour inventory:");
            Item[] playerInventory = player.getInventory();
            if(playerInventory != null) {
                for(Item item : playerInventory) {
                    if(item != null) {
                        System.out.println("- " + item.getName());
                        if(item instanceof Ammo) {
                            Ammo ammo = (Ammo) item;
                            System.out.println("  Ammo quantity: " + ammo.getQuantity());
                        } else if(item instanceof Bandage) {
                            Bandage bandage = (Bandage) item;
                            System.out.println("  Bandage quantity: " + bandage.getQuantity());
                        }
                    }
                }
            }
            
            targetMob.die();
        }
    }
}