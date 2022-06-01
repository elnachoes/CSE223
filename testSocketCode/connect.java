import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerError;

class connect {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        PrintWriter printWriter = null;

        
        try {
            serverSocket = new ServerSocket(1234);
        } catch (Exception e) {
            return;
        }
        
        System.out.println("waiting for connection");
        
        try {
            clientSocket = serverSocket.accept();
        } catch (Exception e) {
            try {
                serverSocket.close();
            } catch (Exception f) {
                return;
            }
            return;
        }
        
        System.out.println("connection received");
        
        try {
            printWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (Exception e) {
            return;
        }
        
        String message = "penis penis penis";

        printWriter.print(message);
        printWriter.flush();
        
        try {
            serverSocket.close();
        } catch (Exception e) {
            return;
        }
    }
}