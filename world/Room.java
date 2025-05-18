package world;

import java.util.Arrays;
import java.util.UUID;
import entities.Entity;
import util.Activatable;
import util.Pair;

public class Room {
    private final UUID id = UUID.randomUUID();
    private final UUID[] doors;
    private Entity[] entities = new Entity[0];
    private Activatable[] activatables = new Activatable[0];
    private String outlay;

    public Room(UUID[] doors, String outlay, Entity[] entities, Activatable[] activatables) {
        this.doors = doors;
        this.outlay = outlay;
        this.entities = entities;
        this.activatables = activatables;
    }

    // Getters
    public UUID getId() {
        return id;
    }
    public UUID[] getDoors() {
        return doors;
    }
    public Entity[] getEntities() {
        return entities;
    }
    public Activatable[] getActivatables() {
        return activatables;
    }
    public String getOutlay() {
        return outlay;
    }
    // Setters
    public void setEntities(Entity[] entities) {
        this.entities = entities;
    }
    public void setActivatables(Activatable[] activatables) {
        this.activatables = activatables;
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

    public void addActivatable(Activatable activatable) {
        activatables = Arrays.copyOf(activatables, activatables.length + 1);
        activatables[activatables.length - 1] = activatable;
    }

    public void removeActivatable(Activatable activatable) {
        activatables = Arrays.stream(activatables)
                .filter(a -> !a.equals(activatable))
                .toArray(Activatable[]::new);
    }
}