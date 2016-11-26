package object;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages all the objects in the network.
 */
public class NetworkManager {

    private ArrayList<NetworkObject> objects = new ArrayList<>();

    public void add(NetworkObject obj) {
        if (!objects.contains(obj)) {
            objects.add(obj);
        }
    }

    public void remove(NetworkObject object) {
        objects.remove(object);
    }

    public void drawAllObjects(GraphicsContext g) {
        objects.forEach(obj -> obj.draw(g));
    }

    /**
     * Use for moving all objects or making it look like we are moving the viewport.
     */
    public void translateAll(final int x, final int y) {
        objects.forEach(obj -> obj.translate(x, y));
    }

    public Optional<NetworkObject> getObject(int x, int y) {
        return objects.stream().filter(obj -> obj.isClicked(x, y)).findFirst();
    }

    public boolean doesOverlap(NetworkObject currentObject) {
        return objects.stream().anyMatch(obj -> obj.collidesWith(currentObject));
    }

    public void deselectAll() {
        objects.forEach(obj -> obj.setSelected(false));
    }
}
