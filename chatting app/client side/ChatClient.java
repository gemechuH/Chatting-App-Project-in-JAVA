import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {
    private String name;
    private ChatServerInterface server;

    // Constructor for GUI integration:
    // Connects to the RMI server using the provided server IP and registers the client.
    public ChatClient(String username, String serverIP) throws Exception {
        super();
        this.name = username;
        // Look up the server remote object using the provided IP.
        server = (ChatServerInterface) Naming.lookup("rmi://" + serverIP + "/ChatServer");
        // Register this client with the server.
        server.registerClient(this);
    }

    // Method to send messages to the server
    public void sendMessage(String message) {
        try {
            server.broadcastMessage(name + ": " + message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // This method is called remotely by the server to deliver messages.
    @Override
    public void receiveMessage(String message) throws RemoteException {
        // Update the GUI using a static method from ChatClientGUI.
        boolean isSender = message.startsWith(name + ":");
        ChatClientGUI.appendMessage(message, isSender);
    }
}