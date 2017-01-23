package object;

import network.Simulation;

import java.io.Serializable;

public class Merger extends ComponentWithImage implements Serializable {
    private static final long serialVersionUID = -2724135797181166853L;
    private final Simulation simulation;

    public Merger(Simulation simulation) {
        super("merger64");
        this.simulation = simulation;
    }

    @Override
    public void showPropertiesDialog() {
        flowProperties.showOnlyCapacityInputDialog();
    }

    @Override
    public void update(double previousFlow) {
        // if not exactly 2 components are pointing to the merger it doesn't work
        if (simulation.getPipesPointingToComponent(this).count() != 2) {
            return;
        }

        // sum the flows of the pipes pointing to the merger
        double flowsSum = simulation.getPipesPointingToComponent(this)
                .mapToDouble(Component::getFlow)
                .sum();
        super.update(flowsSum);
    }
}
