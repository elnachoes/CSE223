import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//this is the main jframe class that contains the entire window
//author : corbin martin
public class Dots extends JFrame {
    //setting up the jcompnent variables that will be used for the jframe
    private GameBoard gameBoard = null;
    private PlayerScoreboard player2Scoreboard = null;
    private PlayerScoreboard player1Scoreboard = null;
    private NotificationBoard notificationBoard = null;
    private TurnScoreboard turnScoreboard = null;
    private JTextField setPlayer1NameInput = null;
    private JTextField setPlayer2NameInput = null;
    private JTextField setBoardSizeInput;
    private JPanel contentPane = null;
    private JButton okButton = null;

    //this function is the main function and it instantiates the frame
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dots frame = new Dots();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //this constructor will build and partially initialize all the elements within the jframe 
    //some of the initialization of the elements such as the scoreboards and gameboard need to all be instantiated before the game can begin
    //this is because each of those custom component classes need references to each other to invoke methods on each other
    //once everything is partially initialized the NewGame method will be called and it will start the game
    public Dots() {
    	setTitle("Dots!");

        //set up the main jpanel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1069, 658);
        contentPane = new JPanel();
        contentPane.setBackground(Color.GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        //set up the notification board jlable
        //set the visibility of the notification jlable to false and only show it when there is a pop up
        notificationBoard = new NotificationBoard();
        notificationBoard.setName("notificationBoard");
        notificationBoard.setForeground(Color.WHITE);
        notificationBoard.setBackground(Color.RED);
        notificationBoard.setBounds(613, 363, 430, 90);
        notificationBoard.setVisible(false);
        contentPane.add(notificationBoard);
        
        //set up the ok button
        //the ok button will only show itself when there is an pop up notification
        okButton = new JButton("OK");
        okButton.setName("okButton");
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //once the ok button is clicked hide the notification board and the ok button
                notificationBoard.message = null;
                notificationBoard.setVisible(false);
                okButton.setVisible(false);
            }
        });
        okButton.setBounds(954, 464, 89, 23);
        contentPane.add(okButton);
        okButton.setVisible(false);

        //the start game button will be used to start the game and as a reset game button
        //the start button will set all the critical game components as visible then start a new game
        JButton startGameButton = new JButton("start game");
        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //once the start button is pressed use it as a restart button afterwards
                startGameButton.setText("restart game");
                startGameButton.repaint();

                //if the critical components for the game to be played aren't yet initialized return out of this function
                if (turnScoreboard == null || player1Scoreboard == null || player2Scoreboard == null || gameBoard == null) return;
                else{
                    //set all the game components to be visible when the game is started
                    turnScoreboard.setVisible(true);
                    player1Scoreboard.setVisible(true);
                    player2Scoreboard.setVisible(true);
                    gameBoard.setVisible(true);
                }

                //start a new game
                NewGame();
            }
        });
        startGameButton.setBounds(613, 40, 427, 23);
        contentPane.add(startGameButton);

        //player1scoreboard will track the name and score of the first player
        //the default name for player 1 is player 1 if you dont give it a name
        //at the start of the game the scoreboard is invisible until the game is started
        player1Scoreboard = new PlayerScoreboard("Player 1");
        player1Scoreboard.setName("player1Scoreboard");
        player1Scoreboard.setForeground(Color.WHITE);
        player1Scoreboard.setBounds(613, 545, 200, 23);
        contentPane.add(player1Scoreboard);
        player1Scoreboard.setVisible(false);
        
        //player2scoreboard will track the name and score of the first player
        //the default name for player 2 is player 2 if you dont give it a name
        //at the start of the game the scoreboard is invisible until the game is started
        player2Scoreboard = new PlayerScoreboard("Player 2");
        player2Scoreboard.setName("player2Scoreboard");
        player2Scoreboard.setForeground(Color.WHITE);
        player2Scoreboard.setBounds(613, 579, 200, 23);
        contentPane.add(player2Scoreboard);
        player2Scoreboard.setVisible(false);
        
        //turnScoreboard tracks who's turn it is 
        //the turnscoreboard will be invisible until the game is started
        turnScoreboard = new TurnScoreboard();
        turnScoreboard.setName("turnScoreboard");
        turnScoreboard.setBounds(613, 511, 291, 23);
        contentPane.add(turnScoreboard);
        turnScoreboard.setVisible(false);

        //gameBoard is where the main game is displayed
        //gameBoard will handle a mouse click if a mouse clicks on it
        //gameBoard is invisible until the game is started
        gameBoard = new GameBoard();
        gameBoard.setName("gameBoard");
        gameBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameBoard.HandleMouseClick(e.getX(),e.getY());
            }
        });
        gameBoard.setBounds(10, 11, 593, 591);
        gameBoard.setBackground(Color.BLACK);
        contentPane.add(gameBoard);
        gameBoard.setVisible(false);
        
        //input box that allows the player 1 name to be set
        setPlayer1NameInput = new JTextField();
        setPlayer1NameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    player1Scoreboard.SetPlayerName(setPlayer1NameInput.getText());
                    gameBoard.repaint();
                }
            }
        });
        setPlayer1NameInput.setBounds(613, 105, 243, 20);
        contentPane.add(setPlayer1NameInput);
        setPlayer1NameInput.setColumns(10);
        
        //input box that allows the player 2 name to be set
        setPlayer2NameInput = new JTextField();
        setPlayer2NameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    player2Scoreboard.SetPlayerName(setPlayer2NameInput.getText());
                    gameBoard.repaint();
                }
            }
        });
        setPlayer2NameInput.setBounds(613, 136, 243, 20);
        contentPane.add(setPlayer2NameInput);
        setPlayer2NameInput.setColumns(10);
        
        
        //input button that allows the player 1 name to be set
        JButton setPlayer1NameButton = new JButton("Set Player 1 Name");
        setPlayer1NameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                player1Scoreboard.SetPlayerName(setPlayer1NameInput.getText());
                gameBoard.repaint();
            }
        });
        setPlayer1NameButton.setBounds(866, 104, 174, 23);
        contentPane.add(setPlayer1NameButton);
        
        //input button that allows the player 2 name to be set
        JButton setPlayer2NameButton = new JButton("Set Player 2 Name");
        setPlayer2NameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                player2Scoreboard.SetPlayerName(setPlayer2NameInput.getText());
                gameBoard.repaint();
            }
        });
        setPlayer2NameButton.setBounds(866, 135, 174, 23);
        contentPane.add(setPlayer2NameButton);
        
        
        //input box that allows the board size to be set
        setBoardSizeInput = new JTextField();
        setBoardSizeInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    //TODO : make this a pop up
                    try {
                        gameBoard.SetBoardSize(Integer.parseInt(setBoardSizeInput.getText()));
                    }
                    catch (Exception f) { 
                        notificationBoard.message = "you have to put in a number for the board size";
                        notificationBoard.setVisible(true);
                        notificationBoard.repaint();
                        okButton.setVisible(true);
                        return;
                    }
                    NewGame();
                }
            }
        });
        setBoardSizeInput.setColumns(10);
        setBoardSizeInput.setBounds(613, 74, 243, 20);
        contentPane.add(setBoardSizeInput);
        
        //input button that allows the board size to be set
        JButton setBoardSize = new JButton("Set Board Size (max 12)");
        setBoardSize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO : make this a pop up
                try {
                    gameBoard.SetBoardSize(Integer.parseInt(setBoardSizeInput.getText()));
                }
                catch (Exception f) { 
                    notificationBoard.message = "you have to put in a number for the board size";
                    notificationBoard.setVisible(true);
                    notificationBoard.repaint();
                    okButton.setVisible(true);
                    return;
                }
                NewGame(); 
            }
        });
        setBoardSize.setBounds(866, 74, 174, 23);
        contentPane.add(setBoardSize);

        //start a new game
        NewGame();
    }
    
    //this function will initialize all the components of the game
    private void NewGame() {
        //initialize the components themselves
        player1Scoreboard.InitPlayerScoreboard();
        player2Scoreboard.InitPlayerScoreboard();
        turnScoreboard.InitTurnScoreboard();
        gameBoard.InitGameBoard();
    }
}
