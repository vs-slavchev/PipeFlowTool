package collision;

public class CircleCollisionMask extends CollisionMask {

    private int radius;

    /**
     * Construct a circular collision mask.
     *
     * @param radius The radius of the circle
     */
    public CircleCollisionMask(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean hasCollision(final int x, final int y) {
        double horizontalDistance = Math.abs(x - this.getX());
        double verticalDistance = Math.abs(y - this.getY());
        return Math.sqrt(Math.pow(horizontalDistance, 2) + Math.pow(verticalDistance, 2)) <= radius;
    }
}
