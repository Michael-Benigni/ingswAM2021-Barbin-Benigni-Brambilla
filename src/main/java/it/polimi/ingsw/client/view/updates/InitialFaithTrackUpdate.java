package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.InfoMatch;
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
    public void update(View view) {

        view.getModel().getBoard().updateFaithTrack(buildListOfCells(view.getModel().getInfoMatch()));
    }

    private ArrayList<LWCell> buildListOfCells(InfoMatch infoMatch) {
        ArrayList<LWCell> listOfCells = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add(infoMatch.getYourUsername());
        usernames.addAll(infoMatch.getAllPlayersUsernames ());
        listOfCells.add(new LWCell(usernames, this.VP.get(0), this.isPopeSpace.get(0)));
        usernames.clear();
        for(int i = 1; i < this.VP.size(); i++){
            listOfCells.add(new LWCell(usernames, this.VP.get(i), this.isPopeSpace.get(i)));
        }
        return listOfCells;
    }

}
