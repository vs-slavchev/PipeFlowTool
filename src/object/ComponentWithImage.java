package object;

import file.ImageManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utility.Point;
import utility.Values;

/**
 * A component that is represented by an image. The collision box depends on the image size.
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
    public boolean overlaps(Component other) {
        if (!this.equals(other)) {
            if (other instanceof ComponentWithImage) { // temporary hack; TODO fix hack
                return calculateCollisionBox().intersects(
                        ((ComponentWithImage)other).calculateCollisionBox().getBoundsInLocal());
            }
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY());
        drawHighlighting(gc);
        flowProperties.drawFlowCapacity(gc,
                position.getX() + (int)image.getWidth() / 2,
                position.getY() + (int)image.getHeight() + Values.INFO_VERTICAL_OFFSET);
    }

    protected void drawHighlighting(GraphicsContext gc) {
        if (selected) {
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(4);
            gc.strokeRect(position.getX(), position.getY(), image.getWidth(), image.getHeight());
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
        int boxOriginX = x - (int)image.getWidth() / 2;
        int boxOriginY = y - (int)image.getHeight() / 2;

        position.setLocation(boxOriginX, boxOriginY);
    }

    public Point getCenterPosition() {
        Point center = new Point(position);
        center.translate((int)image.getWidth() / 2, (int)image.getHeight() / 2);
        return center;
    }
}
