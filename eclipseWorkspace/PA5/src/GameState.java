public class GameState {
    public int player1Score;
    public int player2Score;
    public boolean isPlayer1Turn;
    public boolean isGameOver;
    public boolean isPlayer1Winner;
    public String player1Name;
    public String player2Name;
    public GameBox[][] gameBoxes;

    public GameState(
        int player1Score,
        int player2Score,
        boolean isPlayer1Turn,
        boolean isGameOver,
        boolean isPlayer1Winner,
        String player1Name,
        String player2Name,
        GameBox[][] gameBoxes
    ) {
        super();


    }
}
