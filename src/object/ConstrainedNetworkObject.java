package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Values;

import java.util.Optional;

public class ConstrainedNetworkObject extends NetworkObject {

    private FlowConstraints flowConstraints;

    public ConstrainedNetworkObject(int x, int y, String imageName) {
        super(x, y, imageName);
        flowConstraints = new FlowConstraints();
    }

    public void setFlowCapacity(int flow, int capacity) {
        flowConstraints.setFlow(flow);
        flowConstraints.setCapacity(capacity);
    }

    @Override
    public void showPropertiesDialog() {
        Optional<String> properties = flowConstraints.showPropertiesInputDialog();
        flowConstraints.setObjectFlowAndCapacity(this, properties.orElse("10"));
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        drawFlowCapacity(gc);
    }

    public void drawFlowCapacity(GraphicsContext gc) {
        gc.setFill(Color.PURPLE);
        gc.fillText(flowConstraints.toString(),
                collisionBox.getX() + Values.OBJECT_SIZE / 2,
                collisionBox.getY() + Values.INFO_VERTICAL_OFFSET);
    }
}
