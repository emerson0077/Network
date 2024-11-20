import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ne7ts {
    public static void main(String[] args) {
        int port = 8088;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Time Server is running on port " + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    out.println("Current Time: " + currentTime);
                    System.out.println("Sent current time to client: " + currentTime);
                } 
                catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } 
        catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }
}
