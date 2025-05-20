import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import entities.Player;
import entities.Entity;
import entities.Mob;
import entities.Boss;
import util.Pair;
import util.Point;
import world.Chest;
import world.Door;
import world.Room;
import items.Item;
import items.Weapon;
import items.Ammo;
import items.Bandage;
import util.FightSequence;
import items.Crowbar;
import items.Key;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Initialize player with inventory ---
        Player player = new Player("Hero", 100, new Item[150], new Point(3, 4), null, 10);
        
        // Create weapons and ammo
        String pistolType = "Pistol";
        String rifleType = "Rifle";
        String shotgunType = "Shotgun";
        
        // Create initial items - IMPROVED: Player starts with better equipment
        Bandage basicBandage = new Bandage("Basic Bandage", 5, 15);
        Ammo pistolAmmo = new Ammo("Pistol Ammo", pistolType, 20); // More ammo
        Weapon pistol = new Weapon("Basic Pistol", pistolType, 15); // Stronger pistol
        Crowbar crowbar = new Crowbar("Sturdy Crowbar"); // Better crowbar
        Bandage advancedBandage = new Bandage("Advanced Bandage", 3, 30); // Better healing

        // Keys for doors
        Key keyForDoor1 = new Key("Bronze Key");
        Key keyForDoor2 = new Key("Silver Key");
        Key keyForDoor3 = new Key("Golden Key");
        Key keyForChest3 = new Key("Iron Key"); // New key for chest3

        // Set player's starting inventory - IMPROVED: Player starts with more items
        Item[] playerInventory = new Item[] {
            basicBandage,
            advancedBandage,
            pistolAmmo,
            pistol,
            crowbar,
            keyForDoor1, // IMPROVED: Player now starts with the key to Room 2
        };

        player.setInventory(playerInventory);
        player.setCurrentWeapon(pistol);

        // --- Create mobs --- IMPROVED: Weaker enemies
        // Room 1: Weak goblin
        Mob goblin = new Mob("Goblin", 20, new Item[1], new Point(2, 2), null, 3);
        Ammo goblinAmmo = new Ammo("Goblin's Pistol Ammo", pistolType, 5);
        goblin.setInventory(new Item[] { goblinAmmo }); // No key needed here since player has it

        // Room 2: Weaker orc
        Mob orc = new Mob("Orc", 35, new Item[2], new Point(5, 5), null, 5);
        Weapon orcWeapon = new Weapon("Orc's Rifle", rifleType, 8);
        Ammo orcAmmo = new Ammo("Rifle Ammo", rifleType, 15);
        orc.setInventory(new Item[] { orcWeapon, orcAmmo });

        // Room 3: Two zombies (weaker)
        Mob zombie1 = new Mob("Zombie", 25, new Item[1], new Point(2, 3), null, 7);
        Mob zombie2 = new Mob("Undead", 20, new Item[1], new Point(5, 2), null, 7);
        zombie1.setInventory(new Item[] { new Bandage("Bloodied Bandage", 2, 10) });
        zombie2.setInventory(new Item[] { keyForDoor2 }); // Zombie2 has the key to Room 4

        // Room 4: Final boss (weaker)
        Boss finalBoss = new Boss("Dungeon Lord", 80, new Item[3], new Point(5, 5), null, 12);
        Weapon bossWeapon = new Weapon("Legendary Shotgun", shotgunType, 13);
        Ammo bossAmmo = new Ammo("Shotgun Shells", shotgunType, 5);
        finalBoss.setInventory(new Item[] { 
            bossWeapon, 
            bossAmmo, 
            keyForDoor3, // Boss has exit key
            new Bandage("Advanced Medkit", 3, 25) 
        });

        // --- Create doors between rooms ---
        // Door 1: Room 1 to Room 2 (requires Bronze Key - player has this)
        UUID door1Id = keyForDoor1.getId();
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d1ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{ new Pair<>(1, new Point(9, 4)), new Pair<>(2, new Point(0, 4)) };
        Door door1 = new Door(door1Id, d1ends, true, false);

        // Door 2: Room 2 to Room 3 (can be unlocked with crowbar)
        UUID door2Id = keyForDoor2.getId();
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d2ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{ new Pair<>(2, new Point(9, 2)), new Pair<>(3, new Point(0, 2)) };
        Door door2 = new Door(door2Id, d2ends, true, true); // Can be opened with crowbar

        // Door 3: Room 3 to Room 4 (requires Silver Key from zombie)
        // FIXED: Using proper UUID from the Silver Key
        // MODIFIED: Changed door position to be more visible on the map (moved from bottom to right side)
        UUID door3Id = keyForDoor2.getId();
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d3ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{ new Pair<>(3, new Point(9, 5)), new Pair<>(4, new Point(0, 5)) };
        Door door3 = new Door(door3Id, d3ends, true, false); // Can't be opened with crowbar, needs key

        // Secret exit door (requires Golden Key from boss)
        UUID exitDoorId = keyForDoor3.getId();
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] exitEnds = (Pair<Integer,Point>[])
            new Pair<?,?>[]{ new Pair<>(4, new Point(9, 5)), new Pair<>(0, new Point(0, 0)) };
        Door exitDoor = new Door(exitDoorId, exitEnds, true, false);

        // --- Create chests ---
        // Room 1: Unlocked chest with basic supplies and Iron Key
        Chest chest1 = new Chest("Wooden Chest", new Point(7, 2), null, UUID.randomUUID(), false, 
            new Item[] { 
                new Bandage("Extra Bandage", 2, 30),
                new Ammo("Extra Pistol Ammo", pistolType, 8),
                keyForChest3, // IMPROVED: This chest contains the key for chest3
            });

        // Room 2: Unlocked chest with rifle 
        Chest chest2 = new Chest("Steel Chest", new Point(3, 7), null, UUID.randomUUID(), false, // IMPROVED: Now unlocked
            new Item[] { 
                new Weapon("Military Rifle", rifleType, 12),
                new Ammo("Military Rifle Ammo", rifleType, 20) 
            });

        // Room 3: Locked chest with shotgun (requires Iron Key from chest1)
        Chest chest3 = new Chest("Iron Chest", new Point(6, 5), null, keyForChest3.getId(), true,
            new Item[] { 
                new Weapon("Super Puper Gun (Pushka)", shotgunType, 30),
                new Ammo("Shotgun Shells", shotgunType, 10),
                new Bandage("Advanced Bandage", 3, 40),
                keyForDoor3, // IMPROVED: This chest has Golden Key for exit door (alternative to boss fight)
            });

        // Room 4: Treasure chest (requires Golden Key)
        Chest treasureChest = new Chest("Golden Chest", new Point(8, 8), null, keyForDoor3.getId(), true,
            new Item[] { 
                new Weapon("Ultimate Weapon", "Ultimate", 30),
                new Ammo("Ultimate Ammo", "Ultimate", 50),
                new Bandage("Ultimate Medkit", 5, 80) 
            });

        // --- Create rooms and populate with entities ---
        Room room1 = new Room(1, new Door[]{door1}, new Chest[]{chest1}, new Entity[]{player, goblin});
        Room room2 = new Room(2, new Door[]{door1, door2}, new Chest[]{chest2}, new Entity[]{orc});
        // FIXED: Make sure door3 is included in Room 3
        Room room3 = new Room(3, new Door[]{door2, door3}, new Chest[]{chest3}, new Entity[]{zombie1, zombie2});
        Room room4 = new Room(4, new Door[]{door3, exitDoor}, new Chest[]{treasureChest}, new Entity[]{finalBoss});

        Map<Integer,Room> dungeon = new HashMap<>();
        dungeon.put(1, room1);
        dungeon.put(2, room2);
        dungeon.put(3, room3);
        dungeon.put(4, room4);
        dungeon.put(0, new Room(0, new Door[]{}, new Chest[]{}, new Entity[]{})); // Exit "room"

        // Start in room 1
        Room current = dungeon.get(1);
        player.setPlayerLocation(current);

        // --- Game introduction ---
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║                   DUNGEON CRAWLER                    ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("You wake up in a dimly lit dungeon. Can you escape?");
        System.out.println("\nControls:");
        System.out.println("- W/A/S/D: Move up/left/down/right");
        System.out.println("- O: Open doors or chests");
        System.out.println("- F: Fight nearby enemies");
        System.out.println("- I: View inventory");
        System.out.println("- H: Help/controls");
        System.out.println("- Q: Quit game");
        System.out.println("\nLegend:");
        System.out.println("- @: You");
        System.out.println("- m: Monster");
        System.out.println("- B: Boss");
        System.out.println("- D: Door");
        System.out.println("- C: Chest");
        System.out.println("- #: Wall");
        System.out.println("\nGood luck!");

        // --- ADDED: Display starter tip ---
        System.out.println("\n** TIP: You start with the Bronze Key to unlock the door to Room 2.");
        System.out.println("** Check your inventory with 'I' and look for the Wooden Chest in this room!");

        // --- Main game loop ---
        String input;
        boolean gameCompleted = false;
        
        do {
            current.updateEntityPositions();
            System.out.println("\n=== Room " + current.getRoomNumber() + " ===");
            printGrid(current.getGrid());
            
            if (current.getRoomNumber() == 0) {
                System.out.println("\nCongratulations! You've escaped the dungeon!");
                gameCompleted = true;
                break;
            }

            System.out.print("\nEnter action (W/A/S/D/O/F/I/H/Q): ");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            char action = Character.toLowerCase(input.charAt(0));

            switch (action) {
                case 'w': case 'a': case 's': case 'd':
                    // Movement
                    if (!player.move(action, current)) {
                        System.out.println("Can't move in that direction!");
                    }
                    break;
                    
                case 'f':
                    // Fight sequence
                    handleFight(player, current);
                    break;
                    
                case 'o':
                    // Open doors/chests
                    Room newRoom = handleOpenAction(player, current, dungeon);
                    if (newRoom != null && newRoom != current) {
                        current = newRoom;
                        
                        // Check if this is the boss room and first entry
                        if (current.getRoomNumber() == 4) {
                            Boss boss = null;
                            for (Entity entity : current.getEntities()) {
                                if (entity instanceof Boss) {
                                    boss = (Boss) entity;
                                    break;
                                }
                            }
                            
                            if (boss != null && boss.getHealth() > 0) {
                                System.out.println("\n▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
                                System.out.println("       WARNING: YOU'VE ENTERED THE BOSS ROOM!       ");
                                System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
                                System.out.println("\nThe " + boss.getName() + " approaches you menacingly!");
                                System.out.println("Prepare for a difficult battle!");
                            }
                        }
                        
                        // IMPROVED: Show tips when entering new rooms
                        if (current.getRoomNumber() == 2) {
                            System.out.println("\n** TIP: Look for the Steel Chest. It's unlocked and has better weapons!");
                            System.out.println("** The door to Room 3 can be opened with your crowbar.");
                        } else if (current.getRoomNumber() == 3) {
                            System.out.println("\n** TIP: There's an Iron Chest here that needs a key.");
                            System.out.println("** Find or defeat the Undead enemy to get the Silver Key to Room 4.");
                            // ADDED: Door to Room 4 tip
                            System.out.println("** The door to Room 4 is at the bottom of this room and requires the Silver Key.");
                        }
                    }
                    break;
                    
                case 'i':
                    // Show inventory
                    displayInventory(player);
                    break;
                    
                case 'h':
                    // Show help
                    displayHelp();
                    break;
                    
                case 'q':
                    // Quit game
                    System.out.println("Are you sure you want to quit? (Y/N)");
                    String confirm = scanner.nextLine().trim().toUpperCase();
                    if (!confirm.startsWith("Y")) {
                        input = "";  // Don't quit if not confirmed
                    }
                    break;
                
                // IMPROVED: Added heal command
                case 'b':
                    // Use bandage to heal
                    handleHealing(player);
                    break;
                    
                default:
                    System.out.println("Unknown command. Type 'H' for help.");
                    break;
            }
            
            // Check if player died
            if (player.getHealth() <= 0) {
                System.out.println("\n☠️ You have been defeated! Game over! ☠️");
                break;
            }
            
        } while (!input.equalsIgnoreCase("q") && !gameCompleted);

        if (gameCompleted) {
            System.out.println("\n🏆 You've completed the game! 🏆");
            System.out.println("You defeated the Dungeon Lord and escaped with your life and treasures!");
        }
        
        scanner.close();
        System.out.println("\nGame ended. Thanks for playing!");
    }

    /**
     * Handles the fight action when player presses 'F'
     */
    private static void handleFight(Player player, Room current) {
        String surroundings = player.checkPlayerSurroundings(current);
        if (!surroundings.contains("m") && !surroundings.contains("B")) {
            System.out.println("No enemies nearby to fight!");
            return;
        }
        
        // Find the actual nearby enemy
        Entity target = null;
        for (Entity entity : current.getEntities()) {
            if (entity instanceof Mob && entity.getHealth() > 0) {
                Point mobPos = entity.getPosition();
                Point playerPos = player.getPlayerPosition();
                int dx = Math.abs(playerPos.getX() - mobPos.getX());
                int dy = Math.abs(playerPos.getY() - mobPos.getY());
                
                if (dx <= 1 && dy <= 1) {
                    target = entity;
                    break;
                }
            }
        }
        
        if (target != null) {
            FightSequence fight = new FightSequence(player, (Mob)target);
            fight.startCombatLoop();
            
            // Remove dead mob from room
            if (target.getHealth() <= 0) {
                // IMPROVED: Show what the player got from defeating the enemy
                System.out.println("\nThe " + target.getName() + " has been defeated!");
                Item[] loot = ((Mob)target).getInventory();
                if (loot != null && loot.length > 0) {
                    System.out.println("You found:");
                    for (Item item : loot) {
                        if (item != null) {
                            System.out.println("- " + item.getName());
                        }
                    }
                    player.loot((Mob)target);
                }
                current.removeEntity(target);
            }
        } else {
            System.out.println("No enemies within attack range!");
        }
    }

    /**
     * Handles opening doors and chests when player presses 'O'
     * Returns the new room if player moved, otherwise returns the current room
     */
    private static Room handleOpenAction(Player player, Room current, Map<Integer, Room> dungeon) {
        String surroundings = player.checkPlayerSurroundings(current);
        boolean actionPerformed = false;

        // Check for nearby chests or doors
        if (surroundings.contains("c") || surroundings.contains("d")) {
            // Try to open chest first
            if (surroundings.contains("c")) {
                Chest targetChest = findNearbyChest(player, current);
                
                if (targetChest != null) {
                    handleChestInteraction(player, targetChest);
                    actionPerformed = true;
                }
            }

            // If no chest was opened, try doors
            if (!actionPerformed && surroundings.contains("d")) {
                Door targetDoor = findNearbyDoor(player, current);
                
                if (targetDoor != null) {
                    Room newRoom = handleDoorInteraction(player, targetDoor, current, dungeon);
                    if (newRoom != null && newRoom != current) {
                        return newRoom; // Player moved to new room
                    }
                    actionPerformed = true;
                }
            }

            // If no action was performed
            if (!actionPerformed) {
                System.out.println("Nothing here you can open.");
            }
        } else {
            System.out.println("No door or chest nearby to open!");
        }
        
        return current; // No room change
    }

    /**
     * Finds a chest near the player
     */
    private static Chest findNearbyChest(Player player, Room current) {
        for (Chest chest : current.getChests()) {
            Point chestPos = chest.getPosition();
            Point playerPos = player.getPlayerPosition();
            int dx = Math.abs(playerPos.getX() - chestPos.getX());
            int dy = Math.abs(playerPos.getY() - chestPos.getY());
            
            if (dx <= 1 && dy <= 1) {
                return chest;
            }
        }
        return null;
    }

    /**
     * Handles opening a chest with a key or crowbar and looting it
     */
    private static void handleChestInteraction(Player player, Chest chest) {
        if (chest.isLocked()) {
            System.out.println("This chest is locked.");
            
            // Try to find the key for this chest
            UUID keyId = player.findKeyForChest(chest);
            
            if (keyId != null) {
                chest.unlockChest(keyId);
                System.out.println("You used a key to unlock the chest!");
            } 
            // Try using crowbar if player has one
            else if (player.hasCrowbar() && chest.isLocked()) {
                chest.unlockChest(null); // Unlock without a key (crowbar)
                System.out.println("You forced the chest open with your crowbar!");
            }
        }
        
        // If chest is now unlocked (or was already unlocked)
        if (!chest.isLocked()) {
            // Loot the chest
            Item[] loot = chest.getInventory();
            if (loot != null && loot.length > 0) {
                System.out.println("You opened the chest and found:");
                for (Item item : loot) {
                    if (item != null) {
                        System.out.println("- " + item.getName());
                    }
                }
                player.loot(chest);
            } else {
                System.out.println("The chest is empty.");
            }
        } else {
            System.out.println("You couldn't find a way to open this chest.");
        }
    }

    /**
     * Finds a door near the player
     */
    private static Door findNearbyDoor(Player player, Room current) {
        for (Door door : current.getDoors()) {
            Point doorPos = door.getPositionByRoomNumber(current.getRoomNumber());
            Point playerPos = player.getPlayerPosition();
            
            if (doorPos != null) {
                int dx = Math.abs(playerPos.getX() - doorPos.getX());
                int dy = Math.abs(playerPos.getY() - doorPos.getY());
                
                if (dx <= 1 && dy <= 1) {
                    return door;
                }
            }
        }
        return null;
    }

    /**
     * Handles opening a door with a key or crowbar and moving to a new room
     * Returns the new room if successful
     */
    private static Room handleDoorInteraction(Player player, Door door, Room current, Map<Integer, Room> dungeon) {
        // Try to unlock the door if it's locked
        if (door.isLocked()) {
            System.out.println("This door is locked.");
            
            // Try to find the key for this door
            UUID keyId = player.findKeyForDoor(door);
            
            if (keyId != null) {
                door.unlockWithKey(keyId);
                System.out.println("You used a key to unlock the door!");
            } 
            // Try using crowbar if the door can be forced and player has one
            else if (door.canBeUnlockedByCrowbar() && player.hasCrowbar()) {
                door.unlockWithCrowbar();
                System.out.println("You pried the door open with your crowbar!");
            } else {
                System.out.println("You don't have the right key or tools to open this door.");
                return current;
            }
        }
        
        // If door is now unlocked (or was already unlocked)
        if (!door.isLocked()) {
            // Find the destination room and position
            Pair<Integer, Point> destination = null;
            for (Pair<Integer, Point> end : door.getRoomsAndPositions()) {
                if (!end.getLeft().equals(current.getRoomNumber())) {
                    destination = end;
                    break;
                }
            }
            
            if (destination != null) {
                Room nextRoom = dungeon.get(destination.getLeft());
                Point destPoint = destination.getRight();
                
                if (nextRoom != null) {
                    player.moveToNewRoom(nextRoom, destPoint);
                    System.out.println("You pass through the door...");
                    return nextRoom;
                }
            }
        }
        
        return current;
    }

    /**
     * IMPROVED: Added method to handle healing
     */
    private static void handleHealing(Player player) {
        Item[] inventory = player.getInventory();
        Bandage bestBandage = null;
        
        // Find the best bandage in inventory
        for (Item item : inventory) {
            if (item instanceof Bandage) {
                Bandage bandage = (Bandage) item;
                if (bestBandage == null || bandage.getHealingAmount() > bestBandage.getHealingAmount()) {
                    bestBandage = bandage;
                }
            }
        }
        
        if (bestBandage != null) {
            int healthBefore = player.getHealth();
            player.heal(bestBandage);
            bestBandage.useBandage(1); // Use the bandage
            System.out.println("You used " + bestBandage.getName() + " and healed " + 
                              (player.getHealth() - healthBefore) + " health points.");
            System.out.println("Current health: " + player.getHealth());
        } else {
            System.out.println("You don't have any bandages to heal yourself!");
        }
    }

    /**
     * Displays the player's inventory
     */
    private static void displayInventory(Player player) {
        Item[] inventory = player.getInventory();
        Weapon currentWeapon = player.getCurrentWeapon();
        
        System.out.println("\n=== INVENTORY ===");
        System.out.println("Health: " + player.getHealth());
        System.out.println("Current weapon: " + (currentWeapon != null ? currentWeapon.getName() : "Unarmed"));
        
        // Count items by type
        int weaponCount = 0;
        int ammoCount = 0;
        int bandageCount = 0;
        int keyCount = 0;
        int otherCount = 0;
        
        // First, collect all non-null items
        for (Item item : inventory) {
            if (item != null) {
                if (item instanceof Weapon) weaponCount++;
                else if (item instanceof Ammo) ammoCount++;
                else if (item instanceof Bandage) bandageCount++;
                else if (item instanceof Key) keyCount++;
                else otherCount++;
            }
        }
        
        // Display weapons
        if (weaponCount > 0) {
            System.out.println("\nWEAPONS:");
            for (Item item : inventory) {
                if (item instanceof Weapon) {
                    Weapon weapon = (Weapon) item;
                    Ammo ammo = player.getAmmoFromInventory(weapon.getType());
                    String ammoInfo = ammo != null ? " (Ammo: " + ammo.getQuantity() + ")" : " (No ammo)";
                    System.out.println("- " + weapon.getName() + " | Damage: " + weapon.getDamage() + ammoInfo);
                }
            }
        }
        
        // Display ammo
        if (ammoCount > 0) {
            System.out.println("\nAMMO:");
            for (Item item : inventory) {
                if (item instanceof Ammo) {
                    Ammo ammo = (Ammo) item;
                    System.out.println("- " + ammo.getName() + " | Type: " + ammo.getWeaponType() + 
                                      " | Quantity: " + ammo.getQuantity());
                }
            }
        }
        
        // Display bandages
        if (bandageCount > 0) {
            System.out.println("\nMEDICAL:");
            for (Item item : inventory) {
                if (item instanceof Bandage) {
                    Bandage bandage = (Bandage) item;
                    System.out.println("- " + bandage.getName() + " | Healing: " + bandage.getHealingAmount() + 
                                      " | Quantity: " + bandage.getQuantity());
                }
            }
        }
        
        // Display keys
        if (keyCount > 0) {
            System.out.println("\nKEYS:");
            for (Item item : inventory) {
                if (item instanceof Key) {
                    System.out.println("- " + item.getName());
                }
            }
        }
        
        // Display other items
        if (otherCount > 0) {
            System.out.println("\nOTHER ITEMS:");
            for (Item item : inventory) {
                if (item != null && !(item instanceof Weapon) && !(item instanceof Ammo) && 
                    !(item instanceof Bandage) && !(item instanceof Key)) {
                    System.out.println("- " + item.getName());
                }
            }
        }
        
        if (weaponCount + ammoCount + bandageCount + keyCount + otherCount == 0) {
            System.out.println("Your inventory is empty!");
        }
    }

    /**
     * Displays help information
     */
    private static void displayHelp() {
        System.out.println("\n=== HELP ===");
        System.out.println("Controls:");
        System.out.println("- W/A/S/D: Move up/left/down/right");
        System.out.println("- O: Open doors or chests");
        System.out.println("- F: Fight nearby enemies");
        System.out.println("- B: Use bandage to heal yourself"); // IMPROVED: Added healing control
        System.out.println("- I: View inventory");
        System.out.println("- H: Show this help");
        System.out.println("- Q: Quit game");
        
        System.out.println("\nLegend:");
        System.out.println("- @: You (player)");
        System.out.println("- m: Monster/enemy");
        System.out.println("- B: Boss");
        System.out.println("- D: Door");
        System.out.println("- C: Chest");
        System.out.println("- #: Wall");
    }

    /**
     * Prints the room grid
     */
    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char c : row) System.out.print(c + " ");
            System.out.println();
        }
    }
}