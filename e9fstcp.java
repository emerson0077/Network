import java.io.*;
import java.net.*;

public class e9fstcp {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream dis = null;
        FileOutputStream fos = null;

        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server is waiting for file...");

            socket = serverSocket.accept();
            dis = new DataInputStream(socket.getInputStream());

            String destinationFolder = dis.readUTF();
            System.out.println("Destination folder received: " + destinationFolder);

            String fileName = dis.readUTF();
            System.out.println("Receiving file: " + fileName);

            File outputFile = new File(destinationFolder, "received_" + fileName);
            fos = new FileOutputStream(outputFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("File received successfully at: " + outputFile.getAbsolutePath());

        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            try {
                if (fos != null) fos.close();
                if (dis != null) dis.close();
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
