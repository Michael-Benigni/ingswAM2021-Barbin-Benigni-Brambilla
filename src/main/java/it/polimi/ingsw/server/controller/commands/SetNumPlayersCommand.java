package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.model.exception.EmptyDeckException;
import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import java.io.FileNotFoundException;

/**
 * Class that represents the initial command that allows to the leader of the game to change the number of players of this game.
 */
public class SetNumPlayersCommand implements Command{
    private int dimension;

    /**
     * Constructor method of this class. It receives in input the number of players you want to reach for this game.
     */
    public SetNumPlayersCommand(int dimension) {
        this.dimension = dimension;
    }

    /**
     * Override method of "handled" method of "Command" class. In this case, it updates the number of players allowed in the waiting room.
     */
    @Override
    public void handled(Controller controller, User user) throws FileNotFoundException, InvalidUserException, IllegalNumberOfPlayersException, TooManyPlayersException, ImpossibleChangingSizeException, EmptyDeckException {
        controller.setWaitingRoomDimension(user, dimension);
    }
}
