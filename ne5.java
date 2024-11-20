import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
// Scanning the port
public class ne5{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int startPort, endPort;

        while (true) {
            System.out.print("Enter the start port: ");
            startPort = scanner.nextInt();

            System.out.print("Enter the end port: ");
            endPort = scanner.nextInt();

            if (startPort < 1 || endPort > 65535 || startPort > endPort) {
                System.out.println("Invalid port range. Please enter valid start and end ports between 1 and 65535.");
            } 
            else {
                break;
            }
        }

        for (int port = startPort; port <= endPort; port++) {
            try (Socket socket = new Socket("localhost", port)) {
                System.out.println("Port " + port + " is in use.");
            } 
            catch (IOException e) {
                // Port is not in use or cannot be accessed
            }
        }
        System.out.println("Scanning the Porting Completed");
    }
}
