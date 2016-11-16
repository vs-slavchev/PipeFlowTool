package objects;

import collision.CollisionMask;

import java.awt.Point;

/** The base of an object of the network. */
public abstract class NetworkObject {

    protected Point location;
    protected CollisionMask collisionMask;

    public NetworkObject(int x, int y, CollisionMask collisionMask) {
        location = new Point(x, y);
        this.collisionMask = collisionMask;
        this.collisionMask.setCenterPoint(location);
    }

    public boolean isClicked(final Point point) {
        return collisionMask.hasCollision(point);
    }
}
