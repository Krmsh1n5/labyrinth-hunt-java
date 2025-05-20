import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import entities.Player;
import entities.Entity;
import entities.Mob;
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

        // --- set up player, mobs, doors, chests, rooms as before ---
        Player player = new Player("Hero", 100, new Item[150], new Point(3, 4), null, 10);
        // Mob goblin = new Mob("Goblin", 50, new Item[0], new Point(2, 2), null, 5);
        String firstType = "Pistol";
        String secondType = "Super Puper Pushka";
        Bandage bandage = new Bandage("Basic Bandage", 5, 15);
        Ammo ammo = new Ammo("Pistol Ammo", firstType, 10);
        Weapon weapon = new Weapon("My Pistol", firstType, 5);
        Ammo secondAmmo = new Ammo("Super Puper Pushka Ammo", secondType, 30);
        Weapon superWeapon = new Weapon("My Super Puper Pushka", secondType, 13);
        Crowbar crowbar = new Crowbar("Crowbar");

        Key keyForDoor1 = new Key("Key for Door 1");
        Key keyForDoor2 = new Key("Key for Door 2");

        Item[] inventory = new Item[] {
            bandage,
            ammo,
            weapon,
            secondAmmo,
            superWeapon,
            crowbar,
            keyForDoor1,
            keyForDoor2
        };

        player.setInventory(inventory);
        player.setCurrentWeapon(superWeapon);

        Mob goblin = new Mob("Goblin", 50, new Item[1], new Point(2, 2), null, 5);
        Weapon goblinWeapon = new Weapon("New Pistol", "Pistol", 5);
        goblin.setInventory(new Item[] {
            goblinWeapon,
        });

        UUID door1Id = UUID.randomUUID();
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d1ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{ new Pair<>(1, new Point(0,3)), new Pair<>(3, new Point(6,3)) };
        Door door1 = new Door(door1Id, d1ends, true, false);

        UUID door2Id = keyForDoor2.getId();
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d2ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{ new Pair<>(1, new Point(9,2)), new Pair<>(2, new Point(1,0)) };
        Door door2 = new Door(door2Id, d2ends, true, false);

        // New doors for rooms 2-3 and 3-4
        UUID door3Id = keyForDoor1.getId();
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d3ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{
                new Pair<>(2, new Point(3, 9)), // Room 2 right border
                new Pair<>(3, new Point(3, 0))  // Room 3 left border
            };
        Door door3 = new Door(door3Id, d3ends, true, false);

        UUID door4Id = UUID.randomUUID();
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d4ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{
                new Pair<>(3, new Point(6, 5)), // Room 3 bottom border
                new Pair<>(4, new Point(0, 5))  // Room 4 top border
            };
        Door door4 = new Door(door4Id, d4ends, false, true);

        Chest chest1 = new Chest("Gold",   new Point(3,2), null, keyForDoor1.getId(), true,  new Item[0]);
        Chest chest2 = new Chest("Potion", new Point(5,5), null, UUID.randomUUID(), false, new Item[0]);

        Room room1 = new Room(1, new Door[]{door1, door2}, new Chest[]{chest1}, new Entity[]{player, goblin});
        Room room2 = new Room(2, new Door[]{door2, door3}, new Chest[]{chest2}, new Entity[]{});
        Room room3 = new Room(3, new Door[]{door3, door4}, new Chest[]{}, new Entity[]{});
        Room room4 = new Room(4, new Door[]{door4}, new Chest[]{}, new Entity[]{});

        Map<Integer,Room> dungeon = new HashMap<>();
        dungeon.put(1, room1);
        dungeon.put(2, room2);
        dungeon.put(3, room3);
        dungeon.put(4, room4);

        // start in room 1
        Room current = dungeon.get(1);
        player.setPlayerLocation(current);
        current.addEntity(player);

        System.out.println("5x8 Grid Player Movement");
        System.out.println("Use WASD to move, O to open doors, F to Fight, Q to quit");

        // --- main loop ---
 
        String input;
        do {
            current.updateEntityPositions();
            printGrid(current.getGrid());

            System.out.print("Enter move (W/A/S/D), Open (O), Fight (F), Quit (Q): ");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            char dir = Character.toLowerCase(input.charAt(0));

            // 1) Try to move
            boolean moved = false;
            if ("wasd".indexOf(dir) >= 0) {
                moved = player.move(dir, current);
            }
            

            // 2) If move failed or was not a direction, check other actions
            if (dir == 'f') {
                String surroundings = player.checkPlayerSurroundings(current);
                if(surroundings.contains("m")) {  // Check if 'm' is present anywhere in the string
                    // Find the actual nearby mob (not just using 'goblin')
                    Mob target = null;
                    for (Entity entity : current.getEntities()) {
                        if (entity instanceof Mob) {
                            Point mobPos = entity.getPosition();
                            Point playerPos = player.getPlayerPosition();
                            int dx = Math.abs(playerPos.getX() - mobPos.getX());
                            int dy = Math.abs(playerPos.getY() - mobPos.getY());
                            
                            if (dx <= 1 && dy <= 1 && entity.getHealth() > 0) {
                                target = (Mob) entity;
                                break;
                            }
                        }
                    }
                    
                    if (target != null) {
                        FightSequence fight = new FightSequence(player, target);
                        fight.startCombatLoop();
                        
                        // Remove dead mob from room
                        if (target.getHealth() <= 0) {
                            current.removeEntity(target);
                        }
                    }
                } else {
                    System.out.println("No mob nearby to fight!");
                }
            }
            else if (dir == 'o') {
                String surroundings = player.checkPlayerSurroundings(current);
                boolean actionDone = false;

                // do we have a chest or a door nearby?
                if (surroundings.contains("c") || surroundings.contains("d")) {

                    // 1) Try chest first (if any)
                    if (surroundings.contains("c")) {
                        Chest targetChest = null;
                        for (Chest chest : current.getChests()) {
                            Point chestPos = chest.getPosition();
                            Point playerPos = player.getPlayerPosition();
                            int dx = Math.abs(playerPos.getX() - chestPos.getX());
                            int dy = Math.abs(playerPos.getY() - chestPos.getY());
                            if (dx <= 1 && dy <= 1) {
                                targetChest = chest;
                                break;
                            }
                        }

                        if (targetChest != null) {
                            // open() will unlock if you have the right key (or crowbar, assuming you extend open())
                            if (player.open(targetChest)) {
                                player.loot(targetChest);
                                System.out.println("You opened and looted the chest!");
                            } else {
                                System.out.println("The chest is locked and you have no way to open it.");
                            }
                            actionDone = true;
                        }
                    }

                    // 2) If no chest action (or it failed), try doors
                    // --- inside your 'O' handler in Main.java ---
                    if (!actionDone && surroundings.contains("d")) {
                        boolean doorOpened = false;
                        for (Door door : current.getDoors()) {
                            Point doorPos   = door.getPositionByRoomNumber(current.getRoomNumber());
                            Point playerPos = player.getPlayerPosition();
                            if (Math.abs(playerPos.getX() - doorPos.getX()) > 1 ||
                                Math.abs(playerPos.getY() - doorPos.getY()) > 1) {
                                continue;
                            }

                            // 1) Try to open (key / crowbar)
                            if (door.isLocked()) {
                                UUID keyId = player.findKeyForDoor(door);
                                if (keyId != null) {
                                    door.unlockWithKey(keyId);
                                    System.out.println("You used the key to unlock the door.");
                                }
                                else if (door.canBeUnlockedByCrowbar() && player.hasCrowbar()) {
                                    door.unlockWithCrowbar();
                                    System.out.println("You pried the door open with your crowbar.");
                                }
                                else {
                                    System.out.println("The door is locked. You need the right key or a crowbar.");
                                    actionDone = true;
                                    break;
                                }
                            }

                            // 2) Door is now unlocked → find the other side
                            Pair<Integer,Point> exit = null;
                            for (Pair<Integer,Point> end : door.getRoomsAndPositions()) {
                                if (!end.getLeft().equals(current.getRoomNumber())) {
                                    exit = end;
                                    break;
                                }
                            }

                            // 3) Move player into the new room
                            if (exit != null) {
                                Room next   = dungeon.get(exit.getLeft());
                                Point dest  = exit.getRight();
                                player.moveToNewRoom(next, dest);
                                current = next;
                                System.out.println("Entered room " + current.getRoomNumber());
                                doorOpened = true;
                                actionDone  = true;
                                break;
                            }
                        }

                        if (!doorOpened) {
                            System.out.println("You can't open any nearby door.");
                            actionDone = true;
                        }
                    }


                    // 3) Neither chest nor door could be opened
                    if (!actionDone) {
                        System.out.println("Nothing here you can open.");
                    }

                } else {
                    System.out.println("No door or chest nearby to open!");
                }
            }

            else if (!moved) {
                // neither moved nor attacked nor opened
                System.out.println("Can't move beyond grid boundaries!");
            }

        } while (!input.equalsIgnoreCase("q"));

        scanner.close();
        System.out.println("Game exited!");
    }

    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char c : row) System.out.print(c + " ");
            System.out.println();
        }
    }
}
