package collision;

import java.awt.Point;

public class RectangleCollisionMask extends CollisionMask {

    private int width, height;

    public RectangleCollisionMask(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean hasCollision(Point point) {
        return point.getX() > centerPoint.getX() - width/2
                && point.getX() < centerPoint.getX() + width/2
                && point.getY() > centerPoint.getY() - height/2
                && point.getY() < centerPoint.getY() + height/2;
    }
}
