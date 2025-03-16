import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote {
    // Register a client for callbacks.
    void registerClient(ChatClientInterface client) throws RemoteException;
    
    // Broadcast a message to all registered clients.
    void broadcastMessage(String message) throws RemoteException;
}

