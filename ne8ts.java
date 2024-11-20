import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ne8ts {
    public static void main(String[] args) {
        int port = 12345; 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] commandParts = inputLine.split(" ");
                String command = commandParts[0].toUpperCase();

                switch (command) {
                    case "ECHO":
                        out.println("Echo: " + inputLine.substring(5)); 
                        out.println("END");
                        break;

                    case "PING":
                        if (commandParts.length > 1) {
                            String destination = commandParts[1];
                            executePingCommand(destination, out);
                        } else {
                            out.println("PING command requires an IP address or domain name.");
                        }
                        out.println("END"); // End signal for PING
                        break;

                    case "TIME":
                        LocalDateTime currentTime = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        out.println("Current time: " + currentTime.format(formatter));
                        out.println("END"); // End signal for TIME
                        break;

                    case "EXIT":
                        out.println("Goodbye!");
                        out.println("END");
                        return;

                    default:
                        out.println("Unknown command: " + inputLine);
                        out.println("END");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    private void executePingCommand(String destination, PrintWriter out) {
        try {
            ProcessBuilder builder = new ProcessBuilder("ping", destination);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);  
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            out.println("Error executing ping command: " + e.getMessage());
        }
    }
}
