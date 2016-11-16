package collision;

import java.awt.Point;

/** A shape that is responsible for checking if a point is inside it. */
 public abstract class CollisionMask {

    protected Point centerPoint;

    public void setCenterPoint(final Point centerPoint){
        this.centerPoint = centerPoint;
    }

    public abstract boolean hasCollision(final Point point);
}
