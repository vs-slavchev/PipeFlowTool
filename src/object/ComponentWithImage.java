package object;

import file.ImageManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import network.Point;
import utility.Values;

import java.io.Serializable;

/**
 * A component that is represented by an image. The collision box depends on the image size.
 */
public abstract class ComponentWithImage extends Component implements Serializable{
    private static final long serialVersionUID = -2724135797181166853L;
    protected Point position;
    protected transient Image image;

    public ComponentWithImage(String imageName) {
        image = ImageManager.getImage(imageName);
        position = new Point(0, 0);
    }

    @Override
    public boolean isClicked(final Point clickLocation) {
        return calculateCollisionBox().contains(clickLocation.getX(), clickLocation.getY());
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
                calculateCollisionBox().getX(),
                calculateCollisionBox().getY());
        drawHighlighting(gc);
        flowProperties.drawFlowCapacity(gc,
                position.getX(),
                position.getY() + (int)image.getHeight() / 2 + Values.INFO_VERTICAL_OFFSET);
    }

    protected void drawHighlighting(GraphicsContext gc) {
        if (selected) {
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(4);
            gc.strokeRect(calculateCollisionBox().getX(),
                    calculateCollisionBox().getY(),
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

    public void setCenterPosition(Point center) {
        position = new Point(center);
    }

    public Point getCenterPosition() {
        return position;
    }
}
