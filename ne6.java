import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
// Local Dns server hostname and ip address and resolve a given hostname
public class ne6 {
    public static void main(String[] args) {
        try
        {
            Scanner sc=new Scanner(System.in);
            InetAddress localhost=InetAddress.getLocalHost();
            System.out.println("Local DNS server's hostname: "+localhost.getHostName());
            System.out.println("Local DNS server's IP Address: "+localhost.getHostAddress());
            System.out.println();

            System.out.print("Enter the hostname to resolve:");
            String reshostname=sc.nextLine();

            InetAddress resadd=InetAddress.getByName(reshostname);
            System.out.println("Resolved Host Name: "+reshostname);
            System.out.println("Resolved Host Address: "+resadd.getHostAddress());
        }
        catch(UnknownHostException e)
        {
            System.err.println("Error retrieving DNS information: "+e.getMessage());
        }
    }
}