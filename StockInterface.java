import java.rmi.*;

public interface StockInterface extends Remote {

  
   public int getQuantity(String productName) throws RemoteException;

   
   public String[] getProducts() throws RemoteException;

   public void updateQuantity(String productName, int quantity) throws RemoteException;

  
   public void removeQuantity(String productName, int quantity) throws RemoteException;

   

   public void registerClient(ClientInterface client) throws RemoteException;


   public void unregisterClient(ClientInterface client) throws RemoteException;
}
