package world;

import java.util.Arrays;
import items.Item;
import entities.Entity;

public class World {
    private Entity[] entities = new Entity[0];
    private Room[] rooms = new Room[0];
    private Item[] items = new Item[0];

    public void addEntity(Entity entity) {
        entities = Arrays.copyOf(entities, entities.length + 1);
        entities[entities.length - 1] = entity;
    }

    public void addRoom(Room room) {
        rooms = Arrays.copyOf(rooms, rooms.length + 1);
        rooms[rooms.length - 1] = room;
    }

    public void addItem(Item item) {
        items = Arrays.copyOf(items, items.length + 1);
        items[items.length - 1] = item;
    }

    public void removeEntity(Entity entity) {
        entities = Arrays.stream(entities)
                .filter(e -> !e.equals(entity))
                .toArray(Entity[]::new);
    }

    public void removeRoom(Room room) {
        rooms = Arrays.stream(rooms)
                .filter(r -> !r.equals(room))
                .toArray(Room[]::new);
    }

    public void removeItem(Item item) {
        items = Arrays.stream(items)
                .filter(i -> !i.equals(item))
                .toArray(Item[]::new);
    }
}