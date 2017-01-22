package utility;

import network.Point;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Values {
    public static final int DEFAULT_FLOW_INPUT = 10;

    // graphics related
    public static final int INFO_VERTICAL_OFFSET = 10;
    public static final int FILE_BUTTON_HEIGHT = 42;

    public static final int MAX_DISTANCE_ARROW =
            (int) Math.ceil(distance(new Point(0, 0), new Point(32, 32)));
    public static final int ARROW_WING_LENGTH = 30;
    public static final int WING_ANGLE = 30;

    /**
     * Calculates the distance between 2 points in 2D space.
     */
    public static double distance(Point first, Point second) {
        double horizontalDistance = Math.abs(first.getX() - second.getX());
        double verticalDistance = Math.abs(first.getY() - second.getY());
        return Math.sqrt(Math.pow(horizontalDistance, 2) + Math.pow(verticalDistance, 2));
    }

    /**
     * Calculates the coordinates of a point which lies between 2 other points and
     * is a certain amount of distance from the end one.
     * @param origin of the line
     * @param end of the line
     * @param distanceFromEnd distance from the end point on the line
     * @return the resulting point on the line
     */
    public static Point cuttingPoint(Point origin, Point end, int distanceFromEnd) {
        double cuttingX = end.getX()
                - distanceFromEnd
                * (end.getX() - origin.getX())
                / distance(origin, end);
        double cuttingY = end.getY()
                - distanceFromEnd
                * (end.getY() - origin.getY())
                / distance(origin, end);
        return new Point((int) cuttingX, (int) cuttingY);
    }

    /**
     * Rotates a point around another point.
     * @param toRotate the point to rotate
     * @param angle the angle of rotation
     * @param pivot the pivot point to rotate around
     * @return the new location point
     */
    public static Point rotatePoint(Point toRotate, float angle, Point pivot){
        return new Point(
                (int) (cos(angle) * (pivot.getX() - toRotate.getX())
                        - sin(angle) * (pivot.getY() - toRotate.getY()) + toRotate.getX()),
                (int) (sin(angle) * (pivot.getX() - toRotate.getX())
                        + cos(angle) * (pivot.getY() - toRotate.getY()) + toRotate.getY()));
    }
}
