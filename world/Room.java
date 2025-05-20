package world;

import java.util.Arrays;
import java.util.UUID;
import entities.Entity;
import util.Point;
import util.Pair;

public class Room {
    private final UUID id = UUID.randomUUID();
    private final Pair<Integer, Integer> size = new Pair<>(5, 8); // <rows, columns>
    private final Door[] doors;
    private final Chest[] chests;
    private Entity[] entities = new Entity[0];

    public Room(Pair<Integer, Integer> size, Door[] doors, Chest[] chests, Entity[] entities) {
        this.doors = doors;
        this.chests = chests;
        this.entities = entities;
    }

    // Getters
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