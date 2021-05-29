package it.polimi.ingsw.client.view.lightweightmodel;

public class LightweightModel {
    private final LWeightBoard board;
    private final LWeightPersonalBoard personalBoard;
    private final InfoMatch infoMatch;

    public LightweightModel() {
        this.personalBoard = new LWeightPersonalBoard ();
        this.board = new LWeightBoard ();
        this.infoMatch = new InfoMatch ();
    }

    public LWeightBoard getBoard() {
        return board;
    }

    public InfoMatch getInfoMatch() {
        return infoMatch;
    }

    public LWeightPersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
