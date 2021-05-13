package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import java.io.FileNotFoundException;

public class SetNumPlayersCommand implements Command{
    private int dimension;

    public SetNumPlayersCommand(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public void handled(Controller controller, User user) throws FileNotFoundException, InvalidUserException, IllegalNumberOfPlayersException, TooManyPlayersException, ImpossibleChangingSizeException {
        controller.setWaitingRoomDimension(user, dimension);
    }
}
