package tictactoe.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.requests.LoginRequest;
import network.requests.RegisterRequest;
import network.requests.Request;
import network.responses.LoginResponse;
import network.responses.RegisterResponse;
import network.responses.Response;

public final class PlayerSocket {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService threadPool = Executors.newFixedThreadPool(2); // for read and write 

    public PlayerSocket() {
        this.socket = new Socket(); // Initialize the socket
    }

    public boolean connect(InetSocketAddress ip, int timeout) {
        try {
            socket.connect(ip, timeout);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to the server.");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

//    public void startCommunication() {
//        // Start threads for reading and writing messages
//        threadPool.submit(() -> {
//            readMessages();
//        });
//
//        threadPool.submit(() -> {
//            writeMessages();
//        });
//    }
    public void sendRequest(Request request) {
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Response receiveResponse() {
        try {
            return (Response) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void writeMessages() {
        // Assume you have a way to send a message when the user clicks the login/register button
        try {
            // Create LoginRequest or RegisterRequest based on user input
            LoginRequest loginRequest = new LoginRequest("username", "password");
            out.writeObject(loginRequest); // Send the object to the server
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readMessages() {
        try {
            while (running.get()) {
                Object response = in.readObject(); // Receive object response from the server
                if (response instanceof LoginResponse) {
                    LoginResponse loginResponse = (LoginResponse) response;
                    System.out.println("Login " + (loginResponse.isSuccess() ? "successful" : "failed") + ": " + loginResponse.getMessage());
                } else if (response instanceof RegisterResponse) {
                    RegisterResponse registerResponse = (RegisterResponse) response;
                    System.out.println("Register " + (registerResponse.isSuccess() ? "successful" : "failed") + ": " + registerResponse.getMessage());
                } else {
                    System.out.println("Unknown response received from the server.");
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println("Disconnected from the server.");
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            threadPool.shutdown();
        }
    }
}



/*public final class PlayerSocket {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Scanner scanner;
    private Socket socket;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService threadPool = Executors.newFixedThreadPool(2); // for read and write 

    public PlayerSocket() {
        this.socket = new Socket(); // Initialize the socket
    }

    public boolean connect(InetSocketAddress ip, int timeout) {

        try {
            socket.connect(ip, timeout);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            this.scanner = new Scanner(System.in);
            System.out.println("Connected to the server.");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void startCommunication() {
        // Start threads for reading and writing messages
        threadPool.submit(() -> {
            readMessages();
        });

        threadPool.submit(() -> {
            writeMessages();
        });
    }

    public void writeMessages() {
        while (running.get()) {
            try {
                // Read input from the user
                String line = scanner.nextLine();
                // Send the message to the server
                out.writeObject(line);
            } catch (IOException ex) {
                Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
        close();
    }

    public void readMessages() {
        try {
            while (running.get()) {
                String message = (String) in.readObject();
                System.out.println("Server: " + message);
                if (message.equals("##")) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
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
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Disconnected from the server.");
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            threadPool.shutdown();
        }
    }

}*/
