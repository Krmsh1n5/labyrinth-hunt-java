package world;

import java.util.UUID;
import util.Pair;
import util.Point;
import items.Key;
import items.Crowbar;

public class Door {
    private final UUID id = UUID.randomUUID();
    private final UUID keyId;
    private final Point position;
    private final Pair<Room, Room> rooms;
    private boolean isLocked;
    private boolean isCrowbarAccessible;

    public Door(UUID keyId, Point position, Pair<Room, Room> rooms, boolean isLocked, boolean isCrowbarAccessible) {
        this.keyId = keyId;
        this.position = position;
        this.rooms = rooms;
        this.isLocked = isLocked;
        this.isCrowbarAccessible = isCrowbarAccessible;
    }

    // Getters
    public UUID getId() {
        return id;
    }
    public UUID getKeyId() {
        return keyId;
    }
    public Point getPosition() {
        return position;
    }
    public Pair<Room, Room> getRooms() {
        return rooms;
    }
    public boolean isLocked() {
        return isLocked;
    }
    public boolean isCrowbarAccessible() {
        return isCrowbarAccessible;
    }
    // Setters
    // public void setLocked(boolean locked) {
    //     isLocked = locked;
    // }

    // Methods
    public boolean canBeUnlockedByKey(UUID keyId) {
        return keyId.equals(keyId);
    }

    public boolean canBeUnlockedByCrowbar() {
        return isCrowbarAccessible;
    }

    public boolean unlockWithKey(UUID keyId) {
        if (canBeUnlockedByKey(keyId)) {
            isLocked = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean unlockWithCrowbar() {
        if (canBeUnlockedByCrowbar()) {
            this.isLocked = false;
            return true;
        } else {
            return false;
        }
    }
}