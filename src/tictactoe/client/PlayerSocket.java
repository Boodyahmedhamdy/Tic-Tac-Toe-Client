package tictactoe.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.requests.Request;
import network.responses.Response;

public final class PlayerSocket {

    private static PlayerSocket instance; // Singleton instance
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private final AtomicBoolean running = new AtomicBoolean(true);

    private PlayerSocket() {
        this.socket = new Socket();
    }

    public static synchronized PlayerSocket getInstance() {
        if (instance == null) {
            instance = new PlayerSocket();
        }
        return instance;
    }

    public boolean connect(InetSocketAddress ip, int timeout) {
        try {
            System.out.println("Connecting to server: " + ip);
            socket.connect(ip, timeout);
            System.out.println("Connected to the server.");

            // Initialize the streams after the socket is connected
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            return true;
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Failed to connect to server: " + ex.getMessage(), ex);
            return false;
        }
    }

    public void sendRequest(Request request) {
        try {
            System.out.println("Sending Request: " + request.getClass().getSimpleName());
            out.writeObject(request);
            out.flush();
            System.out.println("Request sent: " + request.getClass().getSimpleName());
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error sending request: " + ex.getMessage(), ex);
        }
    }
    
    public void sendResponse(Response response) {
        try {
            System.out.println("Sending Response: " + response.getClass().getSimpleName());
            out.writeObject(response);
            out.flush();
            System.out.println("Response sent: " + response.getClass().getSimpleName());
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error sending request: " + ex.getMessage(), ex);
        }
    }

    public Response receiveResponse() {
        try {
            System.out.println("Waiting for response...");
            Response response = (Response) in.readObject();
            System.out.println("Response received: " + response.getClass().getSimpleName());
            return response;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error receiving response: " + ex.getMessage(), ex);
            return null;
        }
    }
    
    public Request receiveRequest() {
        try {
            System.out.println("Waiting for request...");
            Request request = (Request) in.readObject();
            System.out.println("request received: " + request.getClass().getSimpleName());
            return request;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error receiving response: " + ex.getMessage(), ex);
            return null;
        }
    }

    public void close() {
        running.set(false);
        try {
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed() && out != null && in != null;
    }
}