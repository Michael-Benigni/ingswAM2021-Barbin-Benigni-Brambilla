package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VictoryPoint;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import java.util.ArrayList;

interface Effect {
    void applyOn(Player p);
}

public class LeaderCard {
    private boolean played;
    private ArrayList<Requirement> requirements;
    private VictoryPoint victoryPoint;
    private ArrayList<Effect> effects; //TODO: is it needed to use ArrayList since the Effect is only 1?

    public LeaderCard(ArrayList<Requirement> requirements, VictoryPoint victoryPoint, ArrayList<Effect> effects) {
        this.requirements = requirements;
        this.victoryPoint = victoryPoint;
        this.effects = effects;
        this.played = false;

        Effect discount = (p) -> {};
        Effect extraDepot = (p) -> {};
        Effect transformWhiteMarble = (p) -> {};
        Effect extraProductionPower = (p) -> {};
    }

    private FaithPoint whenDiscarded() {
        try {
            return new FaithPoint(1);
        } catch (NegativeResourceAmountException e) {
            return null;
        }
    }

    private void whenPlayed (Player player) {
        this.played = true;
        for (Effect effect : effects) {
            effect.applyOn(player);
        }
    }

    boolean checkRequirementsOf (Player player) {
        //TODO: implement this
        return false;
    }
}
