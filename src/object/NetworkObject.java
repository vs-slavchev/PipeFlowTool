package object;

import collision.CollisionMask;

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
    public void draw(Graphics gr) {
        int halfWidth = image.getWidth(null) / 2;
        int halfHeight = image.getHeight(null) / 2;
        int offsetLeft = collisionMask.getX() - halfWidth;
        int offsetUp = collisionMask.getY() - halfHeight;
        gr.drawImage(image, offsetLeft, offsetUp, null);
    }
}
