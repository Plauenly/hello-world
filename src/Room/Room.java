package Room;

import Person.*;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @description:
 * @author: li yi
 * @date: Created in 2020/5/13 0:40
 * @version: ${VERSION}
 * @modified By:
 */
public class Room {
    private String description;
    private HashMap <String, Room> exit = new HashMap <String, Room>();
    public Room(String description){
        this.description = description;
    }




    public String getDescription() {
        return description;
    }

    public void setExit(String dir, Room room) {
        this.exit.put(dir,room);
    }

    public HashMap <String, Room> getExit() {
        return exit;
    }
    public String getDirectiondesc(Room ret) {
        StringBuffer sb = new StringBuffer();
        for (String dir : exit.keySet()) {
            sb.append(dir + " ");
        }
        return sb.toString();

    }

    public Room gotoNextroom(String direction){
        return exit.get(direction);
    }


}
