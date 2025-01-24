package tictactoe.client.ui.states;

public class UserListItemUiState {
    private String username;
    private int score;

    public UserListItemUiState(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return username + " (" + score + " points)";
    }
}