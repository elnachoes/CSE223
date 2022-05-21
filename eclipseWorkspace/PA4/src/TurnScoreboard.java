import javax.swing.JLabel;
import java.awt.Graphics;

public class TurnScoreboard extends JLabel{
    private boolean isPlayer1Turn = true;

    private PlayerScoreboard player1Scoreboard = null;
    private PlayerScoreboard player2Scoreboard = null;
    
    public void InitTurnScoreboard(PlayerScoreboard player1Scoreboard, PlayerScoreboard player2Scoreboard){
        this.player1Scoreboard = player1Scoreboard;
        this.player2Scoreboard = player2Scoreboard;
        isPlayer1Turn = true;
        repaint();
    }

    public boolean GetPlayerTurn(){
        return isPlayer1Turn;
    }

    public void SwitchTurn(){
        if (isPlayer1Turn) isPlayer1Turn = false;
        else isPlayer1Turn = true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isPlayer1Turn) {
            if(player1Scoreboard != null) setText(player1Scoreboard.GetPlayerName() + "\'s turn");
        }
        else {
            if(player2Scoreboard != null) setText(player2Scoreboard.GetPlayerName() + "\'s turn");
        }
    }
}
