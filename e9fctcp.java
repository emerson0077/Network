import java.io.*;
import java.net.*;
import java.util.Scanner;

public class e9fctcp {
    public static void main(String[] args) {
        Socket socket = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket("localhost", 5000);
            dos = new DataOutputStream(socket.getOutputStream());

            System.out.println("Enter the full file path of the file to send:");
            String filePath = scanner.nextLine();

            System.out.println("Enter the destination folder path on the server:");
            String destinationFolder = scanner.nextLine();

            dos.writeUTF(destinationFolder);

            File file = new File(filePath);
            fis = new FileInputStream(file);

            dos.writeUTF(file.getName());

            System.out.println("Sending file: " + file.getName());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully.");

        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            try {
                if (fis != null) fis.close();
                if (dos != null) dos.close();
                if (socket != null) socket.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
