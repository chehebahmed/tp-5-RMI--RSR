import java.rmi.*;

//pour lancer registry tapez "rmiregistry" dans le terminal
public class Server {

   public static void main(String[] args) {
      try {
         StockInterface stock = new Stock();
         Naming.rebind("Stock", stock);
         System.out.println("Serveur prêt.");
      } catch (Exception e) {
         System.out.println("Erreur de démarrage du serveur : " + e.getMessage());
      }
   }
}