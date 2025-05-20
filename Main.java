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

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- set up player, mobs, doors, chests, rooms as before ---
        Player player = new Player("Hero", 100, new Item[10], new Point(3, 4), null, 10);
        Mob goblin = new Mob("Goblin", 50, new Item[0], new Point(2, 2), null, 5);

        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d1ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{ new Pair<>(1, new Point(0,3)), new Pair<>(2, new Point(4,3)) };
        @SuppressWarnings("unchecked")
        Pair<Integer,Point>[] d2ends = (Pair<Integer,Point>[])
            new Pair<?,?>[]{ new Pair<>(1, new Point(9,2)), new Pair<>(2, new Point(1,0)) };

        Door door1 = new Door(UUID.randomUUID(), d1ends, true, false);
        Door door2 = new Door(UUID.randomUUID(), d2ends, false, true);

        Chest chest1 = new Chest("Gold",   new Point(3,2), null, UUID.randomUUID(), true,  new Item[0]);
        Chest chest2 = new Chest("Potion", new Point(5,5), null, UUID.randomUUID(), false, new Item[0]);

        Door[] room1Doors   = { door1, door2 };
        Chest[] room1Chests = { chest1 };
        Entity[] room1Ents  = { player, goblin };

        Door[] room2Doors   = { door2 };
        Chest[] room2Chests = { chest2 };
        Entity[] room2Ents  = { /* more mobs if you like */ };

        Map<Integer,Room> dungeon = new HashMap<>();
        dungeon.put(1, new Room(1, room1Doors, room1Chests, room1Ents));
        dungeon.put(2, new Room(2, room2Doors, room2Chests, room2Ents));

        // start in room 1
        Room current = dungeon.get(1);
        player.setPlayerLocation(current);
        current.addEntity(player);

        System.out.println("5x8 Grid Player Movement");
        System.out.println("Use WASD to move, O to open doors, A to attack, Q to quit");

        // --- main loop ---
        String input;
        String surroundings;
        do {
            current.updateEntityPositions();
            printGrid(current.getGrid());

            System.out.print("Enter move (W/A/S/D), Open (O), Attack (A), Quit (Q): ");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            char dir = Character.toLowerCase(input.charAt(0));

            // 1) Try to move
            boolean moved = false;
            if ("wasd".indexOf(dir) >= 0) {
                moved = player.move(dir, current);
            }
            surroundings = player.checkPlayerSurroundings(current);

            // 2) If move failed or was not a direction, check other actions
            if (dir == 'a') {
                System.out.println("Attacking mob...");
            }
            else if (dir == 'o') {
                boolean doorOpened = false;
                for (Door door : current.getDoors()) {
                    Point doorPos = door.getPositionByRoomNumber(current.getRoomNumber());
                    Point playerPos = player.getPlayerPosition();
                    int dx = Math.abs(playerPos.getX() - doorPos.getX());
                    int dy = Math.abs(playerPos.getY() - doorPos.getY());

                    if (dx <= 1 && dy <= 1) { // Player is near the door
                        Pair<Integer, Point> otherEnd = player.interactWithDoor(door, current);
                        if (otherEnd != null) {
                            int newRoomNumber = otherEnd.getLeft();
                            Point newPosition = otherEnd.getRight();
                            Room newRoom = dungeon.get(newRoomNumber);

                            // Validate new room and position
                            if (newRoom != null) {
                                char[][] newGrid = newRoom.getGrid();
                                if (newPosition.getY() >= 0 && newPosition.getY() < newGrid.length &&
                                    newPosition.getX() >= 0 && newPosition.getX() < newGrid[0].length) {
                                    
                                    // Move player to the new room
                                    player.moveToNewRoom(newRoom, newPosition);
                                    current = newRoom; // Update current room reference
                                    System.out.println("Entered room " + newRoomNumber);
                                    doorOpened = true;
                                    break;
                                } else {
                                    System.out.println("Invalid door position in new room!");
                                }
                            } else {
                                System.out.println("The door leads nowhere!");
                            }
                        }
                    }
                }
                if (!doorOpened) System.out.println("No door nearby or couldn't open.");
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
