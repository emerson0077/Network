import java.net.*;

//machine name and ip address
// javac -Xlint:deprecation filename.java
public class  ne3{
    public static void main(String[] args) {
        try {
            InetAddress a = InetAddress.getLocalHost();
            System.out.println("IP Address: " + a.getHostAddress());
            System.out.println("Hostname: " + a.getHostName());
        } 
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
