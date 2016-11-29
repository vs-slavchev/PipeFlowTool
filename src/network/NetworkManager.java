package network;

import javafx.scene.canvas.GraphicsContext;
import object.NetworkObject;

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
    public void moveObjects(final int x, final int y) {
        if (objects.stream().anyMatch(NetworkObject::isSelected)) {
            objects.stream()
                    .filter(NetworkObject::isSelected)
                    .forEach(obj -> obj.translate(x, y));
        } else {
            objects.forEach(obj -> obj.translate(x, y));
        }
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

    public void deleteSelected() {
        objects.removeIf(NetworkObject::isSelected);
    }
}
