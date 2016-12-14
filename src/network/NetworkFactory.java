package network;

import object.*;
import utility.CursorManager.CursorType;

public class NetworkFactory {

    private static Pipe notFinished;

    public static ComponentWithImage createNetworkObject(CursorType selected) {
        switch (selected) {
            case SPLITTER:
                return new Splitter();
            case MERGER:
                return new Merger();
            case SINK:
                return new Sink();
            default:
                return new Pump();
        }
    }

    /**
     * Start building a pipe object by giving it a component to start from.
     * @param pipeInput
     */
    public static void startPipe(Component pipeInput) {
        if (pipeInput instanceof ComponentWithImage) {
            notFinished = new Pipe();
            notFinished.addJoin((ComponentWithImage)pipeInput);
        }
    }

    /**
     * Abandon the pipe that is currently being built.
     */
    public static void stopBuildingPipe() {
        notFinished = null;
    }

    /**
     * Finish the building of a pipe by giving the second component to end at.
     * @param pipeOutput
     * @return a completed pipe
     */
    public static Pipe finishPipe(Component pipeOutput) {
        if (pipeOutput instanceof ComponentWithImage && notFinished != null) {
            notFinished.addJoin((ComponentWithImage)pipeOutput);
            Pipe finished = notFinished;
            notFinished = null;
            return finished;
        }
        return null;
    }

}
