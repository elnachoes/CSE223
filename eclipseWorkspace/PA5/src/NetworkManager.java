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

    public NetworkManager(boolean isServer, JComponent rootNode) {
        super();
        this.rootNode = rootNode;
        this.isServer = isServer;
        this.dotsGameWindow = (Dots)rootNode.getTopLevelAncestor();
        Init();
    }
    
    public void SetIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public void Init() {
        //TODO : INITIALIZE REFERENCES TO CHILD NODES
        for (Component component : rootNode.getComponents()) {
            if (component.getName() == null) continue;

            if (component.getName().compareTo("gameBoard") == 0) {
                this.gameBoard = (GameBoard)component;
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
        
        SyncStartGame();
        
        isConnected = true;
        
        System.out.println("someone connected");
    }
    
    public void ConnectToServer() {
        if (ipAddress == null) {
            System.out.println("error : ip address was null");
            return;
        }
        //TODO : split this up when things are working
        try {
            clientSide = new Socket(ipAddress, STANDARD_PORT);
            readFromClient = new Scanner(clientSide.getInputStream());
            sendToClient = new PrintWriter(clientSide.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        isConnected = true;
        
        System.out.println("connected successfully");
    }

    public void SendMessage(String message) {
        sendToClient.println(message);
        sendToClient.flush();
    }

    public void SyncStartGame() {
        dotsGameWindow.NewGame();
        dotsGameWindow.SetGameComponentsVisible();
        if (isServer) {
            sendToClient.println("NewGame");
            sendToClient.flush();
        }
    }


    public void SyncClickInput(int x, int y) {

    }

    public void SyncQuitGame() {

    }

    public void SyncName(String name) {

    }

    public void SyncBoardSize(int size) {

    }

    //this method is the thread that will continuously wait for input from the other connection
    @Override
    public void run() {
        if (isServer) HostServer();
        else ConnectToServer();

        if (readFromClient == null || clientSide == null) return;

        while (readFromClient.hasNextLine()) {
            String nextLine = readFromClient.nextLine();
            
            String[] messages = nextLine.split(" ");

            if (messages[0].compareTo("NewGame") == 0) {
                SyncStartGame();
            }

            //TODO : PROCESS NETWORK MESSAGES HERE
        }
    }
}
