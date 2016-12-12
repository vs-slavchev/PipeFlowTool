package object;

import file.ImageManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utility.Point;
import utility.Values;

/**
 * A component that is represented by an image. Has a collision box that depends on the image size.
 */
public abstract class ComponentWithImage extends Component {

    protected Point position;
    protected Image image;

    public ComponentWithImage(String imageName) {
        image = ImageManager.getImage(imageName);
        position = new Point(0, 0);
    }

    @Override
    public boolean isClicked(final int x, final int y) {
        return calculateCollisionBox().contains(x, y);
    }

    @Override
    public boolean collidesWith(Component other) {
        if (!this.equals(other)) {
            if (other instanceof ComponentWithImage) { // temporary hack; TODO fix hack
                return calculateCollisionBox().intersects(
                        ((ComponentWithImage)other).calculateCollisionBox().getBoundsInLocal());
            }
        }
        return false;
    }

    /**
     * Draw the image of the object in the center of its coordinates.
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY());
        drawHighlighting(gc);
        flowProperties.drawFlowCapacity(gc,
                position.getX() + Values.OBJECT_SIZE / 2,
                position.getY() + Values.INFO_VERTICAL_OFFSET);
    }

    protected void drawHighlighting(GraphicsContext gc) {
        if (selected) {
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(4);
            gc.strokeRect(position.getX(), position.getY(),
                    image.getWidth(), image.getHeight());
        }
    }

    @Override
    public void translate(final int dx, final int dy) {
        position.translate(dx, dy);
    }

    public Rectangle calculateCollisionBox() {
        return new Rectangle(position.getX(), position.getY(),
                image.getWidth(), image.getHeight());
    }

    public void setCenterPosition(int x, int y) {
        int boxOriginX = x - Values.OBJECT_SIZE / 2;
        int boxOriginY = y - Values.OBJECT_SIZE / 2;

        position.setLocation(boxOriginX, boxOriginY);
    }

    public Point getCenterPosition() {
        Point center = new Point(position);
        center.translate((int)image.getWidth() / 2, (int)image.getHeight() / 2);
        return center;
    }
}
