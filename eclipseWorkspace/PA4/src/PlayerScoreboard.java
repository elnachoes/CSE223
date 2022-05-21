import javax.swing.JLabel;
import java.awt.Graphics;

public class PlayerScoreboard extends JLabel{
    public int score = 0;
    private String name = null;
    private TurnScoreboard turnScoreboard = null;

    public PlayerScoreboard(String name) {
        this.name = name;
        InitPlayerScoreboard(null);
    }

    public void SetPlayerName(String name) {
    	this.name = name;
        if(turnScoreboard != null) turnScoreboard.repaint();
    	repaint();
    }

    public String GetPlayerName(){
        return name;
    }

    public void InitPlayerScoreboard(TurnScoreboard turnScoreboard) {
        this.turnScoreboard = turnScoreboard;
        score = 0;
        repaint();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        setText(name + " Score : " + score);
    }
}
