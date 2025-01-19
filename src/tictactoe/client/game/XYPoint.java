package tictactoe.client.game;

/**
 * @author Abdelrahman_Elshreif
 */
public class XYPoint {

    // pos : x,y string "x"
    int x;
    int y;
    Character playerSymbol;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPlayerSymbol(Character playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Character getPlayerSymbol() {
        return playerSymbol;
    }

    @Override
    public String toString() {
        return "Position : " + x + "," + y + " Player : " + playerSymbol;
    }
}
