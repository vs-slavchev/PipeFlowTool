import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Dimension;

public class Main extends Canvas {

    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    private Main() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getScreenDevices()[0];
        Rectangle SCREEN_RECTANGLE = device.getDefaultConfiguration().getBounds();
        SCREEN_WIDTH = SCREEN_RECTANGLE.width;
        SCREEN_HEIGHT = SCREEN_RECTANGLE.height;

        JFrame container = new JFrame("PipeFlowTool");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        panel.setLayout(null);
        panel.add(this);

        setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        //setLocation(0, 0);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        requestFocus();
    }

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
	}
}
