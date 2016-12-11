package object;

import javafx.scene.canvas.GraphicsContext;

/**
 * The base of an object of the network.
 */
public abstract class Component {

    protected FlowProperties flowProperties;
    protected boolean selected;

    public Component() {
        flowProperties = new FlowProperties();
    }

    public abstract boolean isClicked(int x, int y);

    public abstract boolean collidesWith(Component other);

    public abstract void draw(GraphicsContext gc);

    public abstract void setPosition(int x, int y);

    /**
     * Move the component a certain amount of distance from its current position.
     * @param dx distance to move over X axis
     * @param dy distance to move over Y axis
     */
    public abstract void translate(int dx, int dy);

    public void showPropertiesDialog() {
        flowProperties.inputFlowPropertyValues();
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
