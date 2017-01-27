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
 * A component that is represented by an imageName. The collision box depends on the imageName size.
 */
public abstract class ComponentWithImage extends Component implements Serializable {
    private static final long serialVersionUID = -2724135797181166853L;
    protected Point position;
    protected String imageName;

    public ComponentWithImage(String imageName) {
        this.imageName = imageName;
        position = new Point(0, 0);
    }

    @Override
    public boolean isClicked(final Point clickLocation) {
        return calculateCollisionBox().contains(clickLocation.getX(), clickLocation.getY());
    }

    @Override
    public boolean overlaps(Component other) {
        if (!this.equals(other)) {
            if (other instanceof ComponentWithImage) {
                return calculateCollisionBox().intersects(
                        ((ComponentWithImage) other).calculateCollisionBox().getBoundsInLocal());
            }
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Image image = ImageManager.getImage(imageName);
        gc.drawImage(image,
                calculateCollisionBox().getX(),
                calculateCollisionBox().getY());
        drawHighlighting(gc);
        flowProperties.drawFlowCapacity(gc,
                position.getX(),
                position.getY() + (int) image.getHeight() / 2 + Values.INFO_VERTICAL_OFFSET);
    }

    protected void drawHighlighting(GraphicsContext gc) {
        if (selected) {
            Image image = ImageManager.getImage(imageName);
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
        Image image = ImageManager.getImage(imageName);
        return new Rectangle(position.getX() - (int) image.getWidth() / 2,
                position.getY() - (int) image.getHeight() / 2,
                image.getWidth(), image.getHeight());
    }

    public Image getImage() {
        return ImageManager.getImage(imageName);
    }

    public Point getCenterPosition() {
        return position;
    }

    public void setCenterPosition(Point center) {
        position = new Point(center);
    }
}
