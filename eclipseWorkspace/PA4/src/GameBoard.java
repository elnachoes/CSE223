import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Graphics;

public class GameBoard extends JPanel {
	//length of the columns
	//TODO : MAKE THESE VARIABLES LATER ON SO THAT YOU CAN PICK BOARD SIZE
	final int COLUMN_ROW_LENGTH = 100;
	final int COLUMN_ROW_COUNT = 9;
	final int COLUMN_ROW_BOX_COUNT = COLUMN_ROW_COUNT - 1;
	final int POINT_DIAMETER = 5;
	
	//array of column points
	Point boardPoints[][];
	GameBox boardBoxes[][];

	public GameBoard(){
		boardPoints = new Point[COLUMN_ROW_COUNT][COLUMN_ROW_COUNT]; 
		//the number of boxes inside of a grid of lines 9x9 will be 8x8 so that is why it is instantiated this way
		boardBoxes = new GameBox[COLUMN_ROW_BOX_COUNT][COLUMN_ROW_BOX_COUNT]; 
		InitGameBoard();
	}

	//this function will set up the game board at the start of the game
	//it will setup all of the points in boardPoints
	private void InitGameBoard(){
		for (int i = 0; i < COLUMN_ROW_BOX_COUNT; i++) {
			for (int j = 0; j < COLUMN_ROW_BOX_COUNT; j++) {
				boardBoxes[i][j] = new GameBox();
			}
		}

		for (int i = 0; i < COLUMN_ROW_COUNT; i++) {
			for (int j = 0; j < COLUMN_ROW_COUNT; j++) {
				boardPoints[i][j] = new Point(i * COLUMN_ROW_LENGTH, j * COLUMN_ROW_LENGTH);
			}
		}

		for (int i = 0; i < COLUMN_ROW_COUNT; i++) {
			for (int j = 0; j < COLUMN_ROW_COUNT; j++) {

				// WARNING : THIS MIGHT BE SETUP SLIGHTLY WRONG CHECK LATER

				if (i % 2 == 0 && j % 2 == 0) {
					boardBoxes[i/2][j/2].topLeftCorner = boardPoints[i][j];
				}
				if (i % 2 == 1 && j % 2 == 0) {
					boardBoxes[i/2][j/2].topRightCorner = boardPoints[i][j];
				}
				if (i % 2 == 0 && j % 2 == 1) {
					boardBoxes[i/2][j/2].bottomLeftCorner = boardPoints[i][j];
				}
				if (i % 2 == 1 && j % 2 == 1) {
					boardBoxes[i/2][j/2].bottomRightCorner = boardPoints[i][j];
				}
			}
		}
	}

	//this function will setup ALL the data necessary for 
	public void DrawBoard()
	{
		repaint();
	}

	public void paint(Graphics g){
		super.paint(g);

		//this will draw out the whole array of points
		for (int i = 0; i < COLUMN_ROW_COUNT; i++) {
			for (int j = 0; j < COLUMN_ROW_COUNT; j++) {
				g.fillOval(boardPoints[i][j].x, boardPoints[i][j].y, POINT_DIAMETER, POINT_DIAMETER);
			}
		}

		for (int i = 0; i < COLUMN_ROW_BOX_COUNT; i++) {
			for (int j = 0; j < COLUMN_ROW_BOX_COUNT; j++) {
				// currentBox

				//TODO : HAVE THE SIDES OF THE BOXES GET DRAW BASED ON THEIR STATES

			}
		}
	}
}
