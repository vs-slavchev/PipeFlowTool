package network;

import object.*;
import utility.CursorManager.CursorType;

public class ComponentFactory {
    private final Simulation simulation;
    private Pipe notFinished;

    public ComponentFactory(Simulation simulation) {
        this.simulation = simulation;
    }

    public ComponentWithImage createComponent(CursorType selected) {
        switch (selected) {
            case SPLITTER:
                return new Splitter();
            case MERGER:
                return new Merger(simulation);
            case SINK:
                return new Sink();
            default:
                return new Pump();
        }
    }

    /**
     * Start building a pipe object by giving it a component to start from.
     *
     * @param pipeInput the component the pipe starts from
     * @return successful
     */
    public boolean startPipe(Component pipeInput) {
        if (pipeInput instanceof ComponentWithImage) {
            notFinished = new Pipe();
            notFinished.addEndpoint((ComponentWithImage) pipeInput);
            notFinished.setInput(pipeInput);
            return true;
        }
        return false;
    }

    /**
     * Abandon the pipe that is currently being built.
     */
    public void stopBuildingPipe() {
        notFinished = null;
    }

    /**
     * Finish the building of a pipe by giving the second component to end at.
     *
     * @param pipeOutput the component the pipe ends on
     * @return a completed pipe
     */
    public Pipe finishPipe(Component pipeOutput) {
        boolean inputIsSink = notFinished.getInput() instanceof Sink;
        boolean bothPumps = (notFinished.getInput() instanceof Pump)
                && (pipeOutput instanceof Pump);
        boolean bothSinks = inputIsSink
                && (pipeOutput instanceof Sink);
        boolean splitterHasNext = notFinished.getInput() instanceof Splitter
                && ((Splitter) notFinished.getInput()).getSecondNext() != null;
        boolean pumpHasNext = notFinished.getInput() instanceof Pump
                && (notFinished.getInput()).getNext() != null;
        boolean mergerHasNext = notFinished.getInput() instanceof Merger
                && (notFinished.getInput()).getNext() != null;

        if (pipeOutput instanceof ComponentWithImage
                && notFinished != null
                && notFinished.getInput() != pipeOutput
                && !bothPumps && !bothSinks
                && !inputIsSink
                && !splitterHasNext
                && !pumpHasNext
                && !mergerHasNext) {
            notFinished.addEndpoint((ComponentWithImage) pipeOutput);
            Pipe finished = notFinished;
            notFinished = null;

            finished.getInput().setNext(finished);
            finished.setNext(pipeOutput);
            return finished;
        }
        return null;
    }

}
