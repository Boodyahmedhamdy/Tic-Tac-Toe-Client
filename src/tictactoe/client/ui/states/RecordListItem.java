package tictactoe.client.ui.states;

/**
 *
 * @author Abdelrahman_Elshreif
 */
public class RecordListItem {

    public String recordName;
    public String player1;
    public String player2;

    public String getRecordName() {
        return recordName;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public RecordListItem(String recName) {
        this.recordName = recName;
    }

    public RecordListItem(String recName, String player1, String player2) {
        this.recordName = recName;
        this.player1 = player1;
        this.player2 = player2;
    }

}
