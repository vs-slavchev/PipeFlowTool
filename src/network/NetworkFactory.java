package network;

// TODO: add pipe creation and setting up;

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

    public static void startPipe(Component pipeInput) {
        if (pipeInput instanceof ComponentWithImage) {
            notFinished = new Pipe();
            notFinished.addJoin((ComponentWithImage)pipeInput);
        }
    }

    public static void stopBuildingPipe() {
        notFinished = null;
    }

    public static Pipe finishPipe(Component pipeOutput) {
        if (pipeOutput instanceof ComponentWithImage) {
            notFinished.addJoin((ComponentWithImage)pipeOutput);
            Pipe finished = notFinished;
            notFinished = null;
            return finished;
        }
        return null;
    }

}
