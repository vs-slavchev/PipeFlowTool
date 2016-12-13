package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pipe extends Component {

    private ArrayList<Join> joins = new ArrayList<>();

    public void addJoin(ComponentWithImage component) {
        joins.add(new Join(
                component.getCenterPosition().getX(),
                component.getCenterPosition().getY()));
    }

    @Override
    public boolean isClicked(int x, int y) {
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
        for (int firstPoint_i = 0; firstPoint_i < joins.size() - 1; firstPoint_i++) {
                gc.strokeLine(
                        joins.get(firstPoint_i).getX(),
                        joins.get(firstPoint_i).getY(),
                        joins.get(firstPoint_i + 1).getX(),
                        joins.get(firstPoint_i + 1).getY());
        }
    }

    @Override
    public void translate(int dx, int dy) {
        //TODO: maybe translate only inner joins, outer ones are shared with the objs?
        joins.stream().forEach(join -> join.translate(dx, dy));
    }
}
