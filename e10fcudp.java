import java.io.*;
import java.net.*;
import java.util.Scanner;

public class e10fcudp {

    public static void main(String[] args) {
        DatagramSocket socket = null;
        FileInputStream fis = null;
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 5000;

            System.out.println("Enter the full file path of the file to send:");
            String filePath = scanner.nextLine();

            System.out.println("Enter the destination folder path on the server:");
            String destinationFolder = scanner.nextLine();

            DatagramPacket packet = new DatagramPacket(destinationFolder.getBytes(), destinationFolder.length(), serverAddress, serverPort);
            socket.send(packet);

            File file = new File(filePath);
            fis = new FileInputStream(file);
            byte[] sendBuffer = new byte[4096];

            packet = new DatagramPacket(file.getName().getBytes(), file.getName().length(), serverAddress, serverPort);
            socket.send(packet);

            System.out.println("Sending file: " + file.getName());

            int bytesRead;
            while ((bytesRead = fis.read(sendBuffer)) > 0) {
                packet = new DatagramPacket(sendBuffer, bytesRead, serverAddress, serverPort);
                socket.send(packet);
            }

            packet = new DatagramPacket(new byte[0], 0, serverAddress, serverPort);
            socket.send(packet);

            System.out.println("File sent successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
