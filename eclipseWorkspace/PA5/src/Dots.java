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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
    private JPanel contentPane = null;
    private JButton okButton = null;
    private final ButtonGroup connectionButtons = new ButtonGroup();
    private JTextField ipAddressInput = null;
    private JTextField playerNameInput = null;
    private JTextField player2NameInput;
    private JTextField boardSizeInput = null;
    private JTextField player1NameInput;
    
    public NetworkManager networkManager = null;
    
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
        notificationBoard.setBounds(613, 410, 427, 90);
        notificationBoard.setVisible(false);
        contentPane.add(notificationBoard);
        
        //set up the ok button
        //the ok button will only show itself when there is an pop up notification
        okButton = new JButton("OK");
        okButton.setName("okButton");
        okButton.setBounds(954, 511, 89, 23);
        contentPane.add(okButton);
        okButton.setVisible(false);

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
        gameBoard.setBounds(10, 11, 593, 591);
        gameBoard.setBackground(Color.BLACK);
        contentPane.add(gameBoard);
        
        JRadioButton serverRadioButton = new JRadioButton("server");
        connectionButtons.add(serverRadioButton);
        serverRadioButton.setBounds(660, 11, 186, 23);
        contentPane.add(serverRadioButton);
        
        JRadioButton clientRadioButton = new JRadioButton("client");
        connectionButtons.add(clientRadioButton);
        clientRadioButton.setBounds(854, 11, 186, 23);
        contentPane.add(clientRadioButton);
        
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(660, 211, 380, 23);
        contentPane.add(quitButton);
        
        ipAddressInput = new JTextField();
        ipAddressInput.setEnabled(false);
        ipAddressInput.setBounds(854, 177, 186, 23);
        contentPane.add(ipAddressInput);
        ipAddressInput.setColumns(10);
        
        JButton ipAddressButton = new JButton("Enter Ip Address");
        ipAddressButton.setEnabled(false);
        ipAddressButton.setBounds(854, 143, 186, 23);
        contentPane.add(ipAddressButton);
        
        player2NameInput = new JTextField();
        player2NameInput.setEnabled(false);
        player2NameInput.setColumns(10);
        player2NameInput.setBounds(854, 109, 186, 23);
        contentPane.add(player2NameInput);
        
        JButton player2NameButton = new JButton("Enter Player 2 Name");
        player2NameButton.setEnabled(false);
        player2NameButton.setBounds(854, 75, 186, 23);
        contentPane.add(player2NameButton);
        
        JButton boardSizeButton = new JButton("Enter Board Size (max 12)");
        boardSizeButton.setEnabled(false);
        boardSizeButton.setBounds(660, 143, 186, 23);
        contentPane.add(boardSizeButton);
        
        boardSizeInput = new JTextField();
        boardSizeInput.setEnabled(false);
        boardSizeInput.setColumns(10);
        boardSizeInput.setBounds(660, 177, 186, 23);
        contentPane.add(boardSizeInput);
        
        JButton startButton = new JButton("Start");
        startButton.setEnabled(false);
        startButton.setBounds(660, 41, 186, 23);
        contentPane.add(startButton);
        
        JButton connectButton = new JButton("Connect");
        connectButton.setEnabled(false);
        connectButton.setBounds(854, 41, 186, 23);
        contentPane.add(connectButton);
        
        JButton player1NameButton = new JButton("Enter Player 1 Name");
        player1NameButton.setEnabled(false);
        player1NameButton.setBounds(660, 75, 186, 23);
        contentPane.add(player1NameButton);
        
        player1NameInput = new JTextField();
        player1NameInput.setEnabled(false);
        player1NameInput.setColumns(10);
        player1NameInput.setBounds(660, 109, 186, 23);
        contentPane.add(player1NameInput);
        gameBoard.setVisible(false);

        
        
        startButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
                // networkManager.HostServer();
                if (!networkManager.isConnected) networkManager.start();
                else networkManager.SyncStartGame();
        	}
        });
        
        connectButton.addMouseListener(new MouseAdapter() {
            @Override
        	public void mousePressed(MouseEvent e) {
                networkManager.ConnectToServer();
                if (networkManager.isConnected) networkManager.start();
        	}
        });
        
        //when the server button is clicked
        serverRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                // set the menu buttons visible for a server
                clientRadioButton.setEnabled(false);
                boardSizeButton.setEnabled(true);
                boardSizeInput.setEnabled(true);
                player1NameButton.setEnabled(true);
                player1NameInput.setEnabled(true);
                startButton.setEnabled(true);
                networkManager = new NetworkManager(true, contentPane);
            }
        });
        
        //when the client button is c licked
        clientRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                //set the menu buttons visible for a client
                serverRadioButton.setEnabled(false);
                ipAddressButton.setEnabled(true);
                ipAddressInput.setEnabled(true);
                player2NameButton.setEnabled(true);
                player2NameInput.setEnabled(true);
                connectButton.setEnabled(true);
                networkManager = new NetworkManager(false, contentPane);
            }
        });
        
        
        ipAddressButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                networkManager.SetIpAddress(ipAddressInput.getText());
            }
        });
        
        ipAddressInput.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    networkManager.SetIpAddress(ipAddressInput.getText());
                }
        	}
        });
        
        boardSizeInput.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    if (networkManager.isConnected) networkManager.SyncBoardSize(Integer.parseInt(boardSizeInput.getText()), true);
                    else networkManager.SyncBoardSize(Integer.parseInt(boardSizeInput.getText()), false);
                }
        	}
        });

        boardSizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (networkManager.isConnected) networkManager.SyncBoardSize(Integer.parseInt(boardSizeInput.getText()), true);
                else networkManager.SyncBoardSize(Integer.parseInt(boardSizeInput.getText()), false);
            }
        });
        
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gameBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {


                if (!networkManager.isServer()) {
                    if (!turnScoreboard.GetPlayerTurn()) {
                        networkManager.SyncClickInput(e.getX(), e.getY());
                    }
                }
                else {
                    if (turnScoreboard.GetPlayerTurn()) {
                        gameBoard.HandleMouseClick(e.getX(),e.getY());
                    }
                }
            }
        });


        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //once the ok button is clicked hide the notification board and the ok button
                notificationBoard.message = null;
                notificationBoard.setVisible(false);
                okButton.setVisible(false);
            }
        });

        player1NameButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
                // player1Scoreboard.SetPlayerName(player1NameInput.getText());


                // //TODO MAKE SURE THIS SYNCS
                // // if (networkManager.isConnected)

                // gameBoard.repaint();
                if (networkManager.isConnected) networkManager.SyncName(player1NameInput.getText(), true);
                else networkManager.SyncName(player1NameInput.getText(), false);
        	}
        });

        player1NameInput.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    // player1Scoreboard.SetPlayerName(player1NameInput.getText());


                    // //TODO MAKE SURE THIS SYNCS
                    // // if (networkManager.isConnected)
    
                    // gameBoard.repaint();
                    if (networkManager.isConnected) networkManager.SyncName(player1NameInput.getText(), true);
                    else networkManager.SyncName(player1NameInput.getText(), false);
                }
        	}
        });

        player2NameButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
                // player2Scoreboard.SetPlayerName(player2NameInput.getText());


                // //TODO MAKE SURE THIS SYNCS
                // // if (networkManager.isConnected)

                // gameBoard.repaint();
                if (networkManager.isConnected) networkManager.SyncName(player2NameInput.getText(), true);
                else networkManager.SyncName(player2NameInput.getText(), false);
        	}
        });

        player2NameInput.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    // player2Scoreboard.SetPlayerName(player2NameInput.getText());


                    // //TODO MAKE SURE THIS SYNCS
                    // // if (networkManager.isConnected)
    
                    // gameBoard.repaint();
                    if (networkManager.isConnected) networkManager.SyncName(player2NameInput.getText(), true);
                    else networkManager.SyncName(player2NameInput.getText(), false);
                }
        	}
        });
        //start a new game
        NewGame();
    }
    
    //this function will initialize all the components of the game
    public void NewGame() {
        //initialize the components themselves
        player1Scoreboard.InitPlayerScoreboard();
        player2Scoreboard.InitPlayerScoreboard();
        turnScoreboard.InitTurnScoreboard();
        gameBoard.InitGameBoard();
    }

    //sets the components of the game visible
    public void SetGameComponentsVisible() {
        turnScoreboard.setVisible(true);
        player1Scoreboard.setVisible(true);
        player2Scoreboard.setVisible(true);
        gameBoard.setVisible(true);
    }
}
