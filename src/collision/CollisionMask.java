package collision;

import java.awt.Point;

/** A shape that is responsible for checking if a point is inside it. */
 public abstract class CollisionMask {

    protected int x, y;

    public void setCenterPoint(final int x, final int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** Called when checking if a point is inside the collision mask. */
    public abstract boolean hasCollision(final Point point);
}
