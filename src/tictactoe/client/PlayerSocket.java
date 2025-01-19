package tictactoe.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PlayerSocket {

    private static PlayerSocket playerSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Scanner scanner;
    private Socket socket;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService threadPool = Executors.newFixedThreadPool(2); // for read and write 

    private PlayerSocket() {
        this.socket = new Socket();
    }

    public static PlayerSocket getInstance() {
        if (playerSocket == null) {
            playerSocket = new PlayerSocket();
        }
        return playerSocket;
    }

    public boolean connect(InetSocketAddress ip, int timeout) {

        try {
            if (!socket.isConnected()) {
                socket.connect(ip, timeout);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                this.scanner = new Scanner(System.in);
                System.out.println("Connected to the server.");
                return true;
            }else{
                System.out.println("Already Connected To Server");
                return true;
            }

        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void startCommunication() {
        // Start threads for reading and writing messages
        threadPool.submit(() -> {
            if(socket.isConnected())
                readMessages();
        });

        threadPool.submit(() -> {
             if(socket.isConnected())
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

    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
}
