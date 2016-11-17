
import utility.ScreenPanel;

import javax.swing.*;

public class PipeFlowTool {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Pipe Flow Tool");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new ScreenPanel());
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}