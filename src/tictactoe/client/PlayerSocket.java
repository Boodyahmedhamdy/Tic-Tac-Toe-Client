package tictactoe.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.requests.Request;
import network.requests.StartGameRequest;
import network.responses.GetAvailablePlayersResponse;
import network.responses.PlayAtResponse;
import network.responses.RefuseStartGameResponse;
import network.responses.Response;
import network.responses.SignOutResponse;
import network.responses.StartGameResponse;

public final class PlayerSocket {

    private static PlayerSocket instance;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private Thread listenerThread;
    private static GameScreenOnlineController gameScreenOnlineController ;

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
            try {
                socket.connect(ip, timeout);
            } catch (SocketTimeoutException ex) {
                System.out.println("Can't Connect");
                resetSocket();
                return false;
            }
            System.out.println("Connected to the server.");

            // Initialize the streams after the socket is connected
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Failed to connect to server: " + ex.getMessage(), ex);
            resetSocket();
            return false;
        }
    }

    public void startListenerThread() {
        listenerThread = new Thread(() -> {
            System.out.println("Listening Thread started running ...");
            while (running.get()) {
                try {
                    System.out.println("****ON LISTENING THREAD BEFORE READING ***********");
                    Object incomingObject = in.readObject();
                    System.out.println("****ON LISTENING THREAD AFTER READING ***********" + incomingObject.getClass().getSimpleName());

                    if (incomingObject instanceof Request) {
                        Request request = (Request) incomingObject;
                        System.out.println("Handling request: " + request.getClass().getSimpleName());

                        if (request instanceof StartGameRequest) {
                            System.out.println("Start Game request received for username: " + ((StartGameRequest) request).getRecieverUsername());
                            handleStartGameRequest((StartGameRequest) request);
                        }

                    }

                    if (incomingObject instanceof Response) {
                        Response response = (Response) incomingObject;
                        System.out.println("Handling response: " + response.getClass().getSimpleName());

                        if (response instanceof StartGameResponse) {
                            System.out.println("Start Game Response received.");
                            handleStartGameResponse((StartGameResponse) response);

                        } else if (response instanceof GetAvailablePlayersResponse) {
                            System.out.println("GetAvailablePlayersResponse recieved");
                            handleGetAvailablePlayersResponse((GetAvailablePlayersResponse) response);
                        } else if (response instanceof SignOutResponse) {
                            System.out.println("SignOutResponse recieved");
                            handleSignOutResponse((SignOutResponse) incomingObject);
                        }

                        else if (response instanceof PlayAtResponse) {
                            System.out.println("PlayAtResponse recieved");
                            handlePlayAtResponse( (PlayAtResponse) incomingObject);
                        }


                    }
                } catch (IOException ex) {
                    Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "I/O error in listener thread: " + ex.getMessage(), ex);
                    break; // Exit the loop on I/O errors
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Class not found in listener thread: " + ex.getMessage(), ex);
                }
            }
        });
        listenerThread.start();
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
            if (listenerThread != null) {

                listenerThread.join();
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed() && out != null && in != null;
    }

    private void handleStartGameRequest(StartGameRequest startGameRequest) {
        System.out.println("waiting for StartGameRequest to come....");
        System.out.println("StartGameRequest just arrived with type" + startGameRequest.getClass().getSimpleName());
        AvailablePlayersScreenController.onReceiveStartGameRequest(startGameRequest);

    }

    private void handleStartGameResponse(StartGameResponse startGameResponse) {
        System.out.println("waiting for StartGameResponse to come....");
        System.out.println("StartGameResponse just arrived with type" + startGameResponse.getClass().getSimpleName());
        AvailablePlayersScreenController.onReceiveStartGameResponse(startGameResponse);
    }

    private void handleGetAvailablePlayersResponse(GetAvailablePlayersResponse response) {
        AvailablePlayersScreenController.onRecieveGetAvailablePlayersResponse(response);
    }

    private void handleSignOutResponse(SignOutResponse signOutResponse) {
        AvailablePlayersScreenController.onRecieveSignOutResponse(signOutResponse);
    }

    private void resetSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            socket = new Socket();
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, "Error resetting socket", ex);
        }
    }
    
    private void handlePlayAtResponse(PlayAtResponse playAtResponse) {
        
        gameScreenOnlineController.OnReceivePlayerAction(playAtResponse);
    }
    public void setGameScreenOnlineController(GameScreenOnlineController gameScreenOnlineController){
        this.gameScreenOnlineController=gameScreenOnlineController;
    }
}
