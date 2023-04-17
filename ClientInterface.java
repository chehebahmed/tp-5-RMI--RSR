import java.rmi.*;

public interface ClientInterface extends Remote {

   
   public void notify(String message) throws RemoteException;
}
