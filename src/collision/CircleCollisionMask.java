package collision;

import java.awt.Point;

public class CircleCollisionMask extends CollisionMask {

    private int radius;

    /** Construct a circular collision mask.
     * @param radius The radius of the circle */
    public CircleCollisionMask(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean hasCollision(Point point) {
        double horizontalDistance = Math.abs(point.getX() - this.getX());
        double verticalDistance = Math.abs(point.getY() - this.getY());
        return Math.sqrt(Math.pow(horizontalDistance, 2) + Math.pow(verticalDistance, 2)) <= radius;
    }
}
