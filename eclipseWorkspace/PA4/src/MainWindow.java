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

public class MainWindow extends JFrame {

    private JPanel contentPane = null;
    private GameBoard gameBoard = null;
    private PlayerScoreboard player1Scoreboard = null;
    private PlayerScoreboard player2Scoreboard = null;
    private JTextField setPlayer1NameInput = null;
    private JTextField setPlayer2NameInput = null;
    private TurnScoreboard turnScoreboard = null;
    private JTextField setBoardSizeInput;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                    frame.NewGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1069, 658);
        contentPane = new JPanel();
        contentPane.setBackground(Color.GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton resetGameButton = new JButton("reset game");
        resetGameButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		NewGame();
        	}
        });
        resetGameButton.setBounds(613, 40, 427, 23);
        contentPane.add(resetGameButton);

        
        player1Scoreboard = new PlayerScoreboard("Player 1");
        player1Scoreboard.setForeground(Color.WHITE);
        player1Scoreboard.setBounds(613, 545, 200, 23);
        contentPane.add(player1Scoreboard);
        
        
        player2Scoreboard = new PlayerScoreboard("Player 2");
        player2Scoreboard.setForeground(Color.WHITE);
        player2Scoreboard.setBounds(613, 579, 200, 23);
        contentPane.add(player2Scoreboard);
        
        
        turnScoreboard = new TurnScoreboard();
        turnScoreboard.setBounds(613, 511, 291, 23);
        contentPane.add(turnScoreboard);


        gameBoard = new GameBoard();
        gameBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameBoard.HandleMouseClick(e.getX(),e.getY());
            }
        });
        gameBoard.setBounds(10, 11, 593, 591);
        contentPane.add(gameBoard);
        
        
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


        JButton setPlayer1NameButton = new JButton("Player 1 Name");
        setPlayer1NameButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		player1Scoreboard.SetPlayerName(setPlayer1NameInput.getText());
                gameBoard.repaint();
        	}
        });
        setPlayer1NameButton.setBounds(866, 104, 174, 23);
        contentPane.add(setPlayer1NameButton);
        
        JButton setPlayer2NameButton = new JButton("Player 2 Name");
        setPlayer2NameButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
                player2Scoreboard.SetPlayerName(setPlayer2NameInput.getText());
                gameBoard.repaint();
        	}
        });
        setPlayer2NameButton.setBounds(866, 135, 174, 23);
        contentPane.add(setPlayer2NameButton);
        
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
                        System.out.println("you have to put in a number for the board size"); 
                        return;
                    }
                    NewGame();
                }
            }
        });
        setBoardSizeInput.setColumns(10);
        setBoardSizeInput.setBounds(613, 74, 243, 20);
        contentPane.add(setBoardSizeInput);

        JButton setBoardSize = new JButton("Set Board Size (max 8)");
        setBoardSize.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
                //TODO : make this a pop up
                try {
                    gameBoard.SetBoardSize(Integer.parseInt(setBoardSizeInput.getText()));
                }
                catch (Exception f) { 
                    System.out.println("you have to put in a number for the board size"); 
                    return;
                }
                NewGame();
        	}
        });
        setBoardSize.setBounds(866, 74, 174, 23);
        contentPane.add(setBoardSize);
    }
    
    //this function will initialize all the components of the game
    private void NewGame() {
        //initialize the components themselves
        player1Scoreboard.InitPlayerScoreboard(turnScoreboard);
        player2Scoreboard.InitPlayerScoreboard(turnScoreboard);
        turnScoreboard.InitTurnScoreboard(player1Scoreboard, player2Scoreboard);
        gameBoard.InitGameBoard(player1Scoreboard, player2Scoreboard, turnScoreboard);
    }
}
