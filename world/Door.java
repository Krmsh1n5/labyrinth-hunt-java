package world;

import java.util.UUID;
import util.Pair;
import util.Point;
import items.Key;
import items.Crowbar;

public class Door {
    private final UUID id = UUID.randomUUID();
    private final UUID keyId;
    private final Pair<Room, Point>[] roomsAndPositions;
    private boolean isLocked;
    private boolean isCrowbarAccessible;

    public Door(UUID keyId, Pair<Room, Point>[] roomsAndPositions, boolean isLocked, boolean isCrowbarAccessible) {
        this.keyId = keyId;
        this.roomsAndPositions = roomsAndPositions;
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
    public Room[] getRooms() {
        Room[] roomArray = new Room[roomsAndPositions.length];
        for (int i = 0; i < roomsAndPositions.length; i++) {
            roomArray[i] = roomsAndPositions[i].getLeft();
        }
        return roomArray;
    }
    public Point getPositionbyRoom(Room room) {
        for (Pair<Room, Point> pair : roomsAndPositions) {
            if (pair.getLeft().equals(room)) {
                return pair.getRight();
            }
        }
        return null;
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