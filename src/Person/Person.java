package Person;

import Room.Room;

/**
 * @description:
 * @author: li yi
 * @date: Created in 2020/5/12 23:52
 * @version: ${VERSION}
 * @modified By:
 */
public class Person {
    private int health;
    private int armour;
    int damage;
    int property;
    private boolean live = false;
    private Room location;

    public Person(int health, int damage, int property, int armour, boolean live, Room location) {
        super();
        this.health = health;
        this.damage = damage;
        this.property = property;
        this.armour = armour;
        this.live = live;
        this.location = location;

    }

    public Person(int damage, int health, int armour, boolean live,Room location) {
        super();
        this.damage = damage;
        this.health = health;
        this.armour = armour;
        this.live = live;
        this.location =location;
    }



    public boolean check() {
        if (this.health <= 0) {
            this.live = false;
        }
        return live;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public Room getLocation() {
        return location;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmour() {
        return armour;
    }

    public int getProperty() {
        return property;
    }
}

