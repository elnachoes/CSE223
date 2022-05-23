import javax.swing.JLabel;
import java.awt.Graphics;

public class NotificationBoard extends JLabel {
    public String message = null;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setText(message);
    }
}
