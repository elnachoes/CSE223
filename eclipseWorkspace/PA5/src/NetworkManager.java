import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.naming.InitialContext;
import javax.swing.JComponent;
import java.awt.Component;
import java.io.PrintWriter;

public class NetworkManager extends Thread{
    private boolean isServer = false;
    private final int STANDARD_PORT = 1234;
    private ServerSocket serverSide = null;
    private Socket clientSide = null;
    private Scanner readFromClient = null;
    private PrintWriter sendToClient = null;
    private JComponent rootNode = null;
    private GameBoard gameBoard = null;
    private String ipAddress = null;
    private Dots dotsGameWindow = null;
    public boolean isConnected = false;

    private PlayerScoreboard player1Scoreboard = null;
    private PlayerScoreboard player2Scoreboard = null;


    public NetworkManager(boolean isServer, JComponent rootNode) {
        super();
        this.rootNode = rootNode;
        this.isServer = isServer;
        this.dotsGameWindow = (Dots)rootNode.getTopLevelAncestor();
        Init();
    }
    
    public boolean isServer() { return isServer; }

    public void SetIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public void Init() {
        //TODO : INITIALIZE REFERENCES TO CHILD NODES
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
        }
    }

    public void HostServer() {
        //TODO : split this up when things are working
        try {
            serverSide = new ServerSocket(STANDARD_PORT);
            clientSide = serverSide.accept();
            readFromClient = new Scanner(clientSide.getInputStream());
            sendToClient = new PrintWriter(clientSide.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        isConnected = true;
        
        SyncBoardSize(gameBoard.GetBoardSize());
        SyncName(player1Scoreboard.GetPlayerName());
        SyncStartGame();
        
        System.out.println("someone connected");
    }
    
    public boolean ConnectToServer() {
        if (ipAddress == null) {
            System.out.println("error : ip address was null");
            return false;
        }
        //TODO : split this up when things are working
        try {
            clientSide = new Socket(ipAddress, STANDARD_PORT);
            readFromClient = new Scanner(clientSide.getInputStream());
            sendToClient = new PrintWriter(clientSide.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        isConnected = true;

        SyncName(player2Scoreboard.GetPlayerName());
        
        System.out.println("connected successfully");
        return true;
    }

    public void SyncStartGame() {
        if (!isConnected) return;
        
        dotsGameWindow.NewGame();
        dotsGameWindow.SetGameComponentsVisible();
        if (isServer) {
            sendToClient.println("newgame");
            sendToClient.flush();
        }
    }
    
    
    public void SyncClickInput(int x, int y) {
        if (!isConnected) return;
        sendToClient.println("click " + x + " " + y);
        sendToClient.flush();
    }
    
    public void SyncQuitGame() {
        
        if (isConnected) {
            
        }

        System.exit(0);
    }
    

    //FIX THIS ITS CUTTING NAMES OFF IF THEY HAVE SPACES
    public void SyncName(String name) {
        boolean wasCalledRemotely = (name.split(" ")[0].compareTo("name") == 0);

        if (!wasCalledRemotely) {
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
        else {
            if (isServer) {
                player2Scoreboard.SetPlayerName(name.split(" ")[1]);
            }
            else {
                player1Scoreboard.SetPlayerName(name.split(" ")[1]);
            }
        }
    }
    
    public void SyncBoardSize(int size) {
        gameBoard.SetBoardSize(size);

        if (isConnected) {
            sendToClient.println("boardsize " + size);
            sendToClient.flush();
            dotsGameWindow.NewGame();
        }

    }

    //this method is the thread that will continuously wait for input from the other connection
    @Override
    public void run() {
        if (isServer) HostServer();
        // else ConnectToServer();

        if (readFromClient == null || clientSide == null || !isConnected) return;

        while (readFromClient.hasNextLine()) {
            String nextLine = readFromClient.nextLine();
            
            String[] messages = nextLine.split(" ");

            if (messages[0].compareTo("newgame") == 0) {
                SyncStartGame();
            }

            if (messages[0].compareTo("name") == 0) {
                SyncName(nextLine);
            } 

            if (messages[0].compareTo("click") == 0) {
                gameBoard.HandleMouseClick(Integer.parseInt(messages[1]), Integer.parseInt(messages[2]));
            }

            if (messages[0].compareTo("boardsize") == 0) {
                gameBoard.SetBoardSize(Integer.parseInt(messages[1]));
                dotsGameWindow.NewGame();
            }
        }

        System.exit(0);
    }
}
