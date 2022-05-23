import java.awt.Point;

//this class will be the object that will store the data for what boxes have been filled in the game of Dots
//it is basically just a pile of booleans that will get used for each box on the GameBoard class
public class GameBox {
    //booleans for if the sides are claimed
    public boolean isLeftSideClaimed = false;
    public boolean isRightSideClaimed = false;
    public boolean isTopSideClaimed = false;
    public boolean isBottomSideClaimed = false;

    //booleans for who claimed the sides
    public boolean isLeftSideClaimedByPlayer1 = false;
    public boolean isRightSideClaimedByPlayer1 = false;
    public boolean isTopSideClaimedByPlayer1 = false;
    public boolean isBottomSideClaimedByPlayer1 = false;

    //booleans for if the whole box is claimed and by whom
    public boolean isClaimed = false;
    public boolean isClaimedByPlayer1 = false;

    //points for the corners of the rects
    public Point topLeftCorner = null;
    public Point topRightCorner = null;
    public Point bottomLeftCorner = null;
    public Point bottomRightCorner = null;

    //initial of the player that captured the box
    public String capturedPlayerInitial = null;

    //boolean that checks if the box was claimed and if it was declare that it was claimed
    public boolean CheckIfClaimed(boolean isPlayer1Turn){
        if (isBottomSideClaimed && isLeftSideClaimed && isRightSideClaimed && isTopSideClaimed) {
            isClaimed = true;
            if (isPlayer1Turn) isClaimedByPlayer1 = true;
            return true;
        }
        return false;
    }
}