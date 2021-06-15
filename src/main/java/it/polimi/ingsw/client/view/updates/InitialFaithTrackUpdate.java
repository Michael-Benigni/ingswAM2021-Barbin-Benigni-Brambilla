package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWCell;
import java.util.ArrayList;

public class InitialFaithTrackUpdate implements ViewUpdate{
    private final ArrayList<Integer> VP;
    private final ArrayList<Boolean> isPopeSpace;

    public InitialFaithTrackUpdate(ArrayList<Integer> vp, ArrayList<Boolean> isPopeSpace) {
        VP = vp;
        this.isPopeSpace = isPopeSpace;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getBoard().updateFaithTrack(buildListOfCells());
    }

    private ArrayList<LWCell> buildListOfCells() {
        ArrayList<LWCell> listOfCells = new ArrayList<>();
        for(int i = 0; i < this.VP.size(); i++)
            listOfCells.add(new LWCell(new ArrayList<> (), this.VP.get(i), this.isPopeSpace.get(i)));
        return listOfCells;
    }

}
