import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

import entities.Player;
import entities.Entity;
import util.Pair;
import util.Point;
import world.Chest;
import world.Door;
import world.Room;
import items.Item;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        Player player = new Player("Hero", 100, new Item[10], 
            new Point(3, 4), null, 10);

        Door[] doors = new Door[] {
            new Door(UUID.randomUUID(), new Point(1, 1), new Pair<>(null, null), true, false)
        };
        Chest[] chests = new Chest[] {
            new Chest("Treasure", new Point(3, 2), null, UUID.randomUUID(), true, new Item[0])
        };

        Room room = new Room(1, doors, chests, new Entity[] { player });
        char[][] grid = room.getGrid();

        System.out.println("5x8 Grid Player Movement");
        System.out.println("Use WASD to move, Q to quit");

        do {
            // Update room grid and print
            room.updateEntityPositions();
            printGrid(grid);

            System.out.print("Enter move (W/A/S/D/Q): ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) break;

            if (!input.isEmpty()) {
                char dir = input.charAt(0);
                boolean moved = player.move(dir, room);
                
                if (!moved) {
                    System.out.println("Can't move beyond grid boundaries!");
                }
            }
        } while (true);

        scanner.close();
        System.out.println("Game exited!");
    }

    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

}