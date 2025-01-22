//package tictactoe.client;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import network.requests.LoginRequest;
//import network.requests.RegisterRequest;
//import network.requests.Request;
//import network.responses.LoginResponse;
//import network.responses.RegisterResponse;
//import network.responses.Response;
//
//public final class PlayerSocket {
//
//    private static PlayerSocket instance; // Singleton instance
//    private ObjectOutputStream out;
//    private ObjectInputStream in;
//    private Socket socket;
//    private final AtomicBoolean running = new AtomicBoolean(true);
//    private final ExecutorService threadPool = Executors.newFixedThreadPool(2);
//
//    private PlayerSocket() {
//        // Initialize the socket but do not connect it yet
//        this.socket = new Socket();
//    }
//
//    public static synchronized PlayerSocket getInstance() {
//        if (instance == null) {
//            instance = new PlayerSocket();
//        }
//        return instance;
//    }
//
//    public boolean connect(InetSocketAddress ip, int timeout) {
//        try {
//            System.out.println("Connecting to server: " + ip);
//            socket.connect(ip, timeout);
//            System.out.println("Connected to the server.");
//
//            // Initialize the streams after the socket is connected
//            out = new ObjectOutputStream(socket.getOutputStream());
//            out.flush();
//            in = new ObjectInputStream(socket.getInputStream());
//
//            return true;
//        } catch (IOException ex) {
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Failed to connect to server: " + ex.getMessage(), ex);
//            return false;
//        }
//    }
//
//    public void startCommunication() {
//        threadPool.submit(this::readMessages);
//    }
//
//    public void sendRequest(Request request) {
//        try {
//            out.writeObject(request);
//            out.flush();
//            System.out.println("Request sent: " + request.getClass().getSimpleName());
//        } catch (IOException ex) {
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error sending request: " + ex.getMessage(), ex);
//        }
//    }
//
//    public Response receiveResponse() {
//        try {
//            System.out.println("*********************CLIENT START RECIEVE***************");
//            Response response = (Response) in.readObject();
//            System.out.println("Response received: " + response.getClass().getSimpleName());
//            return response;
//        } catch (IOException | ClassNotFoundException ex) {
//            System.out.println("EXCEP AT ******************RECIEVE RESPONSE **************");
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error receiving response: " + ex.getMessage(), ex);
//            return null;
//        }
//    }
//
//    public void readMessages() {
//        try {
//            while (running.get()) {
//                Object response = in.readObject();
//                if (response instanceof LoginResponse) {
//                    LoginResponse loginResponse = (LoginResponse) response;
//                    System.out.println("Login " + (loginResponse.isSuccess() ? "successful" : "failed") + ": " + loginResponse.getMessage());
//                } else if (response instanceof RegisterResponse) {
//                    RegisterResponse registerResponse = (RegisterResponse) response;
//                    System.out.println("Register " + (registerResponse.isSuccess() ? "successful" : "failed") + ": " + registerResponse.getMessage());
//                } else {
//                    System.out.println("Unknown response received from the server.");
//                }
//            }
//        } catch (IOException | ClassNotFoundException ex) {
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error reading messages: " + ex.getMessage(), ex);
//        } finally {
//            close();
//        }
//    }
//
//    public void close() {
//        running.set(false);
//        try {
//            if (socket != null) {
//                socket.close();
//            }
//            if (out != null) {
//                out.close();
//            }
//            if (in != null) {
//                in.close();
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
//        } finally {
//            threadPool.shutdown();
//            try {
//                if (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
//                    threadPool.shutdownNow();
//                }
//            } catch (InterruptedException ex) {
//                threadPool.shutdownNow();
//                Thread.currentThread().interrupt(); // Restore the interrupt status
//            }
//        }
//    }
//
//    public boolean isConnected() {
//        return socket != null && socket.isConnected() && !socket.isClosed() && out != null && in != null;
//    }
//}

package tictactoe.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.requests.Request;
import network.responses.Response;

public final class PlayerSocket {

    private static PlayerSocket instance;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService threadPool = Executors.newFixedThreadPool(2);

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

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            return true;
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Failed to connect to server: " + ex.getMessage(), ex);
            return false;
        }
    }

    public void startCommunication() {
        threadPool.submit(this::readMessages);
    }

    public void sendRequest(Request request) {
        try {
            out.writeObject(request);
            out.flush();
            System.out.println("Request sent: " + request.getClass().getSimpleName());
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error sending request: " + ex.getMessage(), ex);
        }
    }

    public Response receiveResponse() {
        try {
            System.out.println("*********************CLIENT START RECEIVE***************");
            Response response = (Response) in.readObject();
            System.out.println("Response received: " + response.getClass().getSimpleName());
            return response;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("EXCEPTION AT ******************RECEIVE RESPONSE **************");
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error receiving response: " + ex.getMessage(), ex);
            return null;
        }
    }

    public void readMessages() {
        try {
            while (running.get()) {
                Object response = in.readObject();
                if (response instanceof Response) {
                    Response res = (Response) response;
//                    System.out.println("Response received: " + res.getMessage());
                } else {
                    System.out.println("Unknown response received from the server.");
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error reading messages: " + ex.getMessage(), ex);
        } finally {
            close();
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
        } finally {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException ex) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed() && out != null && in != null;
    }
}