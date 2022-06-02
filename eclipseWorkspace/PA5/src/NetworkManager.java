import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Component;
import java.io.PrintWriter;


//AUTHORS : Corbin Martin, Dakota Schaeffer, Muhamad Al-zughir
//this class allows for network interaction between the two clients
//it will spin up a thread for reading data from each other
public class NetworkManager extends Thread{
    private boolean isServer = false;
    private final int STANDARD_PORT = 1234; //the regular port is 1234
    private ServerSocket serverSide = null;
    private Socket clientSide = null;
    private Scanner readFromClient = null;
    private PrintWriter sendToClient = null;
    private JComponent rootNode = null;
    private GameBoard gameBoard = null;
    private String ipAddress = "127.0.0.1"; //defaults to localhost
    private Dots dotsGameWindow = null;
    private JLabel networkStatusLabel = null;
    private PlayerScoreboard player1Scoreboard = null;
    private PlayerScoreboard player2Scoreboard = null;
    public boolean isConnected = false;

    //this ctor requires knowing if the application is the client or the server and it needs the root node
    public NetworkManager(boolean isServer, JComponent rootNode) {
        super();
        this.rootNode = rootNode;
        this.isServer = isServer;
        this.dotsGameWindow = (Dots)rootNode.getTopLevelAncestor();
        Init();
    }
    
    //this method returns what the value of isserver is
    public boolean isServer() { return isServer; }

    //this method allows you to set the ip address
    public void SetIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    //this method gets all the necessary child components
    public void Init() {
        for (Component component : rootNode.getComponents()) {
            if (component.getName() == null) continue;

            if (component.getName().compareTo("gameBoard") == 0) {
                this.gameBoard = (GameBoard)component;
            }
            if (component.getName().compareTo("player1Scoreboard") == 0) {
                this.player1Scoreboard = (PlayerScoreboard)component;
            }
            if (component.getName().compareTo("player2Scoreboard") == 0) {
                this.player2Scoreboard = (PlayerScoreboard)component;
            }
            if (component.getName().compareTo("networkStatusLabel") == 0) {
                this.networkStatusLabel = (JLabel)component;
            }
        }
    }

    //this method hosts the server and syncs the name board size and the game start
    public void HostServer() {
        //TODO : split this up when things are working
        try {
            serverSide = new ServerSocket(STANDARD_PORT);

            networkStatusLabel.setText("hosting... waiting on connection");
            networkStatusLabel.repaint();

            clientSide = serverSide.accept();
            readFromClient = new Scanner(clientSide.getInputStream());
            sendToClient = new PrintWriter(clientSide.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        networkStatusLabel.setText("hosting... player connected");
        networkStatusLabel.repaint();

        isConnected = true;
        
        SyncBoardSize(gameBoard.GetBoardSize(), true);
        SyncName(player1Scoreboard.GetPlayerName(), true);
        SyncStartGame();
        
        System.out.println("someone connected");
    }
    
    //this method connects to the server and synchronizes the name
    public boolean ConnectToServer() {
        if (ipAddress == null) {
            System.out.println("error : ip address was null");
            return false;
        }
        //TODO : split this up when things are working
        try {
            networkStatusLabel.setText("connecting... trying to connect");
            networkStatusLabel.repaint();
            
            clientSide = new Socket(ipAddress, STANDARD_PORT);
            readFromClient = new Scanner(clientSide.getInputStream());
            sendToClient = new PrintWriter(clientSide.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
            networkStatusLabel.setText("failed connection...");
            networkStatusLabel.repaint();

            return false;
        }
        
        networkStatusLabel.setText("connecting... connected to server");
        networkStatusLabel.repaint();

        isConnected = true;

        SyncName(player2Scoreboard.GetPlayerName(), true);
        
        System.out.println("connected successfully");
        return true;
    }

    //this method syncs the game on all network members
    public void SyncStartGame() {
        if (!isConnected) return;
        
        dotsGameWindow.NewGame();
        dotsGameWindow.SetGameComponentsVisible();
        if (isServer) {
            sendToClient.println("newgame");
            sendToClient.flush();
        }
    }
    
    //this method syncs the input on all network members
    public void SyncClickInput(int x, int y) {
        if (!isConnected) return;
        sendToClient.println("click " + x + " " + y);
        sendToClient.flush();
    }
    
    
    //this method syncs the names on all network members
    public void SyncName(String name, boolean sync) {
        // boolean wasCalledRemotely = (name.split(" ")[0].compareTo("name") == 0);
        
        if (sync && isConnected) {
            if (isServer) {
                player1Scoreboard.SetPlayerName(name);
                if (isConnected) {
                    sendToClient.println("name " + name);
                    sendToClient.flush();
                }
            }
            else {
                player2Scoreboard.SetPlayerName(name);
                if (isConnected) {
                    sendToClient.println("name " + name);
                    sendToClient.flush();
                }
            }
        }
        else if (!sync && isConnected) {
            if (isServer) player2Scoreboard.SetPlayerName(name);
            else player1Scoreboard.SetPlayerName(name);
        }
        else {
            if (isServer) player1Scoreboard.SetPlayerName(name);
            else player2Scoreboard.SetPlayerName(name);
        }
    }
    
    //this method syncs the board size on all network members
    public void SyncBoardSize(int size, boolean sync) {
        gameBoard.SetBoardSize(size);

        if (sync && isConnected) {
            sendToClient.println("boardsize " + size);
            sendToClient.flush();
            dotsGameWindow.NewGame();
        }
        else if (!sync && isConnected) {
            gameBoard.SetBoardSize(size);
            dotsGameWindow.NewGame();
        }
        else gameBoard.SetBoardSize(size);
    }

    //this method is the thread that will continuously wait for input from the other connection
    @Override
    public void run() {
        if (isServer) HostServer();
        if (readFromClient == null || clientSide == null || !isConnected) return;

        //this loop will wait and analyze input on the socket and call the methods accordingly
        while (readFromClient.hasNextLine()) {
            String nextLine = readFromClient.nextLine();
            
            String[] messages = nextLine.split(" ");

            if (messages[0].compareTo("newgame") == 0) {
                SyncStartGame();
            }

            if (messages[0].compareTo("name") == 0) {
                String fullname = nextLine.substring(5, nextLine.length());
                SyncName(fullname, false);
            } 

            if (messages[0].compareTo("click") == 0) {
                gameBoard.HandleMouseClick(Integer.parseInt(messages[1]), Integer.parseInt(messages[2]));
            }

            if (messages[0].compareTo("boardsize") == 0) {
                SyncBoardSize(Integer.parseInt(messages[1]), false);
            }
        }

        System.exit(0);
    }
}
