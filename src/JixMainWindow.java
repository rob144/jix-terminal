import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class JixMainWindow extends JFrame {

    int mpX, mpY;

    public JixMainWindow(){
        super("JIX TERMINAL");

        /* Panel to contain main gui */
        JPanel mainGui = new JPanel();
        mainGui.setLayout(new GridBagLayout());
        mainGui.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mainGui.setBackground(Color.BLACK);

        /* Build top bar with close, minimize and maximize buttons */
        WindowTopBar topBar = new WindowTopBar();
        GridBagConstraints gbcTopBar = new GridBagConstraints();
        gbcTopBar.fill = GridBagConstraints.HORIZONTAL;
        gbcTopBar.anchor = GridBagConstraints.NORTHWEST;
        gbcTopBar.gridx = 0;
        gbcTopBar.gridy = 0;
        gbcTopBar.weightx = 1;
        gbcTopBar.ipady = 0;
        mainGui.add(topBar, gbcTopBar);

        /* Build console with vertical scroll pane */
        JixConsole console = new JixConsole();
        JScrollPane scrollPane = new JScrollPane(
            console,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.gridx = 0;
        gbcScrollPane.gridy = 1;
        gbcScrollPane.weightx = 1;
        gbcScrollPane.weighty = 1;
        mainGui.add(scrollPane, gbcScrollPane);

        /* Displan username text after window load */
        this.addWindowListener(new FrameListener());

        /* Event listeners for clicking and dragging window */
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed( MouseEvent e ){
                mpX = e.getX();
                mpY = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged( MouseEvent e ) {
                setLocation(
                    getLocation().x + e.getX() - mpX,
                    getLocation().y + e.getY() - mpY
                );
            }
        });

        this.setUndecorated(true);
        this.setLayout(new GridLayout(0, 1));
        this.setBackground(new Color(0,0,0,0));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setPreferredSize(new Dimension(400,400));

        this.add(mainGui);
        
        this.pack();
        this.setVisible(true);

        console.append(Color.WHITE, "WELCOME TO JIX TERMINAL!\n");
        console.update(console.getGraphics());
    }
}

class WindowTopBar extends JPanel {

	public WindowTopBar(){
		super(new FlowLayout(FlowLayout.RIGHT));

		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.setBackground(Color.BLUE);

		JButton btnMinimize = new RoundButton(new Dimension(16, 16), Color.YELLOW);
		JButton btnMaximize = new RoundButton(new Dimension(16, 16), Color.GREEN);
		JButton btnClose = new RoundButton(new Dimension(16, 16), Color.RED);

		btnClose.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnMinimize.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnMaximize.setAlignmentX(Component.RIGHT_ALIGNMENT);

		btnMinimize.setBorder(null);
		btnMinimize.setBorderPainted(false);
		btnMinimize.setMargin(new Insets(0,0,0,0));

		btnMaximize.setBorder(null);
		btnMaximize.setBorderPainted(false);
		btnMaximize.setMargin(new Insets(0,0,0,0));

		btnClose.setBorder(null);
		btnClose.setBorderPainted(false);
		btnClose.setMargin(new Insets(0,0,0,0));

		this.add(btnMinimize);
		this.add(btnMaximize);
		this.add(btnClose);
	}
}

class RoundButton extends JButton {

	protected Shape shape, base;
	protected Color thisColor;

	public RoundButton(Dimension size, Color color) {
		thisColor = color;
		this.setModel(new DefaultButtonModel());
		this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		this.setBackground(Color.WHITE);
		this.setContentAreaFilled(true);
		this.setFocusPainted(false);
		this.setAlignmentY(Component.TOP_ALIGNMENT);
		this.setPreferredSize(size);
		initShape(size);
	}
	
	protected void initShape(Dimension size) {
		if(!getBounds().equals(base)) {
		  	base = getBounds();
		  	shape = new Ellipse2D.Float(0, 0, size.width - 1, size.height - 1);
		}
	}

	@Override protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                    RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(thisColor);
		g2.fill(shape);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                    RenderingHints.VALUE_ANTIALIAS_OFF);
	}

/*
	@Override protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                    RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(thisColor);
		g2.setStroke(new BasicStroke(1.0f));
		g2.draw(shape);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                    RenderingHints.VALUE_ANTIALIAS_OFF);
	}
*/
	@Override public boolean contains(int x, int y) {
		return shape.contains(x, y);
		//or return super.contains(x, y) && ((image.getRGB(x, y) >> 24) & 0xff) > 0;
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
