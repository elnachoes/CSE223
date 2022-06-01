import javax.swing.JLabel;
import java.awt.Graphics;

//this is a jlabel class I created to act as a notification label for visually sending info to the player
//author : corbin martin
public class NotificationBoard extends JLabel {
    public String message = null;
    
    //this is the method that will repaint the notification board
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setText(message);
    }
}
