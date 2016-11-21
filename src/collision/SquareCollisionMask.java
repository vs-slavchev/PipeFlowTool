package collision;

/**
 * Squares are rectangles with width and height of the same size.
 */
public class SquareCollisionMask extends RectangleCollisionMask {

    public SquareCollisionMask(int size) {
        super(size, size);
    }
}
