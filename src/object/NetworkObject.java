package object;

import collision.CollisionMask;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;


/** The base of an object of the network. */
public abstract class NetworkObject {

    protected CollisionMask collisionMask;
    protected Image image;

    public NetworkObject(int x, int y, CollisionMask collisionMask) {
        this.collisionMask = collisionMask;
        this.collisionMask.setCenterPoint(x, y);
    }

    public boolean isClicked(final Point point) {
        return collisionMask.hasCollision(point);
    }

    /** Draw the image of the object in the center of its coordinates. */
    public void draw(GraphicsContext gc) {
        int halfWidth = (int) image.getWidth() / 2;
        int halfHeight = (int) image.getHeight() / 2;
        int offsetLeft = collisionMask.getX() - halfWidth;
        int offsetUp = collisionMask.getY() - halfHeight;
        gc.drawImage(image, offsetLeft, offsetUp);
    }

    public void translate(final int x, final int y) {
        collisionMask.translate(x, y);
    }
}
