import javax.swing.JLabel;
import java.awt.Graphics;

public class PlayerScoreboard extends JLabel{
    public int score = 0;
    public String name = null;

    public PlayerScoreboard(String playerName){
        name = playerName;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        setText(name + "Score : " + score);
    }
}
