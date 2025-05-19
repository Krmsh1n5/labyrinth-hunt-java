package items;

import util.Point;
import world.Room;

public class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() { return name; }
    
    // Setters
    public void setName(String name) { this.name = name; }
}