package world;

import java.util.Arrays;
import items.Item;
import entities.Entity;

public class World {
    private Entity[] entities = new Entity[0];
    private Room[] rooms = new Room[0];
    private Item[] items = new Item[0];

    public World(Entity[] entities, Room[] rooms, Item[] items) {
        this.entities = entities;
        this.rooms = rooms;
        this.items = items;
    }

    // Getters
    public Entity[] getEntities() {
        return entities;
    }
    public Room[] getRooms() {
        return rooms;
    }
    public Item[] getItems() {
        return items;
    }
    // Setters
    public void setEntities(Entity[] entities) {
        this.entities = entities;
    }
    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }
    public void setItems(Item[] items) {
        this.items = items;
    }

    // Methods
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