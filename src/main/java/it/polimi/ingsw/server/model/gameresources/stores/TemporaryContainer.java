package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.model.exception.NoEmptyResourceException;
import it.polimi.ingsw.server.model.exception.NotHaveThisEffectException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Publisher;

import java.util.ArrayList;
import java.util.HashMap;

public class TemporaryContainer extends UnboundedResourcesContainer implements Publisher {
    private ArrayList <EmptyResource> emptyResources;
    private HashMap<Player, ArrayList<StorableResource>> modifiers;

    @Override
    public void clear() {
        super.clear();
        emptyResources.clear();
    }

    public TemporaryContainer() {
        super();
        this.emptyResources = new ArrayList<>();
        this.modifiers = new HashMap<>();
    }

    public FaithPoint getPenalty() {
        ArrayList<StorableResource> resources = null;
        resources = getAllResources();
        int resourceCount = 0;
        for (StorableResource r : resources) {
            int amount = 1;
            while (!r.amountLessEqualThan(amount-1))
                amount++;
            resourceCount += amount - 1;
        }
        return new FaithPoint(resourceCount);
    }

    void store (EmptyResource emptyResource) {
        this.emptyResources.add(emptyResource);
    }


    /**
     * @throws NoEmptyResourceException
     */
    public void transformEmptyResources(Player player, int resourceIndex) throws NoEmptyResourceException, NotHaveThisEffectException {
        StorableResource resource = null;
        if(this.modifiers.containsKey(player) && resourceIndex < this.modifiers.get(player).size()){
            resource = this.modifiers.get(player).get(resourceIndex);
        }
        else{
            throw new NotHaveThisEffectException();
        }
        try {
            this.emptyResources.remove(emptyResources.size() - 1);
            store(resource);
        } catch (IndexOutOfBoundsException e) {
            throw new NoEmptyResourceException();
        }
    }

    public void addPlayerModifier(Player player, StorableResource resource) {
        if(this.modifiers.containsKey(player)) {
            this.modifiers.get(player).add(resource);
        }
        else {
            ArrayList<StorableResource> resources = new ArrayList<>();
            resources.add(resource);
            this.modifiers.put(player, resources);
        }
    }

    /**
     * This method notifies a change in the status of the publisher to the Observers registered, usually
     */
    @Override
    public void publish() {

    }

    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {

    }
}
