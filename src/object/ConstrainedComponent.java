package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Values;

import java.util.Optional;

public class ConstrainedComponent extends Component {

    private FlowProperties flowProperties;

    public ConstrainedComponent(String imageName) {
        super(imageName);
        flowProperties = new FlowProperties();
    }

    public void setFlowCapacity(int flow, int capacity) {
        flowProperties.setFlow(flow);
        flowProperties.setCapacity(capacity);
    }

    @Override
    public void showPropertiesDialog() {
        Optional<String> properties = flowProperties.showPropertiesInputDialog();
        flowProperties.setObjectFlowAndCapacity(this, properties.orElse("10"));
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        drawFlowCapacity(gc);
    }

    public void drawFlowCapacity(GraphicsContext gc) {
        gc.setFill(Color.PURPLE);
        gc.fillText(flowProperties.toString(),
                collisionBox.getX() + Values.OBJECT_SIZE / 2,
                collisionBox.getY() + Values.INFO_VERTICAL_OFFSET);
    }
}
