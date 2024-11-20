import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JewelryBill extends Remote {
    void addItem(String itemName, int quantity) throws RemoteException; 
    void setDiscount(double discountPercentage) throws RemoteException;
    double getTotalBill() throws RemoteException;
}
