import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class JewelryBillImpl extends UnicastRemoteObject implements JewelryBill {

    private HashMap<String, double[]> purchasedItems;
    private HashMap<String, Double> wastagePercentages;
    private HashMap<String, Double> defaultPrices; 
    private double discountPercentage;

    protected JewelryBillImpl() throws RemoteException {
        super();
        purchasedItems = new HashMap<>();
        wastagePercentages = new HashMap<>();
        defaultPrices = new HashMap<>(); 

        wastagePercentages.put("Gold Ring", 5.0);
        wastagePercentages.put("Diamond Necklace", 10.0);
        wastagePercentages.put("Silver Bracelet", 3.0);
        wastagePercentages.put("Platinum Earrings", 7.0);

        defaultPrices.put("Gold Ring", 500.0);
        defaultPrices.put("Diamond Necklace", 1500.0);
        defaultPrices.put("Silver Bracelet", 300.0);
        defaultPrices.put("Platinum Earrings", 1200.0);

        discountPercentage = 0;
    }

    @Override
    public void addItem(String itemName, int quantity) throws RemoteException {
        double price = defaultPrices.getOrDefault(itemName, 0.0);
        purchasedItems.put(itemName, new double[]{price, quantity});
        System.out.println("Added " + quantity + " " + itemName + "(s) at Rs " + price + " each.");
    }

    @Override
    public void setDiscount(double discountPercentage) throws RemoteException {
        this.discountPercentage = discountPercentage;
        System.out.println("Discount set to " + discountPercentage + "%.");
    }

    @Override
    public double getTotalBill() throws RemoteException {
        double total = 0;

        for (String item : purchasedItems.keySet()) {
            double[] details = purchasedItems.get(item);
            double price = details[0];
            double quantity = details[1];
            total += price * quantity;
        }

        for (String item : purchasedItems.keySet()) {
            double wastage = wastagePercentages.getOrDefault(item, 0.0);
            total += (total * (wastage / 100));
        }

        total -= total * (discountPercentage / 100);

        return total;
    }
}
