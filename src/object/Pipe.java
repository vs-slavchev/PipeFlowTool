package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import network.Join;
import network.Point;

import java.util.ArrayList;

public class Pipe extends Component {

    private ArrayList<Join> joins = new ArrayList<>();
    private Component input;

    public void addJoin(ComponentWithImage component) {
        joins.add(new Join(component.getCenterPosition()));
    }

    @Override
    public boolean isClicked(final Point clickLocation) {
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
    }

    private void drawLinesOfPipe(GraphicsContext gc) {
        for (int firstOfPair_i = 0; firstOfPair_i < joins.size() - 1; firstOfPair_i++) {
                gc.strokeLine(
                        joins.get(firstOfPair_i).getX(),
                        joins.get(firstOfPair_i).getY(),
                        joins.get(firstOfPair_i + 1).getX(),
                        joins.get(firstOfPair_i + 1).getY());
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
