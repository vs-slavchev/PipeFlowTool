package object;

import file.ImageManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utility.Values;

/**
 * A component that is represented by an image. Has a collision box that depends on the image size.
 */
public abstract class ComponentWithImage extends Component {

    protected Rectangle collisionBox;
    protected Image image;

    public ComponentWithImage(String imageName) {
        image = ImageManager.getImage(imageName);
        this.collisionBox = new Rectangle(0, 0, image.getWidth(), image.getHeight());
    }

    @Override
    public boolean isClicked(final int x, final int y) {
        return collisionBox.contains(x, y);
    }

    @Override
    public boolean collidesWith(Component other) {
        if (!this.equals(other)) {
            if (other instanceof ComponentWithImage) { // temporary hack; TODO fix hack
                return this.collisionBox.intersects(
                        ((ComponentWithImage)other).getCollisionBox().getBoundsInLocal());
            }
        }
        return false;
    }

    /**
     * Draw the image of the object in the center of its coordinates.
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, collisionBox.getX(), collisionBox.getY());
        drawHighlighting(gc);
        flowProperties.drawFlowCapacity(gc,
                (int) collisionBox.getX() + Values.OBJECT_SIZE / 2,
                (int) collisionBox.getY() + Values.INFO_VERTICAL_OFFSET);
    }

    protected void drawHighlighting(GraphicsContext gc) {
        if (selected) {
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(4);
            gc.strokeRect(collisionBox.getX(), collisionBox.getY(),
                    collisionBox.getWidth(), collisionBox.getHeight());
        }
    }

    @Override
    public void setPosition(int x, int y) {
        int boxX = x - Values.OBJECT_SIZE / 2;
        int boxY = y - Values.OBJECT_SIZE / 2;

        collisionBox.setX(boxX);
        collisionBox.setY(boxY);
    }

    @Override
    public void translate(final int dx, final int dy) {
        collisionBox.setX(collisionBox.getX() + dx);
        collisionBox.setY(collisionBox.getY() + dy);
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}
