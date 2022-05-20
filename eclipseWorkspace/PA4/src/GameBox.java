import java.awt.Point;

//this class will be the object that will store the data for what boxes have been filled in the game of Dots
//it is basically just a pile of booleans that will get used for each box on the GameBoard class
public class GameBox {
    public boolean isLeftSideClaimed = false;
    public boolean isRightSideClaimed = false;
    public boolean isTopSideClaimed = false;
    public boolean isBottomSideClaimed = false;
    public boolean isClaimed = false;
    public boolean isClaimedByPlayer1 = false;

    public Point topLeftCorner = null;
    public Point topRightCorner = null;
    public Point bottomLeftCorner = null;
    public Point bottomRightCorner = null;
}