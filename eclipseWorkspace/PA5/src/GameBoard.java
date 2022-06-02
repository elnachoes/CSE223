import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Color;

//this is the main gameboard class that stores which boxes and sides are claimed and handles user input and draws the game itself
//author : corbin martin
public class GameBoard extends JPanel {
    //pixel size constants
    private final int BOX_SIZE = 50;
    private final int POINT_DIAMETER = 15;

    //board sizes
    private final int MAX_BOARD_SIZE = 12;
    private final int DEFAULT_BOARD_SIZE = 8;
    
    //sibling components for calling methods on
    private PlayerScoreboard player1Scoreboard = null;
    private PlayerScoreboard player2Scoreboard = null;
    private TurnScoreboard turnScoreboard = null;
    private NotificationBoard notificationBoard = null;
    private JButton okButton = null;
    
    private int boardSize = DEFAULT_BOARD_SIZE;
    private boolean bonusTurn = false;
    
    //array of boxes to store the state of the game
    private GameBox boardBoxes[][] = null;

    private NetworkManager networkManager = null;

    //this method that sets the board size and stops it from going over a maximum
    public void SetBoardSize(int newSize) {
    	if (!(newSize > MAX_BOARD_SIZE)) {
            boardSize = newSize;
        }
    }
    
    public int GetBoardSize() { return boardSize; }

    //this method will set up the game board at the start of the game
    //this method will get the sibling components and initialize the box array
    public void InitGameBoard() {
        //get the sibling components as you would in a game engine like Godot
        //this will allow GameBoard to have a reference to multiple of the other components and call methods on them
        for (Component component : getParent().getComponents()) {
            if (component.getName() == null) continue;

            if (component.getName().compareTo("player1Scoreboard") == 0) {
                this.player1Scoreboard = (PlayerScoreboard)component;
            }
            if (component.getName().compareTo("player2Scoreboard") == 0) {
                this.player2Scoreboard = (PlayerScoreboard)component;
            }
            if (component.getName().compareTo("notificationBoard") == 0) {
                this.notificationBoard = (NotificationBoard)component;
            }
            if (component.getName().compareTo("turnScoreboard") == 0) {
                this.turnScoreboard = (TurnScoreboard)component;
            }
            if (component.getName().compareTo("okButton") == 0) {
                this.okButton = (JButton)component;
            }

            Dots frame = (Dots)getTopLevelAncestor();
            networkManager = frame.networkManager;
        }

        // initialize the board boxes
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

    //this method will update the boxes array
    //returns true if the sides selected on the boxes were not already selected
    //this method also tracks if the current player gets to have a bonus turn for capturing a box
    private boolean UpdateBoxes(int x, int y) {
        //if they clicked outside the game area that is an illegal move return false
        if (((float)x / BOX_SIZE) + (2.f/BOX_SIZE) > (float)boardBoxes.length + .5f || ((float)y / BOX_SIZE) + (2.f/BOX_SIZE) > (float)boardBoxes.length + .5f) return false;
        
        //the current column and row that the player clicked on
        int column = x / BOX_SIZE;
        int row = y / BOX_SIZE;

        //because there will be 1 more column and row than there are boxes in the array subtract one if the click was at the very edges of the board
        if (column == boardBoxes.length) --column;
        if (row == boardBoxes.length) --row;
        
        //these are the flags that track if the current player will get a bonus turn or not when updating the boxes
        boolean firstScoreboardUpdate = false;
        boolean secondScoreboardUpdate = false;
        
        //these variables get the distances from 0 - 1 from the top of the box to the bottom and from the left of the box to the righ
        float columnDistanceFromLeft = ((float)x / BOX_SIZE) - column;
        float rowDistanceFromTop = ((float)y / BOX_SIZE) - row;

        //if the mouse click is in the top left corner
        if (Math.round(columnDistanceFromLeft) == 0 && Math.round(rowDistanceFromTop) == 0) {
            //if the click was closer to the left side
            if (columnDistanceFromLeft <= rowDistanceFromTop) {
                //if that box was already claimed return false this was an illegal move
                if (boardBoxes[column][row].isLeftSideClaimed) return false;
                
                //claim the left side
                boardBoxes[column][row].isLeftSideClaimed = true;
                if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row].isLeftSideClaimedByPlayer1 = true;
                else boardBoxes[column][row].isLeftSideClaimedByPlayer1 = false;

                //update the scoreboard
                firstScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row]);
                
                //if the click was not on an edge claim the right side of the left box and update the scoreboard
                if (!(column - 1 < 0)) {
                    boardBoxes[column - 1][row].isRightSideClaimed = true;
                    if (turnScoreboard.GetPlayerTurn()) boardBoxes[column - 1][row].isRightSideClaimedByPlayer1 = true;
                    else boardBoxes[column - 1][row].isRightSideClaimedByPlayer1 = false;

                    secondScoreboardUpdate = UpdateScoreboard(boardBoxes[column - 1][row]);
                }
            }
            //else the mouse click was closer to the top side
            else {
                //if that box was already claimed return false this was an illegal move
                if (boardBoxes[column][row].isTopSideClaimed) return false;
                
                //claim the top side
                boardBoxes[column][row].isTopSideClaimed = true;
                if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row].isTopSideClaimedByPlayer1 = true;
                else boardBoxes[column][row].isTopSideClaimedByPlayer1 = false;

                //update the scoreboard
                firstScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row]);
                
                //if the click was not on an edge claim the top side of the bottom box and update the scoreboard
                if (!(row - 1 < 0)) {
                    boardBoxes[column][row - 1].isBottomSideClaimed = true;
                    if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row - 1].isBottomSideClaimedByPlayer1 = true;
                    else boardBoxes[column][row - 1].isBottomSideClaimedByPlayer1 = false;

                    secondScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row - 1]);
                }
            }
        }

        //top right corner
        else if (Math.round(columnDistanceFromLeft) == 1 && Math.round(rowDistanceFromTop) == 0) {
            if (1 - columnDistanceFromLeft <= rowDistanceFromTop) {
                //if that box was already claimed return false this was an illegal move
                if (boardBoxes[column][row].isRightSideClaimed) return false;
                
                //claim the right side
                boardBoxes[column][row].isRightSideClaimed = true;
                if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row].isRightSideClaimedByPlayer1 = true;
                else boardBoxes[column][row].isRightSideClaimedByPlayer1 = false;

                //update the scoreboard
                firstScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row]);

                //if the click was not on an edge claim the left side of the right box and update the scoreboard
                if (!(column + 1 >= boardBoxes.length)) {
                    boardBoxes[column + 1][row].isLeftSideClaimed = true;
                    if (turnScoreboard.GetPlayerTurn()) boardBoxes[column + 1][row].isLeftSideClaimedByPlayer1 = true;
                    else boardBoxes[column + 1][row].isLeftSideClaimedByPlayer1 = false;

                    secondScoreboardUpdate = UpdateScoreboard(boardBoxes[column + 1][row]);
                }
            } 
            else {
                //if that box was already claimed return false this was an illegal move
                if (boardBoxes[column][row].isTopSideClaimed) return false;
                
                //claim the top side
                boardBoxes[column][row].isTopSideClaimed = true;
                if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row].isTopSideClaimedByPlayer1 = true;
                else boardBoxes[column][row].isTopSideClaimedByPlayer1 = false;

                //update the scoreboard
                firstScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row]);

                //if the click was not on an edge claim the bottom side of the top box and update the scoreboard
                if (!(row - 1 < 0)) {
                    boardBoxes[column][row - 1].isBottomSideClaimed = true;
                    if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row - 1].isBottomSideClaimedByPlayer1 = true;
                    else boardBoxes[column][row - 1].isBottomSideClaimedByPlayer1 = false;

                    secondScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row - 1]);
                }
            }
        }
        
        //bottom left corner
        else if (Math.round(columnDistanceFromLeft) == 0 && Math.round(rowDistanceFromTop) == 1) {
            if (columnDistanceFromLeft <= 1 - rowDistanceFromTop) {
                //if that box was already claimed return false this was an illegal move
                if (boardBoxes[column][row].isLeftSideClaimed) return false;
                
                //claim the left side
                boardBoxes[column][row].isLeftSideClaimed = true;
                if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row].isLeftSideClaimedByPlayer1 = true;
                else boardBoxes[column][row].isLeftSideClaimedByPlayer1 = false;

                //update the scoreboard
                firstScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row]);

                //if the click was not on an edge claim the right side of the left box and update the scoreboard
                if (!(column - 1 < 0)) {
                    boardBoxes[column - 1][row].isRightSideClaimed = true;
                    if (turnScoreboard.GetPlayerTurn()) boardBoxes[column - 1][row].isRightSideClaimedByPlayer1 = true;
                    else boardBoxes[column - 1][row].isRightSideClaimedByPlayer1 = false;

                    secondScoreboardUpdate = UpdateScoreboard(boardBoxes[column - 1][row]);
                } 
            } 
            else {
                //if that box was already claimed return false this was an illegal move
                if (boardBoxes[column][row].isBottomSideClaimed) return false;
                
                //claim the bottom side
                boardBoxes[column][row].isBottomSideClaimed = true;
                if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row].isBottomSideClaimedByPlayer1 = true;
                else boardBoxes[column][row].isBottomSideClaimedByPlayer1 = false;

                //update the scoreboard
                firstScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row]);

                //if the click was not on an edge claim the top side of the bottom box and update the scoreboard
                if (!(row + 1 >= boardBoxes.length)) {
                    boardBoxes[column][row + 1].isTopSideClaimed = true;
                    if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row + 1].isTopSideClaimedByPlayer1 = true;
                    else boardBoxes[column][row + 1].isTopSideClaimedByPlayer1 = false;

                    secondScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row + 1]);
                }
            }
        }
        
        //bottom right corner
        else if (Math.round(columnDistanceFromLeft) == 1 && Math.round(rowDistanceFromTop) == 1) {
            if (1 - columnDistanceFromLeft <= 1 - rowDistanceFromTop) {
                //if that box was already claimed return false this was an illegal move
                if (boardBoxes[column][row].isRightSideClaimed) return false;
                
                boardBoxes[column][row].isRightSideClaimed = true;
                if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row].isRightSideClaimedByPlayer1 = true;
                else boardBoxes[column][row].isRightSideClaimedByPlayer1 = false;

                firstScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row]);
                
                if (!(column + 1 >= boardBoxes.length)) {
                    boardBoxes[column + 1][row].isLeftSideClaimed = true;
                    if (turnScoreboard.GetPlayerTurn()) boardBoxes[column + 1][row].isLeftSideClaimedByPlayer1 = true;
                    else boardBoxes[column + 1][row].isLeftSideClaimedByPlayer1 = false;

                    secondScoreboardUpdate = UpdateScoreboard(boardBoxes[column + 1][row]);
                }
            } 
            else {
                //if that box was already claimed return false this was an illegal move
                if (boardBoxes[column][row].isBottomSideClaimed) return false;
                
                //claim the bottom side
                boardBoxes[column][row].isBottomSideClaimed = true;
                if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row].isBottomSideClaimedByPlayer1 = true;
                else boardBoxes[column][row].isBottomSideClaimedByPlayer1 = false;

                //update the scoreboard
                firstScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row]);
                
                //if the click was not on an edge claim the top side of the bottom box and update the scoreboard
                if (!(row + 1 >= boardBoxes.length)) {
                    boardBoxes[column][row + 1].isTopSideClaimed = true;
                    if (turnScoreboard.GetPlayerTurn()) boardBoxes[column][row + 1].isTopSideClaimedByPlayer1 = true;
                    else boardBoxes[column][row + 1].isTopSideClaimedByPlayer1 = true;

                    secondScoreboardUpdate = UpdateScoreboard(boardBoxes[column][row + 1]);
                }

            }
        }

        // if the scoreboard was updated and either the update or the second update returned true for a bonus turn set bonus turn to true else no bonus turn
        if (firstScoreboardUpdate || secondScoreboardUpdate) bonusTurn = true;
        else bonusTurn = false;

        return true;
    }

    //this method will update the player scoreboards by incrementing their scores and repainting them
    //it returns true if a box was claimed and false if it wasn't claimed
    private boolean UpdateScoreboard(GameBox box) {
        //determine if the just updated box has been fully claimed
        if (turnScoreboard == null) return false;
        if (player1Scoreboard == null) return false;
        if (player2Scoreboard == null) return false;

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
            return true;
        }
        return false;
    }
    
    //this method returns true if the game is over else false
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

    //this method will handle a mouse click by checking if they made a legal move, checking if the game is over
    public void HandleMouseClick(int x, int y) {
        if (turnScoreboard == null) return;
        if (player1Scoreboard == null) return;
        if (player2Scoreboard == null) return;
        if (networkManager == null) return;

        //update boxes will update the boxes array and if it returns false the player made an illegal move so return
        if (!UpdateBoxes(x, y)) return;

        //notify the player who won or if there was a tie with the notificationboard
        if (CheckIfGameOver()){
            if (player1Scoreboard.score > player2Scoreboard.score) {
                notificationBoard.message = player1Scoreboard.GetPlayerName() + " WINS!";
            } 
            else if (player1Scoreboard.score < player2Scoreboard.score) {
                notificationBoard.message = player2Scoreboard.GetPlayerName() + " WINS!";
            }
            else {
                notificationBoard.message = "TIE!";
            }

            notificationBoard.setVisible(true);
            notificationBoard.repaint();
            okButton.setVisible(true);
        }

        //if there wasnt a bonus turn switch turns
        if (!bonusTurn) {
            turnScoreboard.SwitchTurn();
            turnScoreboard.repaint();
        }

        if (networkManager.isServer()) {
            networkManager.SyncClickInput(x, y);
        }

        repaint();
    }

    //this function will draw the gameboard
    @Override
    public void paint(Graphics g) {
        if(player1Scoreboard == null) return;
        if(player2Scoreboard == null) return;

        super.paint(g);

        //this will draw out the whole array of points
        for (GameBox[] gameBoxs : boardBoxes) {
            for (GameBox gameBox : gameBoxs) {
                
                //draw every point on every box
                g.setColor(Color.ORANGE);
                g.fillOval(gameBox.topLeftCorner.x, gameBox.topLeftCorner.y, POINT_DIAMETER, POINT_DIAMETER);
                g.fillOval(gameBox.topRightCorner.x, gameBox.topRightCorner.y, POINT_DIAMETER, POINT_DIAMETER);
                g.fillOval(gameBox.bottomLeftCorner.x, gameBox.bottomLeftCorner.y, POINT_DIAMETER, POINT_DIAMETER);
                g.fillOval(gameBox.bottomRightCorner.x, gameBox.bottomRightCorner.y, POINT_DIAMETER, POINT_DIAMETER);

                //fill in the claimed lines on every box
                if (gameBox.isTopSideClaimed) {
                    if (gameBox.isTopSideClaimedByPlayer1) g.setColor(Color.RED);
                    else g.setColor(Color.GREEN);
                    g.drawLine(gameBox.topLeftCorner.x + POINT_DIAMETER/2, gameBox.topLeftCorner.y + POINT_DIAMETER/2, gameBox.topRightCorner.x + POINT_DIAMETER/2, gameBox.topRightCorner.y + POINT_DIAMETER/2);

                }
                if (gameBox.isBottomSideClaimed) {
                    if (gameBox.isBottomSideClaimedByPlayer1) g.setColor(Color.RED);
                    else g.setColor(Color.GREEN);
                    g.drawLine(gameBox.bottomLeftCorner.x + POINT_DIAMETER/2, gameBox.bottomLeftCorner.y + POINT_DIAMETER/2, gameBox.bottomRightCorner.x + POINT_DIAMETER/2, gameBox.bottomRightCorner.y + POINT_DIAMETER/2);
                }
                if (gameBox.isLeftSideClaimed) {
                    if (gameBox.isLeftSideClaimedByPlayer1) g.setColor(Color.RED);
                    else g.setColor(Color.GREEN);
                    g.drawLine(gameBox.topLeftCorner.x + POINT_DIAMETER/2, gameBox.topLeftCorner.y + POINT_DIAMETER/2, gameBox.bottomLeftCorner.x + POINT_DIAMETER/2, gameBox.bottomLeftCorner.y + POINT_DIAMETER/2);
                }
                if (gameBox.isRightSideClaimed) {
                    if (gameBox.isRightSideClaimedByPlayer1) g.setColor(Color.RED);
                    else g.setColor(Color.GREEN);
                    g.drawLine(gameBox.bottomRightCorner.x + POINT_DIAMETER/2, gameBox.bottomRightCorner.y + POINT_DIAMETER/2, gameBox.topRightCorner.x + POINT_DIAMETER/2, gameBox.topRightCorner.y + POINT_DIAMETER/2);
                }

                //draw the claimed initials if the box is claimed
                if (gameBox.isClaimed){
                    if (gameBox.isClaimedByPlayer1) {
                        g.setColor(Color.RED);
                        g.drawString(player1Scoreboard.GetPlayerName().charAt(0) + "",gameBox.topLeftCorner.x + BOX_SIZE / 2, gameBox.topLeftCorner.y + BOX_SIZE / 2);
                    }
                    else {
                        g.setColor(Color.GREEN);
                        g.drawString(player2Scoreboard.GetPlayerName().charAt(0) + "",gameBox.topLeftCorner.x + BOX_SIZE / 2, gameBox.topLeftCorner.y + BOX_SIZE / 2);
                    }
                }
            }
        }
    }
}
