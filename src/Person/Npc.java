package Person;

import Room.Room;

/**
 * @description:
 * @author: li yi
 * @date: Created in 2020/5/13 22:20
 * @version: ${VERSION}
 * @modified By:
 */
public class Npc extends Person{
    private String description;

    public Npc(int damage, int health, int armour, boolean live, Room location,String description) {
        super(damage, health, armour, live, location);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
