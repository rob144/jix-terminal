import java.awt.*;
import javax.swing.*;

public class JixTerminal {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                JixMainWindow mainWindow = new JixMainWindow();
            }
        };
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}