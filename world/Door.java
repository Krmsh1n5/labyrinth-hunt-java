package world;

import java.util.UUID;
import util.Pair;
import items.Key;
import items.Crowbar;

public class Door {
    private final UUID id = UUID.randomUUID();
    private final UUID keyId;
    private final Pair<UUID, UUID> rooms;
    private boolean isLocked;
    private int strength;

    public Door( UUID keyId, Pair<UUID, UUID> rooms, boolean isLocked, int strength) {
        this.keyId = keyId;
        this.rooms = rooms;
        this.isLocked = isLocked;
        this.strength = strength;
    }

    // Getters
    public UUID getId() {
        return id;
    }
    public UUID getKeyId() {
        return keyId;
    }
    public Pair<UUID, UUID> getRooms() {
        return rooms;
    }
    public boolean isLocked() {
        return isLocked;
    }
    public int getStrength() {
        return strength;
    }
    // Setters
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }

    // Methods
    public boolean attemptBreak(Crowbar crowbar) {
        return crowbar.canBreakDoor(this);
    }

    public void open() {
        if(this.isLocked) {
            this.isLocked = true;
            System.out.println("Door is locked!");
        } else {
            System.out.println("Door's already opened!");
        }
    }
}