import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends Canvas implements MouseListener {

    private int screenWidth;
    private int screenHeight;

    private Main() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getScreenDevices()[0];
        Rectangle screenRectangle = device.getDefaultConfiguration().getBounds();
        screenWidth = screenRectangle.width;
        screenHeight = screenRectangle.height;

        JFrame container = new JFrame("PipeFlowTool");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        panel.setLayout(null);
        panel.add(this);

        setBounds(0, 0, screenWidth, screenHeight);
        //setLocation(0, 0);

        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        requestFocus();

        addMouseListener(this);
    }

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
	}

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked: " + e.getX() + ":" + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
