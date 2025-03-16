import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientInterface extends Remote {
    // Callback method for receiving messages.
    void receiveMessage(String message) throws RemoteException;
}
