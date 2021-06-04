package it.polimi.ingsw.client.view.lightweightmodel;

public class LWModel {
    private final LWeightBoard board;
    private final LWPersonalBoard personalBoard;
    private final InfoMatch infoMatch;

    public LWModel() {
        this.personalBoard = new LWPersonalBoard ();
        this.board = new LWeightBoard ();
        this.infoMatch = new InfoMatch ();
    }

    public LWeightBoard getBoard() {
        return board;
    }

    public InfoMatch getInfoMatch() {
        return infoMatch;
    }

    public LWPersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
