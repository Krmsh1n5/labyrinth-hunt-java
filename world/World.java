package world;

import java.util.Arrays;
import items.Item;
import entities.Entity;

public class World {
    private final Entity[] entities;
    private final Room[] rooms;
    private final Door[] doors;
    private final Chest[] chests;
    private final Item[] items;

    public World(Entity[] entities, Room[] rooms, Door[] doors, Chest[] chest, Item[] items) {
        this.entities = entities;
        this.rooms = rooms;
        this.doors = doors;
        this.chests = chest;
        this.items = items;
    }

    // Getters
    public Entity[] getEntities() {
        return entities;
    }
    public Room[] getRooms() {
        return rooms;
    }
    public Door[] getDoors() {
        return doors;
    }
    public Chest[] getChests() {
        return chests;
    }
    public Item[] getItems() {
        return items;
    }
    // Setters
    // public void setEntities(Entity[] entities) {
    //     this.entities = entities;
    // }
    // public void setRooms(Room[] rooms) {
    //     this.rooms = rooms;
    // }
    // public void setItems(Item[] items) {
    //     this.items = items;
    // }

    // Methods
}