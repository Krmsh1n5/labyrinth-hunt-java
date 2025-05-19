package world;

import java.util.Arrays;
import java.util.UUID;
import entities.Entity;
import util.Activatable;
import util.Pair;

public class Room {
    private final UUID id = UUID.randomUUID();
    private final Door[] doors;
    private Entity[] entities = new Entity[0];
    private String outlay;

    public Room(Door[] doors, String outlay, Entity[] entities) {
        this.doors = doors;
        this.outlay = outlay;
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

    public String getOutlay() {
        return outlay;
    }
    // Setters
    public void setEntities(Entity[] entities) {
        this.entities = entities;
    }

    public void setOutlay(String outlay) {
        this.outlay = outlay;
    }

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