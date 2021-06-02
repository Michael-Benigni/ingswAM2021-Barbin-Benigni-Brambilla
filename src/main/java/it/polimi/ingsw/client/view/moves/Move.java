package it.polimi.ingsw.client.view.moves;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.cli.Interlocutor;
import it.polimi.ingsw.client.view.ui.cli.Interpreter;
import it.polimi.ingsw.utils.network.Sendable;

/**
 * each move corresponds to a message sent to the server
 */
public interface Move {
    Sendable ask(UI ui);
}
