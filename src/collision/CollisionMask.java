package collision;

/**
 * A shape that is responsible for keeping track of the position of an object
 * and checking if a point is inside it.
 */
public abstract class CollisionMask {

    protected int x;
    protected int y;

    public void setCenterPoint(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Called when checking if a point is inside the collision mask.
     */
    public abstract boolean hasCollision(final int x, final int y);

    public void translate(final int x, final int y) {
        this.x += x;
        this.y += y;
    }
}
