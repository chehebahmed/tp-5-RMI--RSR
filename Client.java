import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterface {

   public Client() throws RemoteException {
      super();
   }
    
   
   public void notify(String message) throws RemoteException {
      System.out.println("Notification : " + message);
   }
    
   
   public void connectToServer() {
      try {
         StockInterface stock = (StockInterface) Naming.lookup("//localhost/Stock");
         stock.registerClient(this);
         System.out.println("Connected to the server.");
      } catch(Exception e) {
         System.out.println("Error connecting to the server : " + e.getMessage());
      }
   }
    
  
   public void disconnectFromServer() {
      try {
         StockInterface stock = (StockInterface) Naming.lookup("//localhost/Stock");
         stock.unregisterClient(this);
         System.out.println("Disconnected from the server.");
      }catch(Exception e) {
         System.out.println("Error disconnecting from the server : " + e.getMessage());
      }
   }

 
   public void displayStock(StockInterface stock) {
      try {
         String[] products = stock.getProducts();
         System.out.println("Available products:");
         for (String product : products) {
            int quantity = stock.getQuantity(product);
            System.out.println(product + " : " + quantity);
         }
      } catch (Exception e) {
         System.out.println("Error getting the stock information: " + e.getMessage());
      }
   }

  
   public void updateStock(StockInterface stock) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter the name of the product:");
      String product = scanner.nextLine();
      System.out.println("Enter the quantity to add:");
      int quantity = scanner.nextInt();
      try {
         stock.updateQuantity(product, quantity);
         System.out.println("Stock added successfully.");
      } catch (Exception e) {
         System.out.println("Error updating the stock: " + e.getMessage());
      }
   }

   public void removeStock(StockInterface stock) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter the name of the product:");
      String product = scanner.nextLine();
      System.out.println("Enter the quantity to remove:");
      int quantity = scanner.nextInt();
      try {
         stock.removeQuantity(product, quantity);
         System.out.println("Stock updated successfully.");
      } catch (Exception e) {
         System.out.println("Error updating the stock: " + e.getMessage());
      }
   }
   

  
   public void handleInput(StockInterface stock) {
      Scanner scanner = new Scanner(System.in);
      boolean exit = false;
      while (!exit) {
         System.out.println("Select an option:");
         System.out.println("1. Display stock");
         System.out.println("2. Update Stock - Add Quantity");
         System.out.println("3. Update Stock - Remove Quantity");
         System.out.println("4. Exit");
         int option = scanner.nextInt();
         switch (option) {
            case 1:
               displayStock(stock);
               break;
            case 2:
               updateStock(stock);
               break;
               case 3:
                removeStock(stock);
                break;
            case 4:
               exit = true;
               break;
            default:
               System.out.println("Invalid option. Please try again.");
         }
      }
   }

   public static void main(String[] args) {
      Client client = null;
      try {
         client = new Client();
         client.connectToServer();
         StockInterface stock = (StockInterface) Naming.lookup("//localhost/Stock");
         client.handleInput(stock);
      } catch (Exception e) {
         System.out.println("Error: " + e.getMessage());
      } finally {
         if (client != null) {
            client.disconnectFromServer();
         }
      }
   }
}
