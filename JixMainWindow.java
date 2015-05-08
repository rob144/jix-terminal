import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class JixMainWindow extends JFrame {

    public JixMainWindow(){
        super("JIX TERMINAL");
        
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

        JixConsole console = new JixConsole();
        JScrollPane scrollPane = new JScrollPane(console,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        gbConstr.fill = GridBagConstraints.BOTH;
        gbConstr.gridx = 0;
        gbConstr.gridy = 1;
        gbConstr.weightx = 1;
        gbConstr.weighty = 1;
        mainGui.add(scrollPane, gbConstr);

        this.addWindowListener(new FrameListener());
        this.setUndecorated(true);
        this.setLayout(new GridLayout(0, 1));
        this.setBackground(new Color(0,0,0,0));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(mainGui);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setPreferredSize(new Dimension(400,400));
        this.pack();
        this.setVisible(true);
        console.append(Color.WHITE, "WELCOME TO JIX TERMINAL!\n");
        console.update(console.getGraphics());
    }
}

class FrameListener extends WindowAdapter {
    
    public void windowOpened(WindowEvent e){
        JFrame frame = (JFrame)e.getComponent();
        ArrayList<Component> comps = getAllComponents(frame);
        for (Component comp : comps) {
            if (comp instanceof JixConsole) {
                JixConsole console = (JixConsole)comp;
                console.append(Color.WHITE, System.getProperty("user.name") + "$ ");
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