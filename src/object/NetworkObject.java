package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import utility.Values;

/**
 * The base of an object of the network.
 */
public abstract class NetworkObject {

    protected int flow;
    protected int capacity;

    protected Rectangle collisionBox;
    protected Image image;

    public NetworkObject(int x, int y) {
        int boxX = x - Values.OBJECT_SIZE / 2;
        int boxY = y - Values.OBJECT_SIZE / 2;
        this.collisionBox = new Rectangle(boxX, boxY, Values.OBJECT_SIZE, Values.OBJECT_SIZE);
    }

    public void setFlow(final int flow) {
        this.flow = Math.max(flow, 0);
    }

    public void setCapacity(final int capacity) {
        this.capacity = Math.max(capacity, 0);
    }

    public boolean isClicked(final int x, final int y) {
        return collisionBox.contains(x, y);
    }

    public boolean collidesWith(NetworkObject currentObject) {
        if (!this.equals(currentObject)) {
            return this.collisionBox.intersects(
                    currentObject.getCollisionBox().getBoundsInLocal());
        }
        return false;
    }

    /**
     * Draw the image of the object in the center of its coordinates.
     */
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, collisionBox.getX(), collisionBox.getY());
    }

    public void translate(final int x, final int y) {
        collisionBox.setX(collisionBox.getX() + x);
        collisionBox.setY(collisionBox.getY() + y);
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}
