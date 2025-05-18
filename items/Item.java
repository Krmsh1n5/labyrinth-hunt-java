package items;

import util.Point;
import world.Room;

public class Item {
    private String name;
    private Point position;
    private Room location;

    public Item(String name, Point position, Room location) {
        this.name = name;
        this.position = position;
        this.location = location;
    }

    // Getters and setters
    public String getName() { return name; }
    public Point getPosition() { return position; }
    public Room getLocation() { return location; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setPosition(Point position) { this.position = position; }
    public void setLocation(Room location) { this.location = location; }

    // Methods
}