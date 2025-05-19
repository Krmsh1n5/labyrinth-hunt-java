package items;

import java.util.UUID;
import world.Door;
import world.Room;
import util.Point;
import util.Pair;

public class Key extends Item {
    private final UUID id = UUID.randomUUID();

    // Getters
    public UUID getId() {
        return id;
    }

    // Methods
}