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
        gc.drawImage(image,
                position.getX() - (int)image.getWidth() / 2,
                position.getY() - (int)image.getHeight() / 2);
        drawHighlighting(gc);
        flowProperties.drawFlowCapacity(gc,
                position.getX(),
                position.getY() + (int)image.getHeight() / 2 + Values.INFO_VERTICAL_OFFSET);
    }

    protected void drawHighlighting(GraphicsContext gc) {
        if (selected) {
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(4);
            gc.strokeRect(position.getX()  - (int)image.getWidth() / 2,
                    position.getY()  - (int)image.getHeight() / 2,
                    image.getWidth(), image.getHeight());
        }
    }

    @Override
    public void translate(final int dx, final int dy) {
        position.translate(dx, dy);
    }

    public Rectangle calculateCollisionBox() {
        return new Rectangle(position.getX()  - (int)image.getWidth() / 2,
                position.getY() - (int)image.getHeight() / 2,
                image.getWidth(), image.getHeight());
    }

    public void setCenterPosition(int x, int y) {
        position.setLocation(x, y);
    }

    public Point getCenterPosition() {
        return position;
    }
}
