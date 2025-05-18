package items;

import java.util.UUID;
import world.Door;
import world.Room;
import util.Point;
import util.Pair;

public class Key extends Item {
    private final UUID id = UUID.randomUUID();
    private final UUID targetId;

    public Key(String name, Point position, Room location, UUID targetId) {
        super(name, position, location);
        this.targetId = targetId;
    }

    // Getters
    public UUID getId() {
        return id;
    }
    public UUID getTargetId() {
        return targetId;
    }

    // Methods
}