package tictactoe.client.ui.states;

public class UserListItemUiState {

    public String username;
    public int score;

    public UserListItemUiState(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public UserListItemUiState(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username + " -> " + score + " points";
    }

}
