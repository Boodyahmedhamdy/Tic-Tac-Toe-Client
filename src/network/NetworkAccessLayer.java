package network;

import java.io.IOException;
import network.actions.SignOutAction;
import network.requests.StartGameRequest;
import network.responses.StartGameResponse;
import tictactoe.client.PlayerSocket;

public class NetworkAccessLayer {

    private static PlayerSocket playerSocket = PlayerSocket.getInstance();

    public static void sendSignOutAction(SignOutAction signOutAction) throws IOException {
        playerSocket.sendRequest(signOutAction);
    }

    public static void sendStartGameRequest(StartGameRequest startGameRequest) throws IOException {
        playerSocket.sendRequest(startGameRequest);
    }

    public static void sendStartGameResponse(StartGameResponse startGameResponse) throws IOException {
        playerSocket.sendRequest(startGameResponse);
    }
}