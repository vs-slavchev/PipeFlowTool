
import utility.ScreenPanel;

import javax.swing.*;

public class PipeFlowTool {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Pipe Flow Tool");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
        frame.setContentPane(new ScreenPanel());

        frame.setSize(450, 300);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        menu.add(new JMenuItem("New"));
        menu.add(new JMenuItem("Open"));
        menu.add(new JMenuItem("Save"));
        menu.add(new JMenuItem("Save as"));

        JButton pumpBt = new JButton("Pump");
        menuBar.add(pumpBt);

        return menuBar;
    }
}