package tictactoe.client.ui.states;

import java.util.List;

/**
 *
 * @author Abdelrahman_Elshreif
 */
public class RecordListItem {

    private String recordName;
    private List<String> moves;

    public RecordListItem(String recordName, List<String> moves) {
        this.recordName = recordName;
        this.moves = moves;
    }

    // Getters
    public String getRecordName() {
        return recordName;
    }

    public List<String> getMoves() {
        return moves;
    }
}
