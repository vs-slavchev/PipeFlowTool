package utility;

import file.ImageManager;
import object.NetworkManager;
import object.Pump;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScreenPanel extends JPanel {

    private int screenWidth;
    private int screenHeight;
    private NetworkManager networkManager;

    public ScreenPanel() {
        initializeScreen();
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener();

        ImageManager.initImages();
        networkManager = new NetworkManager();
    }

    private void addMouseListener() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                networkManager.add(new Pump(e.getX() - 200, e.getY()));

                repaint();
            }
        });
    }

    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);

        gr.setColor(Color.RED);
        gr.drawString("ello bois", 10, 20);

        networkManager.drawAllObjects(gr);
    }

    public Dimension getPreferredSize() {
        return new Dimension(screenWidth, screenHeight);
    }

    private void initializeScreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getScreenDevices()[0];
        Rectangle screenRectangle = device.getDefaultConfiguration().getBounds();
        screenWidth = screenRectangle.width;
        screenHeight = screenRectangle.height;
    }
}
