import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Component;

//this class is a scoreboard for a player that knows what their score is
public class PlayerScoreboard extends JLabel{
    public int score = 0;
    private String playerName = null;
    private TurnScoreboard turnScoreboard = null;

    //the constructor requires a string for the player name
    public PlayerScoreboard(String playerName) { this.playerName = playerName; }

    //this method lets you set the name on the fly during the game if you want and repaints the scoreboard
    public void SetPlayerName(String playerName) {
    	this.playerName = playerName;
        if (turnScoreboard != null) turnScoreboard.repaint();
    	repaint();
    }

    //this method just returns the player name
    public String GetPlayerName() { return playerName; }

    //this method sets up the playerscoreboard
    //this method will get the sibling components of the parent component and assign them accordingly
    //it will also initialize the player score to 0 and redraw the scoreboard
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

    //this is the paint method that will redraw the scoreboard
    @Override
    public void paint(Graphics g){
        super.paint(g);
        setText(playerName + " Score : " + score);
    }
}
