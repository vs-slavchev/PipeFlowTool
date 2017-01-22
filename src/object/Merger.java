package object;

import network.Simulation;

public class Merger extends ComponentWithImage {

    private final Simulation simulation;

    public Merger(Simulation simulation) {
        super("merger64");
        this.simulation = simulation;
    }

    @Override
    public void showPropertiesDialog() {
        // intentionally left empty
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
