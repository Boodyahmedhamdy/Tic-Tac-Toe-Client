/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.actions.SignOutAction;
import network.requests.LoginRequest;
import network.requests.StartGameRequest;
import network.responses.FailLoginResponse;
import network.responses.LoginResponse;
import network.responses.SuccessLoginResponse;

/**
 *
 * @author HP
 */
public class NetworkAcessLayer {
    
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    static {
//        outputStream = new ObjectOutputStream(new OutputStream());
    }
    
    
    public static void connect() {
        System.out.println("Connecting...");
    }
    
    /**
     * used to send a login Request to the server
     * @param loginRequest
     * @return 
    */
    public static boolean sendLoginRequest(LoginRequest loginRequest) {
        try {
            outputStream.writeObject(loginRequest);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(NetworkAcessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * returns a response from the server
     * @return LoginResponse
     */
    public static LoginResponse reciveLoginResponse() {
        try {
            LoginResponse response = (LoginResponse) inputStream.readObject();
            // sample of what the client should do with the returned result
            if(response instanceof FailLoginResponse) return new FailLoginResponse(" ");
            if(response instanceof SuccessLoginResponse) return new SuccessLoginResponse("", 234);
            return response;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NetworkAcessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    /**
     * sends the passed SignoutAction object to the server 
     * @param signOutAction
     * @throws java.io.IOException
     */
    public static void sendSignOutAction(SignOutAction signOutAction) throws IOException {
        outputStream.writeObject(signOutAction);
    }
    
    
    /**
     * sends a StartGameRequest to the server. the server will forward it to 
     * the user with passed username inside the StartGameRequest object
     * @param startGameRequest
     * @throws java.io.IOException
    */
    public static void sendStartGameRequest(StartGameRequest startGameRequest) throws IOException {
        outputStream.writeObject(startGameRequest);
    }
    
}
