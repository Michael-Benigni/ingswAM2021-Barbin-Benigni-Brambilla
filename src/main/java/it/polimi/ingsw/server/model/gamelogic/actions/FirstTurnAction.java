package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.FirstTurn;

interface FirstTurnAction extends Action {
    /**
     * @param turn turn in which is checked the validity of this action
     * @return true if this Action can be performed after the Action performedActions, otherwise it returns false. This check
     * is done looking at the type of the performedActions, and if it is contained in the requires of this Action, this Action
     * is valid
     */
    @Override
    default boolean isValid(FirstTurn turn) {
        return true;
    }
}
