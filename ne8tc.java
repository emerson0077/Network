import java.io.*;
import java.net.*;

public class ne8tc {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server at " + serverAddress + ":" + port);

            String userInput;
            while (true) {
                System.out.print("Enter command (ECHO <message>, PING <address>, TIME, EXIT): ");
                userInput = stdIn.readLine();

                if (userInput.equalsIgnoreCase("EXIT")) {
                    out.println(userInput);
                    break;
                }

                out.println(userInput);

                String response;
                while ((response = in.readLine()) != null) {
                    if (response.equals("END")) {
                        break;
                    }
                    System.out.println("Server response: " + response);
                }
            }

            System.out.println("Disconnected from server.");
        } 
        catch (IOException e) {
            System.out.println("Error communicating with server: " + e.getMessage());
        }
    }
}
