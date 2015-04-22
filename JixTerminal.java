import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class JixTerminal {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                JPanel mainGui = new JPanel();
                mainGui.setLayout(new GridBagLayout());
                mainGui.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                mainGui.setBackground(Color.BLACK);

                GridBagConstraints gbConstr = new GridBagConstraints();

                JPanel topBar = new JPanel(new BorderLayout());
                topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                topBar.setBackground(Color.BLUE);

                gbConstr.fill = GridBagConstraints.HORIZONTAL;
                gbConstr.anchor = GridBagConstraints.NORTHWEST;
                gbConstr.gridx = 0;
                gbConstr.gridy = 0;
                gbConstr.weightx = 1;
                gbConstr.ipady = 20;
                mainGui.add(topBar, gbConstr);

                JTextArea editArea = new JTextArea(5,40);
                Font font = new Font(Font.MONOSPACED, Font.PLAIN, editArea.getFont().getSize());
                editArea.setFont(font);
                editArea.setBackground(new Color(0,0,0,150));
                editArea.setForeground(Color.WHITE);
                editArea.setCaretColor(Color.WHITE);
                editArea.setLineWrap(true);

                KeyListener keyListener = new KeyListener() {
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        	JTextArea ta = (JTextArea)e.getComponent();
                        	ta.append("\n");
                        	e.consume();
                        	//TODO: parse the input and execute the relevant command.
                        }
                    }
                    public void keyTyped(KeyEvent e) {
                    }
                    public void keyReleased(KeyEvent e) {
                    }

                    private void printIt(String title, KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        String keyText = KeyEvent.getKeyText(keyCode);
                        System.out.println(title + " : " + keyText + " / " + e.getKeyChar());
                    }
                };
                editArea.addKeyListener(keyListener);

                JScrollPane scrollPane = new JScrollPane(editArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

                gbConstr.fill = GridBagConstraints.BOTH;
                gbConstr.gridx = 0;
                gbConstr.gridy = 1;
                gbConstr.weightx = 1;
                gbConstr.weighty = 1;
                mainGui.add(scrollPane, gbConstr);

                JFrame mainWindow = new JFrame("JIX TERMINAL");
                mainWindow.addWindowListener(new FrameListener());
                mainWindow.setUndecorated(true);
                mainWindow.setLayout(new GridLayout(0, 1));
                mainWindow.setBackground(new Color(0,0,0,0));
                mainWindow.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                mainWindow.add(mainGui);
                mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainWindow.setLocationByPlatform(true);
                mainWindow.setPreferredSize(new Dimension(400,400));
                mainWindow.pack();
                mainWindow.setVisible(true);
                editArea.append("WELCOME TO JIX TERMINAL!\n");
                editArea.update(editArea.getGraphics());
            }
        };
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}

class FrameListener extends WindowAdapter {
    public void windowOpened(WindowEvent e){
        JFrame frame = (JFrame)e.getComponent();
        ArrayList<Component> comps = getAllComponents(frame);
        for (Component comp : comps) {
            if (comp instanceof JTextArea) {
                JTextArea ta = (JTextArea)comp;
                ta.append("$");
            }
        }                            
    }
    public ArrayList<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        ArrayList<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
    }
}