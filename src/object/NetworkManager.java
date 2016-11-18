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
        objects.forEach(obj -> obj.draw(g));
    }

    /** Use for moving all objects or making it look like we are moving the viewport. */
    public void translateAll(final int x, final int y) {
        objects.forEach(obj -> obj.translate(x, y));
    }
}
