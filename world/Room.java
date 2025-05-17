package world;

import entities;
import util;

public class Room {
    private Entity[] entities = new Entity[0];
    private Activatable[] activatables = new Activatable[0];
    private String outlay;

    public Room(String outlay) {
        this.outlay = outlay;
    }

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