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
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(flowProperties.getCapacity());
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
