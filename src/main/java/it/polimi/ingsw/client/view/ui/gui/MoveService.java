package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MoveService extends Service<Move> {
    private final Move move;
    private final GUI gui;

    public MoveService(Move move, GUI gui) {
        this.move = move;
        this.gui = gui;
    }

    @Override
    protected Task<Move> createTask() {
        return new Task<Move> () {
            @Override
            protected Move call() throws Exception {
                gui.addMessage (move.ask (gui));
                gui.getInterpreter ().clear();
                return move;
            }
        };
    }
}
