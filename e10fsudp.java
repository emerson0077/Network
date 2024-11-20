import java.io.*;
import java.net.*;

public class e10fsudp {

    public static void main(String[] args) {
        DatagramSocket socket = null;
        FileOutputStream fos = null;

        try {
            socket = new DatagramSocket(5000);
            byte[] receiveBuffer = new byte[4096];
            System.out.println("Server is waiting for file...");

            DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(packet);
            String destinationFolder = new String(packet.getData(), 0, packet.getLength()).trim();
            System.out.println("Destination folder received: " + destinationFolder);

            packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(packet);
            String fileName = new String(packet.getData(), 0, packet.getLength());

            File outputFile = new File(destinationFolder, "received_" + fileName);
            fos = new FileOutputStream(outputFile);

            System.out.println("Receiving file: " + fileName);

            while (true) {
                packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(packet);

                if (packet.getLength() == 0) {
                    break;
                }

                fos.write(packet.getData(), 0, packet.getLength());
            }

            System.out.println("File received successfully at: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
