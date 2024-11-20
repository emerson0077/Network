import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class JewelryBillServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            JewelryBillImpl jewelryBill = new JewelryBillImpl();
            Naming.rebind("JewelryBillService", jewelryBill);
            System.out.println("Jewelry Bill Server is ready.");
        } catch (Exception e) {
            System.out.println("Jewelry Bill Server failed: " + e);
        }
    }
}
