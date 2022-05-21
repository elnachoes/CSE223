import javax.swing.JPanel;
import java.awt.Point;
import java.io.Console;
import java.awt.Graphics;

public class GameBoard extends JPanel {
    //length of the columns
    //TODO : MAKE THESE VARIABLES LATER ON SO THAT YOU CAN PICK BOARD SIZE
    private final int BOX_SIZE = 70;
    private final int BOARD_SIZE = 8;
    private final int POINT_DIAMETER = 15;


    private boolean isPlayer1Turn = true;

    
    private PlayerScoreboard testplayer = null;

    //array of column points
    private GameBox boardBoxes[][];

    public GameBoard(PlayerScoreboard player) {
        //the number of boxes inside of a grid of lines 9x9 will be 8x8 so that is why it is instantiated this way
        testplayer = player;
        boardBoxes = new GameBox[BOARD_SIZE][BOARD_SIZE]; 
        InitGameBoard();
    }

    //this function will set up the game board at the start of the game
    private void InitGameBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
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
    }

    //this function will update the boxes array
    //returns true if the sides selected on the boxes were not already selected
    private boolean UpdateBoxes(int x, int y){
        //TODO : DETERMINE IF THE BOXES HAVE ALREADY BEEN CHECKED OR NOT

        int column = x / BOX_SIZE;
        int row = y / BOX_SIZE;

        //because there will be 9 columns for an 8x8 board this keeps you from indexing out of range if you click at the edges
        //TODO : FIX THIS IT DOESN'T WORK IF YOU REALLY CLICK OUT THERE
        if (column == boardBoxes.length) --column;
        if (row == boardBoxes.length) --row;

        float columnDistanceFromLeft = ((float)x / BOX_SIZE) - column;
        float rowDistanceFromTop = ((float)y / BOX_SIZE) - row;

        //if the mouse click is in the top left corner
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
        box.CheckIfClaimed(isPlayer1Turn);
        if (box.isClaimed) {
            if (isPlayer1Turn) {
                testplayer.score++;
            } 
            else {
                
            }
        }

        testplayer.repaint();
    }


    public void HandleMouseClick(int x, int y) {

        boolean legalMove = UpdateBoxes(x, y);

        if (!legalMove) return;

        
        repaint();
    }

    //this function will draw the 
    @Override
    public void paint(Graphics g) {
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

                //TODO DRAW A LETTER IN THE BOX WHO OWNS THE BOX
            }
        }
    }
}
