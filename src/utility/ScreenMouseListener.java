package utility;

import object.Pump;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScreenMouseListener extends MouseAdapter {

    private ScreenPanel panel;
    private Point dragOrigin;

    public ScreenMouseListener(ScreenPanel panel) {
        this.panel = panel;
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println(panel.askForInput()); // use input to create obj
        panel.getNetworkManager().add(new Pump(e.getX(), e.getY()));

        panel.repaint();
    }

    public void mouseDragged(MouseEvent e) {
        int horizontalOffset = e.getX() - (int) dragOrigin.getX();
        int verticalOffset = e.getY() - (int) dragOrigin.getY();
        panel.getNetworkManager().translateAll(horizontalOffset, verticalOffset);
        dragOrigin = e.getPoint();

        panel.repaint();
    }

    public void mousePressed(MouseEvent e) {
        dragOrigin = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        dragOrigin = null;
    }
}
