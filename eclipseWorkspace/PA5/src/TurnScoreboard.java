import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Component;

//this class keeps track of who's turn it is and displays this information 
//author : corbin martin
public class TurnScoreboard extends JLabel {
    private boolean isPlayer1Turn = true;

    private PlayerScoreboard player1Scoreboard = null;
    private PlayerScoreboard player2Scoreboard = null;
    
    //this method gets the sibiling components and sets the turn to player1 by default
    public void InitTurnScoreboard() {
        for (Component component: getParent().getComponents()) {
            if (component.getName() == null) continue;

            if (component.getName().compareTo("player1Scoreboard") == 0) {
                this.player1Scoreboard = (PlayerScoreboard)component;
            }
            if (component.getName().compareTo("player2Scoreboard") == 0) {
                this.player2Scoreboard = (PlayerScoreboard)component;
            }
        }

        isPlayer1Turn = true;
        repaint();
    }

    //this method returns which players turn it is
    public boolean GetPlayerTurn() { return isPlayer1Turn; }

    //this methods switches turns
    public void SwitchTurn() {
        if (isPlayer1Turn) isPlayer1Turn = false;
        else isPlayer1Turn = true;
    }

    //this method repaints the label to whoever's turn it is
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isPlayer1Turn) {
            if (player1Scoreboard != null) setText(player1Scoreboard.GetPlayerName() + "\'s turn");
        } 
        else { 
            if (player2Scoreboard != null) setText(player2Scoreboard.GetPlayerName() + "\'s turn");
        }
    }
}
