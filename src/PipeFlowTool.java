
import utility.ScreenPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PipeFlowTool implements ActionListener{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        PipeFlowTool program = new PipeFlowTool();
        JFrame frame = new JFrame("Pipe Flow Tool");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(program.createMenuBar());
        frame.setContentPane(new ScreenPanel());

        frame.setSize(450, 300);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        menu.add(createMenuItem("New"));
        menu.add(createMenuItem("Open"));
        menu.add(createMenuItem("Save"));
        menu.add(createMenuItem("Save as"));

        menuBar.add(createMenuButton("pump"));
        menuBar.add(createMenuButton("splitter"));
        menuBar.add(createMenuButton("adjustable splitter"));
        menuBar.add(createMenuButton("merger"));
        menuBar.add(createMenuButton("pipe"));
        menuBar.add(createMenuButton("sink"));

        return menuBar;
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(this);
        return item;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if (e.getSource() instanceof JMenuItem) {
            // do file related stuff
        } else if (e.getSource() instanceof JButton) {
            // change state, cursor, screenPanel onClick action

            //if (e.getSource() == openButton) { // make a switch?
        }
    }
}