package collision;

public class RectangleCollisionMask extends CollisionMask {

    private int width, height;

    public RectangleCollisionMask(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean hasCollision(final int x, final int y) {
        return x > this.getX() - width / 2
                && x < this.getX() + width / 2
                && y > this.getY() - height / 2
                && y < this.getY() + height / 2;
    }
}
