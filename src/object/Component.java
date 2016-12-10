package object;

import file.ImageManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utility.Values;

/**
 * The base of an object of the network.
 */
public abstract class Component {

    protected Rectangle collisionBox;
    protected Image image;

    protected boolean selected;

    public Component(String imageName) {
        this.collisionBox = new Rectangle(0, 0, Values.OBJECT_SIZE, Values.OBJECT_SIZE);
        image = ImageManager.getImage(imageName);
    }

    public boolean isClicked(final int x, final int y) {
        return collisionBox.contains(x, y);
    }

    public boolean collidesWith(Component other) {
        if (!this.equals(other)) {
            return this.collisionBox.intersects(other.getCollisionBox().getBoundsInLocal());
        }
        return false;
    }

    /**
     * Draw the image of the object in the center of its coordinates.
     */
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, collisionBox.getX(), collisionBox.getY());
        drawHighlighting(gc);
    }

    private void drawHighlighting(GraphicsContext gc) {
        if (selected) {
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(4);
            gc.strokeRect(collisionBox.getX(), collisionBox.getY(),
                    collisionBox.getWidth(), collisionBox.getHeight());
        }
    }

    public void setPosition(int x, int y) {
        int boxX = x - Values.OBJECT_SIZE / 2;
        int boxY = y - Values.OBJECT_SIZE / 2;

        collisionBox.setX(boxX);
        collisionBox.setY(boxY);
    }

    public void translate(final int x, final int y) {
        collisionBox.setX(collisionBox.getX() + x);
        collisionBox.setY(collisionBox.getY() + y);
    }

    public void showPropertiesDialog() {
        // intentionally empty
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
