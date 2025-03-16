//javac serverfilename
//start rmiregistry
//java serverfilename
//

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {

    // List to hold registered clients.
    private List<ChatClientInterface> clients;

    protected ChatServer() throws RemoteException {
        clients = new ArrayList<>();
    }

    // Register a new client.
    public synchronized void registerClient(ChatClientInterface client) throws RemoteException {
        clients.add(client);
        System.out.println("New client registered. Total clients: " + clients.size());
    }

    // Broadcast message to all registered clients.
    public synchronized void broadcastMessage(String message) throws RemoteException {
        System.out.println("Broadcasting message: " + message);
        for (ChatClientInterface client : new ArrayList<>(clients)) {
            try {
                client.receiveMessage(message);
            } catch (RemoteException e) {
                System.out.println("Error sending message to a client. Removing client.");
                clients.remove(client);
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Start RMI registry programmatically on port 1099.
            try {
                LocateRegistry.createRegistry(1099);
                System.out.println("RMI registry started on port 1099.");
            } catch (RemoteException e) {
                System.out.println("RMI registry already running on port 1099.");
            }

            // Bind the server instance to the registry.
            ChatServer server = new ChatServer();
            // Use "localhost" or replace with the server's actual IP if needed.
            java.rmi.Naming.rebind("rmi://localhost/ChatServer", server);
            System.out.println("ChatServer is ready and bound to the registry.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
