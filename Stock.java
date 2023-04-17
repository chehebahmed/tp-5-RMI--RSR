import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Stock extends UnicastRemoteObject implements StockInterface {

   private HashMap<String, Integer> stock;
   private ArrayList<ClientInterface> clients;

   public Stock() throws RemoteException {
      super();
      stock = new HashMap<String, Integer>();
      clients = new ArrayList<ClientInterface>();
      stock.put("A", 20);
      stock.put("B", 15);
      stock.put("C", 30);
   }

   
   public int getQuantity(String productName) throws RemoteException {
      return stock.get(productName);
   }

   public String[] getProducts() throws RemoteException {
      Set<String> keys = stock.keySet();
      return keys.toArray(new String[keys.size()]);
   }


   public void updateQuantity(String productName, int quantity) throws RemoteException {
      if (!stock.containsKey(productName)) {
         notifyClients("Le produit " + productName + " n'existe pas dans le stock.");
         return;
     }
      Integer currentQuantity = stock.get(productName);
      if (currentQuantity == null) {
         currentQuantity = 0;
      }
      currentQuantity += quantity;
      stock.put(productName, currentQuantity);
      notifyClients(productName + " a été modifié :" + quantity + " ont été ajouter du stock. nouvelle quantité = "
            + currentQuantity);
   }


   public void removeQuantity(String productName, int quantity) throws RemoteException {
      if (!stock.containsKey(productName)) {
         notifyClients("Le produit " + productName + " n'existe pas dans le stock.");
         return;
     }
      Integer currentQuantity = stock.get(productName);
      if (currentQuantity == null) {
         currentQuantity = 0;
      }
      currentQuantity -= quantity;
      stock.put(productName, currentQuantity);
      notifyClients(productName + " a été modifié :" + quantity + " ont été retirés du stock. nouvelle quantité = "
      + currentQuantity);
   }
   

   public void registerClient(ClientInterface client) throws RemoteException {
      clients.add(client);
   }

   public void unregisterClient(ClientInterface client) throws RemoteException {
      clients.remove(client);
   }

   
   private void notifyClients(String message) {
      Iterator<ClientInterface> iterator = clients.iterator();
      while (iterator.hasNext()) {
         ClientInterface client = iterator.next();
         try {
            client.notify(message);
         } catch (RemoteException e) {
          
            iterator.remove();
         }
      }
   }
}
