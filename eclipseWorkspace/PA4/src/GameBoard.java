import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Graphics;

public class GameBoard extends JPanel {
    //length of the columns
    //TODO : MAKE THESE VARIABLES LATER ON SO THAT YOU CAN PICK BOARD SIZE
    private final int BOX_SIZE = 70;
    private final int POINT_DIAMETER = 15;
    private final int MAX_BOARD_SIZE = 8;

    private int boardSize = MAX_BOARD_SIZE;

    private PlayerScoreboard player1Scoreboard = null;
    private PlayerScoreboard player2Scoreboard = null;
    private TurnScoreboard turnScoreboard = null;

    //array of column points
    private GameBox boardBoxes[][] = null;

    public void SetBoardSize(int newSize) {
    	if (!(newSize > MAX_BOARD_SIZE)) {
            boardSize = newSize;
        }
    }
    
    //this function will set up the game board at the start of the game
    public void InitGameBoard(PlayerScoreboard player1Scoreboard, PlayerScoreboard player2Scoreboard, TurnScoreboard turnScoreboard) {
        this.player1Scoreboard = player1Scoreboard;
        this.player2Scoreboard = player2Scoreboard;
        this.turnScoreboard = turnScoreboard;

        boardBoxes = new GameBox[boardSize][boardSize]; 
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                //create a new box
                GameBox newGameBox = new GameBox();
                //create the 4 points for the box
                newGameBox.topLeftCorner = new Point(i * BOX_SIZE, j * BOX_SIZE);
                newGameBox.topRightCorner = new Point((i + 1) * BOX_SIZE, (j) * BOX_SIZE);
                newGameBox.bottomLeftCorner = new Point((i) * BOX_SIZE, (j + 1) * BOX_SIZE);
                newGameBox.bottomRightCorner = new Point((i + 1) * BOX_SIZE, (j + 1) * BOX_SIZE);
                //add the box to the board boxes array
                boardBoxes[i][j] = newGameBox;
            }
        }
        repaint();
    }

    //this function will update the boxes array
    //returns true if the sides selected on the boxes were not already selected
    private boolean UpdateBoxes(int x, int y){
        int column = x / BOX_SIZE;
        int row = y / BOX_SIZE;

        //because there will be 9 columns for an 8x8 board this keeps you from indexing out of range if you click at the edges
        //TODO : FIX THIS IT DOESN'T WORK IF YOU REALLY CLICK OUT THERE
        if (column == boardBoxes.length) --column;
        if (row == boardBoxes.length) --row;

        float columnDistanceFromLeft = ((float)x / BOX_SIZE) - column;
        float rowDistanceFromTop = ((float)y / BOX_SIZE) - row;

        //if the mouse click is in the top left corner
        //TODO : ADD SOME COMMENTS
        if (Math.round(columnDistanceFromLeft) == 0 && Math.round(rowDistanceFromTop) == 0) {
            //if the click was closer to the left side
            if (columnDistanceFromLeft <= rowDistanceFromTop) {
                if (boardBoxes[column][row].isLeftSideClaimed) return false;
                
                boardBoxes[column][row].isLeftSideClaimed = true;
                UpdateScoreboard(boardBoxes[column][row]);
                
                if (!(column - 1 < 0)) {
                    boardBoxes[column - 1][row].isRightSideClaimed = true;
                    UpdateScoreboard(boardBoxes[column - 1][row]);
                }
            }
            //else the mouse click was closer to the top side
            else {
                if (boardBoxes[column][row].isTopSideClaimed) return false;
                
                boardBoxes[column][row].isTopSideClaimed = true;
                UpdateScoreboard(boardBoxes[column][row]);
                
                if (!(row - 1 < 0)) {
                    boardBoxes[column][row - 1].isBottomSideClaimed = true;
                    UpdateScoreboard(boardBoxes[column][row - 1]);
                }
            }
        }

        //top right corner
        if (Math.round(columnDistanceFromLeft) == 1 && Math.round(rowDistanceFromTop) == 0) {
            if (1 - columnDistanceFromLeft <= rowDistanceFromTop) {
                if (boardBoxes[column][row].isRightSideClaimed) return false;
                
                boardBoxes[column][row].isRightSideClaimed = true;
                UpdateScoreboard(boardBoxes[column][row]);

                if (!(column + 1 >= boardBoxes.length)) {
                    boardBoxes[column + 1][row].isLeftSideClaimed = true;
                    UpdateScoreboard(boardBoxes[column + 1][row]);
                }
            } 
            else {
                if (boardBoxes[column][row].isTopSideClaimed) return false;
                
                boardBoxes[column][row].isTopSideClaimed = true;
                UpdateScoreboard(boardBoxes[column][row]);

                if (!(row - 1 < 0)) {
                    boardBoxes[column][row - 1].isBottomSideClaimed = true;
                    UpdateScoreboard(boardBoxes[column][row - 1]);
                }
            }
        }
        
        //bottom left corner
        if (Math.round(columnDistanceFromLeft) == 0 && Math.round(rowDistanceFromTop) == 1) {
            if (columnDistanceFromLeft <= 1 - rowDistanceFromTop) {
                if (boardBoxes[column][row].isLeftSideClaimed) return false;
                
                boardBoxes[column][row].isLeftSideClaimed = true;
                UpdateScoreboard(boardBoxes[column][row]);

                if (!(column - 1 < 0)) {
                    boardBoxes[column - 1][row].isRightSideClaimed = true;
                    UpdateScoreboard(boardBoxes[column - 1][row]);
                } 
            } 
            else {
                if (boardBoxes[column][row].isBottomSideClaimed) return false;
                
                boardBoxes[column][row].isBottomSideClaimed = true;
                UpdateScoreboard(boardBoxes[column][row]);

                if (!(row + 1 >= boardBoxes.length)) {
                    boardBoxes[column][row + 1].isTopSideClaimed = true;
                    UpdateScoreboard(boardBoxes[column][row + 1]);
                }
            }
        }
        
        //bottom right corner
        if (Math.round(columnDistanceFromLeft) == 1 && Math.round(rowDistanceFromTop) == 1) {
            if (1 - columnDistanceFromLeft <= 1 - rowDistanceFromTop) {
                if (boardBoxes[column][row].isRightSideClaimed) return false;
                
                boardBoxes[column][row].isRightSideClaimed = true;
                UpdateScoreboard(boardBoxes[column][row]);
                
                if (!(column + 1 >= boardBoxes.length)) {
                    boardBoxes[column + 1][row].isLeftSideClaimed = true;
                    UpdateScoreboard(boardBoxes[column + 1][row]);
                }
            } 
            else {
                if (boardBoxes[column][row].isBottomSideClaimed) return false;
                
                boardBoxes[column][row].isBottomSideClaimed = true;
                UpdateScoreboard(boardBoxes[column][row]);
                
                if (!(row + 1 >= boardBoxes.length)) {
                    boardBoxes[column][row + 1].isTopSideClaimed = true;
                    UpdateScoreboard(boardBoxes[column][row + 1]);
                }
            }
        }

        return true;
    }


    private void UpdateScoreboard(GameBox box) {
        //determine if the just updated box has been fully claimed
        if (turnScoreboard == null) return;
        if(player1Scoreboard == null) return;
        if(player2Scoreboard == null) return;
        box.CheckIfClaimed(turnScoreboard.GetPlayerTurn());
        //if it has been claimed update the scoreboards
        if (box.isClaimed) {
            if (turnScoreboard.GetPlayerTurn()) {
                player1Scoreboard.score++;
            }
            else {
                player2Scoreboard.score++;
            }
            //repaint the scoreboards
            player1Scoreboard.repaint();
            player2Scoreboard.repaint();
        }
    }

    public boolean CheckIfGameOver(){
        for (GameBox[] gameBoxs : boardBoxes) {
            for (GameBox gameBox : gameBoxs) {
                if (!gameBox.isClaimed) {
                    return false;
                }
            }
        }
        return true;
    }

    public void HandleMouseClick(int x, int y) {

        if (turnScoreboard == null) return;
        if (player1Scoreboard == null) return;
        if (player2Scoreboard == null) return;

        boolean legalMove = UpdateBoxes(x, y);

        if (!legalMove) return;

        //TODO : make this a pop up
        if (CheckIfGameOver()){
            if (player1Scoreboard.score > player2Scoreboard.score) {
                System.out.println("PLAYER ONE WINS!");
            } 
            else {
                System.out.println("PLAYER TWO WINS!");
            }
        }

        turnScoreboard.SwitchTurn();
        turnScoreboard.repaint();
        
        repaint();
    }

    //this function will draw the 
    @Override
    public void paint(Graphics g) {
        if(player1Scoreboard == null) return;
        if(player2Scoreboard == null) return;

        super.paint(g);

        //this will draw out the whole array of points
        for (GameBox[] gameBoxs : boardBoxes) {
            for (GameBox gameBox : gameBoxs) {
                //draw the points on the boxes
                g.fillOval(gameBox.topLeftCorner.x, gameBox.topLeftCorner.y, POINT_DIAMETER, POINT_DIAMETER);
                g.fillOval(gameBox.topRightCorner.x, gameBox.topRightCorner.y, POINT_DIAMETER, POINT_DIAMETER);
                g.fillOval(gameBox.bottomLeftCorner.x, gameBox.bottomLeftCorner.y, POINT_DIAMETER, POINT_DIAMETER);
                g.fillOval(gameBox.bottomRightCorner.x, gameBox.bottomRightCorner.y, POINT_DIAMETER, POINT_DIAMETER);

                //fill in the lines
                if (gameBox.isTopSideClaimed) g.drawLine(gameBox.topLeftCorner.x + POINT_DIAMETER/2, gameBox.topLeftCorner.y + POINT_DIAMETER/2, gameBox.topRightCorner.x + POINT_DIAMETER/2, gameBox.topRightCorner.y + POINT_DIAMETER/2);
                if (gameBox.isBottomSideClaimed) g.drawLine(gameBox.bottomLeftCorner.x + POINT_DIAMETER/2, gameBox.bottomLeftCorner.y + POINT_DIAMETER/2, gameBox.bottomRightCorner.x + POINT_DIAMETER/2, gameBox.bottomRightCorner.y + POINT_DIAMETER/2);
                if (gameBox.isLeftSideClaimed) g.drawLine(gameBox.topLeftCorner.x + POINT_DIAMETER/2, gameBox.topLeftCorner.y + POINT_DIAMETER/2, gameBox.bottomLeftCorner.x + POINT_DIAMETER/2, gameBox.bottomLeftCorner.y + POINT_DIAMETER/2);
                if (gameBox.isRightSideClaimed) g.drawLine(gameBox.bottomRightCorner.x + POINT_DIAMETER/2, gameBox.bottomRightCorner.y + POINT_DIAMETER/2, gameBox.topRightCorner.x + POINT_DIAMETER/2, gameBox.topRightCorner.y + POINT_DIAMETER/2);

                if (gameBox.isClaimed){
                    if (gameBox.isClaimedByPlayer1) {
                        g.drawString(player1Scoreboard.GetPlayerName().charAt(0) + "",gameBox.topLeftCorner.x + BOX_SIZE / 2, gameBox.topLeftCorner.y + BOX_SIZE / 2);
                    }
                    else {
                        g.drawString(player2Scoreboard.GetPlayerName().charAt(0) + "",gameBox.topLeftCorner.x + BOX_SIZE / 2, gameBox.topLeftCorner.y + BOX_SIZE / 2);
                    }
                }
            }
        }
    }
}
