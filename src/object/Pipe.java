package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import network.Point;
import utility.Values;

import java.io.Serializable;
import java.util.ArrayList;

public class Pipe extends Component implements Serializable{

    private ArrayList<Point> joins = new ArrayList<>();
    private Component input;

    public void addJoin(ComponentWithImage component) {
        joins.add(component.getCenterPosition());
    }

    @Override
    public boolean isClicked(final Point clickLocation) {
        /*function sqr(x) { return x * x }
        function dist2(v, w) { return sqr(v.x - w.x) + sqr(v.y - w.y) }
        function distToSegmentSquared(p, v, w) {
            var l2 = dist2(v, w);
            if (l2 == 0) return dist2(p, v);
            var t = ((p.x - v.x) * (w.x - v.x) + (p.y - v.y) * (w.y - v.y)) / l2;
            t = Math.max(0, Math.min(1, t));
            return dist2(p, { x: v.x + t * (w.x - v.x),
                    y: v.y + t * (w.y - v.y) });
        }
        function distToSegment(p, v, w) { return Math.sqrt(distToSegmentSquared(p, v, w)); }*/
        return false;
    }



    @Override
    public boolean overlaps(Component other) {
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        // draw wider part
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(flowProperties.getCapacity()*2 + 2);
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

    private void drawLinesOfPipe(GraphicsContext gc) {
        for (int firstOfPair_i = 0; firstOfPair_i < joins.size() - 1; firstOfPair_i++) {
            Point secondOfPair = joins.get(firstOfPair_i + 1);
            Point firstOfPair = joins.get(firstOfPair_i);

            gc.strokeLine(
                    firstOfPair.getX(),
                    firstOfPair.getY(),
                    secondOfPair.getX(),
                    secondOfPair.getY());
        }
    }

    private void drawArrowheads(GraphicsContext gc) {
        for (int firstOfPair_i = 0; firstOfPair_i < joins.size() - 1; firstOfPair_i++) {
            Point secondOfPair = joins.get(firstOfPair_i + 1);
            Point firstOfPair = joins.get(firstOfPair_i);

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
