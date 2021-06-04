package it.polimi.ingsw.client.view.lightweightmodel;

public class LWModel {
    private final LWGameBoard board;
    private final LWPersonalBoard personalBoard;
    private final InfoMatch infoMatch;

    public LWModel() {
        this.personalBoard = new LWPersonalBoard ();
        this.board = new LWGameBoard ();
        this.infoMatch = new InfoMatch ();
    }

    public LWGameBoard getBoard() {
        return board;
    }

    public InfoMatch getInfoMatch() {
        return infoMatch;
    }

    public LWPersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
