import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Component;

public class PlayerScoreboard extends JLabel{
    public int score = 0;
    private String playerName = null;
    private TurnScoreboard turnScoreboard = null;

    public PlayerScoreboard(String playerName) {
        this.playerName = playerName;
    }

    public void SetPlayerName(String playerName) {
    	this.playerName = playerName;
        if(turnScoreboard != null) turnScoreboard.repaint();
    	repaint();
    }

    public String GetPlayerName(){
        return playerName;
    }

    public void InitPlayerScoreboard() {
        for (Component component: getParent().getComponents()) {
            if (component.getName() == null) continue;
            
            if (component.getName().compareTo("turnScoreboard") == 0) {
                this.turnScoreboard = (TurnScoreboard)component;
            }
        }
        score = 0;
        repaint();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        setText(playerName + " Score : " + score);
    }
}
