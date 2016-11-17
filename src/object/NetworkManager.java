package object;

import java.awt.*;
import java.util.ArrayList;

/** Manages all the objects in the network. */
public class NetworkManager {

    private ArrayList<NetworkObject> objects = new ArrayList<>();

    public void add(NetworkObject object) {
        objects.add(object);
    }

    public void drawAllObjects(Graphics g) {
        for (NetworkObject obj : objects) {
            obj.draw(g);
        }
    }
}
