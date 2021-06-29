package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.UI;

/**
 * Class that represents the view of the match.
 */
public class LWModel {
    /**
     * Game board of this game.
     */
    private final LWGameBoard board;

    /**
     * Personal board owned by a player.
     */
    private final LWPersonalBoard personalBoard;

    /**
     * Information about this match.
     */
    private final InfoMatch infoMatch;

    /**
     * Constructor method of this class.
     */
    public LWModel() {
        this.personalBoard = new LWPersonalBoard ();
        this.board = new LWGameBoard ();
        this.infoMatch = new InfoMatch ();
    }

    /**
     * Getter method for the game board of this game.
     */
    public LWGameBoard getBoard() {
        return board;
    }

    /**
     * Getter method for the information of this game.
     */
    public InfoMatch getInfoMatch() {
        return infoMatch;
    }

    /**
     * Getter method for the personal board of this player.
     */
    public LWPersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * This method is used to attach the attached to the object that implements this interface.
     */
    public void attach(UI ui) {
        personalBoard.attach (ui);
        board.attach(ui);
        infoMatch.attach (ui);
    }
}
