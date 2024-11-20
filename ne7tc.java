import java.io.*;
import java.net.*;

public class ne7tc {
    public static void main(String[] args) {
        String host = "localhost"; 
        int port = 8088; 
        
        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String serverTime = in.readLine();
            System.out.println("Received from server: " + serverTime);
        } 
        catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}
