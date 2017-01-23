package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import network.Point;
import utility.Values;

import java.io.Serializable;
import java.util.ArrayList;

public class Pipe extends Component implements Serializable{
    private static final long serialVersionUID = -2724135797181166853L;
    private ArrayList<Point> joins = new ArrayList<>();
    private Component input;

    public void addEndpoint(ComponentWithImage component) {
        joins.add(component.getCenterPosition());
    }

    public void addJoin(Point clickLocation) {
        int indexToInsertAt = 0;
        for (int firstOfPair_i = 0; firstOfPair_i < joins.size() - 1; firstOfPair_i++) {
            Point firstOfPair = joins.get(firstOfPair_i);
            Point secondOfPair = joins.get(firstOfPair_i + 1);

            // identify which segment is clicked
            if (Values.distanceLineToPoint(firstOfPair, secondOfPair, clickLocation)
                    < Math.max(flowProperties.getCapacity(), Values.MIN_PIPE_CLICK_WIDTH)) {
                indexToInsertAt = firstOfPair_i + 1;
                break;
            }
        }
        if (indexToInsertAt > 0) {
            joins.add(indexToInsertAt, clickLocation);
        }
    }

    @Override
    public boolean isClicked(final Point clickLocation) {
        for (int firstOfPair_i = 0; firstOfPair_i < joins.size() - 1; firstOfPair_i++) {
            Point firstOfPair = joins.get(firstOfPair_i);
            Point secondOfPair = joins.get(firstOfPair_i + 1);

            // one segment is clicked => the pipe is clicked
            if (Values.distanceLineToPoint(firstOfPair, secondOfPair, clickLocation)
                    < Math.max(flowProperties.getCapacity(), Values.MIN_PIPE_CLICK_WIDTH)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        // draw wider part
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(calculateJointThickness());
        drawLinesOfPipe(gc);

        // draw thinner part
        if (flowProperties.getFlow() <= flowProperties.getCapacity() / 2) {
            gc.setStroke(Color.GREEN); // 0-50%
        } else if (flowProperties.getFlow() <= flowProperties.getCapacity() * 3 / 4) {
            gc.setStroke(Color.YELLOW); // 51-75%
        } else if (flowProperties.getFlow() < flowProperties.getCapacity()) {
            gc.setStroke(Color.ORANGE); // 76-99%
        } else {
            gc.setStroke(Color.RED); // 100%
        }

        double flowToRepresent = Math.min(flowProperties.getFlow(), flowProperties.getCapacity());
        gc.setLineWidth(flowToRepresent*2);
        drawLinesOfPipe(gc);

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        drawArrowheads(gc);
    }

    private int calculateJointThickness() {
        return flowProperties.getCapacity() * 2 + 2;
    }

    private void drawLinesOfPipe(GraphicsContext gc) {
        for (int firstOfPair_i = 0; firstOfPair_i < joins.size() - 1; firstOfPair_i++) {
            Point firstOfPair = joins.get(firstOfPair_i);
            Point secondOfPair = joins.get(firstOfPair_i + 1);

            gc.strokeLine(
                    firstOfPair.getX(),
                    firstOfPair.getY(),
                    secondOfPair.getX(),
                    secondOfPair.getY());

            gc.fillOval(firstOfPair.getX(), firstOfPair.getY(),
                    calculateJointThickness(), calculateJointThickness());
        }
    }

    private void drawArrowheads(GraphicsContext gc) {
        for (int firstOfPair_i = 0; firstOfPair_i < joins.size() - 1; firstOfPair_i++) {
            Point firstOfPair = joins.get(firstOfPair_i);
            Point secondOfPair = joins.get(firstOfPair_i + 1);

            Point arrowPeak = Values.cuttingPoint(firstOfPair, secondOfPair,
                    Values.MAX_DISTANCE_ARROW);
            if (Values.distance(firstOfPair, arrowPeak) > 5) {
                Point initialWing = Values.cuttingPoint(firstOfPair, arrowPeak,
                        Values.ARROW_WING_LENGTH);
                Point leftWing = Values.rotatePoint(initialWing, Values.WING_ANGLE, arrowPeak);
                Point rightWing = Values.rotatePoint(initialWing, -Values.WING_ANGLE, arrowPeak);

                // draw the wings of the arrow
                gc.strokeLine(arrowPeak.getX(), arrowPeak.getY(),
                        leftWing.getX(), leftWing.getY());
                gc.strokeLine(arrowPeak.getX(), arrowPeak.getY(),
                        rightWing.getX(), rightWing.getY());
            }
        }
    }

    @Override
    public void showPropertiesDialog() {
        flowProperties.showOnlyCapacityInputDialog();
    }

    @Override
    public boolean overlaps(Component other) {
        return false;
    }

    @Override
    public void translate(int dx, int dy) {
        //translate only inner joins, outer points are shared with the components
        for (int index = 1; index < joins.size() - 1; index++) {
            joins.get(index).translate(dx, dy);
        }
    }

    public Component getInput() {
        return input;
    }

    public void setInput(Component input) {
        this.input = input;
    }
}
