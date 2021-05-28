package it.polimi.ingsw.client.view.lightweightmodel;

public class LightweightModel {
    private LWeightBoard board;
    private LWeightPersonalBoard personalBoard;


    public LightweightModel() {
        this.personalBoard = new LWeightPersonalBoard ();
        this.board = new LWeightBoard ();
    }

    public LWeightBoard getBoard() {
        return board;
    }

    public LWeightPersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
