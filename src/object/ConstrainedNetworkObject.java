package object;

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
}
