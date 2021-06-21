package it.polimi.ingsw.client.view.moves;

public interface MoveWrapper {

    default Move getMove() {
        return null;
    }

    default String getCmd() {
        return null;
    }
}

