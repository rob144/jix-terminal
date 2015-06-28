import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class JixConsole extends JTextPane {

	String _inputBuffer = "";

    public JixConsole(){
        super();
        this.setFont(new Font("Courier", Font.PLAIN, 14));
        this.setBackground(new Color(0,0,0,150));
        this.setForeground(Color.WHITE);
        this.setCaretColor(Color.WHITE);
        //this.setLineWrap(true);

        KeyListener keyListener = new KeyListener() {
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	                JixConsole console = (JixConsole)e.getComponent();
	                console.append(Color.WHITE, "\n" + System.getProperty("user.name") + "$ ");
	                _inputBuffer = "";
	                e.consume();
	                //TODO: parse the input and execute the relevant command.
	            } else {
	            	int keyCode = e.getKeyCode();
	            	_inputBuffer += KeyEvent.getKeyText(keyCode);
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
    	this.addKeyListener(keyListener);
    }

	public void append(Color c, String s) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(
			SimpleAttributeSet.EMPTY,
		    StyleConstants.Foreground, 
		    c
		);
		int len = getDocument().getLength(); // same value as getText().length();
		setCaretPosition(len); // place caret at the end (with no selection)
		setCharacterAttributes(aset, false);
		replaceSelection(s); // there is no selection, so inserts at caret
	}

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.addRenderingHints(new RenderingHints(
        	RenderingHints.KEY_TEXT_ANTIALIASING, 
        	RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)
        );
        super.paintComponent(g);
    }
        
}

