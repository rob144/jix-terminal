import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class JixTerminal {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // the GUI as seen by the user (without frame)
                JPanel gui = new JPanel();
                gui.setLayout(new GridLayout(0, 1));
                gui.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                gui.setBackground(Color.BLACK);

                JTextArea editArea = new JTextArea(5,40);
                Font font = new Font(Font.MONOSPACED, Font.PLAIN, editArea.getFont().getSize());
                editArea.setFont(font);
        		editArea.setBackground(Color.BLACK);
        		editArea.setForeground(Color.WHITE);
        		editArea.setCaretColor(Color.WHITE);
        		editArea.setLineWrap(true);

        		KeyListener keyListener = new KeyListener() {
				    public void keyPressed(KeyEvent e) {
				    	printIt("Pressed", e);
				    	if(e.getKeyCode() == KeyEvent.VK_ENTER){
				    		JTextArea ta = (JTextArea)e.getComponent();
				    		ta.append("\n");
					    	e.consume();
					    	//TODO: parse the input and execute the relevant command.
					    }
				    }

				    public void keyReleased(KeyEvent e) {
				        printIt("Released", e);
				    }

				    public void keyTyped(KeyEvent e) {
				        printIt("Typed", e);
				    }

				    private void printIt(String title, KeyEvent e) {
				        int keyCode = e.getKeyCode();
				        String keyText = KeyEvent.getKeyText(keyCode);
				        System.out.println(title + " : " + keyText + " / " + e.getKeyChar());
				    }
			    };
			    editArea.addKeyListener(keyListener);


                gui.add(new JScrollPane(editArea,
            		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
           			 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

                JFrame f = new JFrame("JIX TERMINAL");
                f.setLayout(new BorderLayout());
                f.add(gui);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // See http://stackoverflow.com/a/7143398/418556 for demo.
                f.setLocationByPlatform(true);
                f.pack();
                f.setVisible(true);
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}