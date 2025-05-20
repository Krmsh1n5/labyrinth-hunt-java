package world;

import java.util.Arrays;
import java.util.UUID;
import entities.Entity;
import util.Point;
import util.Pair;

public class Room {
    private final int roomNumber;
    private final UUID id = UUID.randomUUID();
    private final Pair<Integer, Integer> size = new Pair<>(6, 9); // <rows, columns>
    private final Door[] doors;
    private final Chest[] chests;
    private Entity[] entities = new Entity[0];
    private char[][] grid;

    public Room(int roomNumber, Door[] doors, Chest[] chests, Entity[] entities) {
        this.roomNumber = roomNumber;
        this.doors = doors;
        this.chests = chests;
        this.entities = entities;
        initializeGrid();
    }

        private void initializeGrid() {
        grid = new char[size.getLeft()][size.getRight()];
        resetGrid(); // Reset to walls, doors, chests
        updateEntityPositions(); // Add entities
    }

    private void resetGrid() {
        int rows = size.getLeft();
        int cols = size.getRight();

        // Reset walls and floor
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    grid[i][j] = '#'; // Wall
                } else {
                    grid[i][j] = '.'; // Floor
                }
            }
        }

        // Re-add doors
        for (Door door : doors) {
            Point pos = door.getPosition();
            if (isInBounds(pos)) grid[pos.getY()][pos.getX()] = 'D';
        }

        // Re-add chests
        for (Chest chest : chests) {
            Point pos = chest.getPosition();
            if (isInBounds(pos)) grid[pos.getY()][pos.getX()] = 'C';
        }
    }

    public void updateEntityPositions() {
        resetGrid(); // Clear old entity positions
        for (Entity entity : entities) {
            Point pos = entity.getPosition();
            if (isInBounds(pos)) grid[pos.getY()][pos.getX()] = entity.getSymbol();
        }
    }

    private boolean isInBounds(Point pos) {
        return pos.getX() >= 0 && pos.getX() < size.getRight() && 
               pos.getY() >= 0 && pos.getY() < size.getLeft();
    }

    public char[][] getGrid() {
        return grid;
    }

    // Getters
    public int getRoomNumber() {
        return roomNumber;
    }
    public UUID getId() {
        return id;
    }
    public Door[] getDoors() {
        return doors;
    }
    public Entity[] getEntities() {
        return entities;
    }
    public Chest[] getChests() {
        return chests;
    }
    public Pair<Integer, Integer> getSize() {
        return size;
    }

    // Setters
    // public void setEntities(Entity[] entities) {
    //     this.entities = entities;
    // }

    // Methods
    public void addEntity(Entity entity) {
        entities = Arrays.copyOf(entities, entities.length + 1);
        entities[entities.length - 1] = entity;
    }

    public void removeEntity(Entity entity) {
        entities = Arrays.stream(entities)
                .filter(e -> !e.equals(entity))
                .toArray(Entity[]::new);
    }
}