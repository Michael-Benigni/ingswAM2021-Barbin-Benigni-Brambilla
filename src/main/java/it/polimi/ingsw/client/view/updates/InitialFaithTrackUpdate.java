package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWCell;
import java.util.ArrayList;

public class InitialFaithTrackUpdate implements ViewUpdate{
    private final ArrayList<Integer> VP;
    private final ArrayList<Boolean> isPopeSpace;
    private final ArrayList<String> sections;
    private final ArrayList<String> players;
    private final ArrayList<Integer> positions;

    public InitialFaithTrackUpdate(ArrayList<Integer> vp, ArrayList<Boolean> isPopeSpace, ArrayList<String> sections, ArrayList<String> players, ArrayList<Integer> positions) {
        VP = vp;
        this.isPopeSpace = isPopeSpace;
        this.sections = sections;
        this.players = players;
        this.positions = positions;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getBoard().updateFaithTrack(buildListOfCells());
        for (int i = 0; i < players.size (); i++)
            controller.getModel ().getBoard ().updatePlayerPosition (players.get (i), positions.get (i));
    }

    private ArrayList<LWCell> buildListOfCells() {
        ArrayList<LWCell> listOfCells = new ArrayList<>();
        for(int i = 0; i < this.VP.size(); i++)
            listOfCells.add(new LWCell(new ArrayList<> (), this.VP.get(i), this.isPopeSpace.get(i), this.sections.get (i)));
        return listOfCells;
    }

}
