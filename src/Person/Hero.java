package Person;

import Room.Room;

import java.util.ArrayList;

/**
 * @description:
 * @author: li yi
 * @date: Created in 2020/5/13 0:05
 * @version: ${VERSION}
 * @modified By:
 */
public class Hero extends Person {
    private ArrayList<Npc> followers = new ArrayList <Npc>();
    private int max;

    public Hero(int health, int damage, int property, int armour, boolean live, Room location,int max) {
        super(health, damage, property,armour,live,location);
        this.max =max;
    }
    public void business(int money) {
        this.property = this.property + money;
    }

    public void addfollowers(Npc o){
        followers.add(o);
    }

    public int getLength() {
        if(this.followers ==null){
            return 0;
        }
        else{
            return followers.size();
        }
    }

    public ArrayList <Npc> getFollowers() {
        return followers;
    }

    public int getMax() {
        return max;
    }
}
