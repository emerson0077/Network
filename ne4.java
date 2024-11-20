import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// next hop router's ip address and mac address
public class ne4 {
    public static void main(String[] args) {
        try {
            String gatewayIP = getDefaultGateway();
            if (gatewayIP != null) {
                System.out.println("Next Hop Router IP Address: " + gatewayIP);

                String macAddress = getMacAddress(gatewayIP);
                if (macAddress != null) {
                    System.out.println("Next Hop Router MAC Address: " + macAddress);
                } else {
                    System.out.println("Could not find MAC address for IP: " + gatewayIP);
                }
            } else {
                System.out.println("Could not find next hop router IP address.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDefaultGateway() {
        try {
            ProcessBuilder builder = new ProcessBuilder("ipconfig");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            Pattern pattern = Pattern.compile("Default Gateway[ .]*: ([0-9.]+)");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group(1); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMacAddress(String ip) {
        try {
            ProcessBuilder builder = new ProcessBuilder("arp", "-a", ip);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            Pattern pattern = Pattern.compile(".*" + ip.replace(".", "\\.") + ".*\\s(([0-9A-Fa-f]{1,2}[:-]){5}[0-9A-Fa-f]{1,2}).*");
            Matcher matcher = pattern.matcher(output.toString());

            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
