// Main.java
import java.util.Arrays;
import java.util.Scanner;
import entities.Player;
import entities.Entity;
import util.Point;
import items.Item;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        int xMatrix = 10;
        int yMatrix = 10;

        Player player = new Player("Hero", 100, new Item[10], 
            new Point(5, 5), null, 10);

        System.out.println("10x10 Grid Player Movement");
        System.out.println("Use WASD to move, Q to quit");

        do {
            // Print the grid with player
            printGrid(player, xMatrix, yMatrix);

            // Get input
            System.out.print("Enter move (W/A/S/D/Q): ");
            input = scanner.nextLine().trim();

            if(input.equalsIgnoreCase("q")) break;

            if(input.length() > 0) {
                char dir = input.charAt(0);
                boolean moved = player.move(dir, xMatrix, yMatrix);
                
                if(!moved) {
                    System.out.println("Can't move beyond grid boundaries!");
                }
            }
        } while(true);

        scanner.close();
        System.out.println("Game exited!");
    }

    // 👇 New method
    public static void printGrid(Player player, int width, int height) {
        char[][] grid = new char[height][width]; // note: rows=height, cols=width
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Border cells are walls '#'
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    grid[i][j] = '#';
                } else {
                    grid[i][j] = '.';
                }
            }
        }
        
        Point pos = player.getPlayerPosition();
        // Place player inside the grid only if inside bounds (avoid ArrayIndexOutOfBounds)
        if (pos.getY() > 0 && pos.getY() < height - 1 && pos.getX() > 0 && pos.getX() < width - 1) {
            grid[pos.getY()][pos.getX()] = '@';
        }
        
        // Print the grid
        for (char[] row : grid) {
            for (char cell : row) System.out.print(cell + " ");
            System.out.println();
        }
    }    
}
