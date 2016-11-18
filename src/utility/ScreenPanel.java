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
                networkManager.add(new Pump(e.getX(), e.getY()));

                System.out.println(askForInput());

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

    public String askForInput() {
        return (String)JOptionPane.showInputDialog(
                this,
                "Specify flow and capacity:\n"
                        + "examples: \"8/10\" or \"10\" (short for 10/10)",
                "Properties", // window title
                JOptionPane.QUESTION_MESSAGE,
                UIManager.getIcon("FileChooser.detailsViewIcon"),
                null,
                "10"); // default text
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
