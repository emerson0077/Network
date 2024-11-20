import java.rmi.Naming;
import java.util.Scanner;

public class JewelryBillClient {
    public static void main(String[] args) {
        try {
            JewelryBill jewelryBill = (JewelryBill) Naming.lookup("//localhost/JewelryBillService");
            Scanner scanner = new Scanner(System.in);
            int quantity;
            String itemName;
            String continueInput;

            do {
                System.out.print("Enter item name: ");
                itemName = scanner.nextLine();

                System.out.print("Enter quantity: ");
                quantity = scanner.nextInt();
                scanner.nextLine();

                jewelryBill.addItem(itemName, quantity);

                System.out.print("Do you want to add more items? (yes/no): ");
                continueInput = scanner.nextLine();

            } while (continueInput.equalsIgnoreCase("yes"));

            System.out.print("Enter discount percentage: ");
            double discount = scanner.nextDouble();
            jewelryBill.setDiscount(discount);
            scanner.nextLine();

            double total = jewelryBill.getTotalBill();
            System.out.println("Total Jewelry Bill: Rs " + total);

        } catch (Exception e) {
            System.out.println("Jewelry Bill Client exception: " + e);
        }
    }
}
