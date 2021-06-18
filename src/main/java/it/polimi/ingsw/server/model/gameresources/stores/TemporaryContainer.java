package it.polimi.ingsw.server.model.gameresources.stores;


import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NoEmptyResourceException;
import it.polimi.ingsw.server.model.exception.NotContainedResourceException;
import it.polimi.ingsw.server.model.exception.NotHaveThisEffectException;
import it.polimi.ingsw.server.model.gamelogic.Player;

import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayList;
import java.util.HashMap;

public class TemporaryContainer extends UnboundedResourcesContainer implements GameComponent {
    private ArrayList <EmptyResource> emptyResources;
    private HashMap<Player, ArrayList<StorableResource>> modifiers;
    private ArrayList<Observer> observers;
    private boolean isContainerForProduction;

    /**
     * Method that stores the provided list of resources in this unbounded resource container.
     *
     * @param resources arraylist of storable resource to be stored in this container.
     * @return this updated container.
     */
    @Override
    public UnboundedResourcesContainer storeAll(ArrayList<StorableResource> resources) {
        super.storeAll (resources);
        notifyUpdate (generateUpdate ());
        return this;
    }

    @Override
    public void clear() {
        super.clear();
        emptyResources.clear();
        notifyUpdate(generateUpdate());
    }

    public TemporaryContainer() {
        super();
        this.emptyResources = new ArrayList<>();
        this.modifiers = new HashMap<>();
        this.observers = new ArrayList<> ();
        this.isContainerForProduction = false;
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
        notifyUpdate(generateUpdate(resourceCount));
        return new FaithPoint(resourceCount);
    }

    private Sendable generateUpdate(int amountOfFP){
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.GET_PENALTY_UPDATE);
        writer.addProperty ("penalty", amountOfFP);
        return writer.write ();
    }

    void store (EmptyResource emptyResource) {
        this.emptyResources.add(emptyResource);
        notifyUpdate(generateUpdate());
    }

    @Override
    public void store(StorableResource storableResource) {
        super.store(storableResource);
        notifyUpdate(generateUpdate());
    }

    /**
     * Method that decrease the amount of the stored resource by the amount provided. If the amount reaches "0", the
     * element is removed from the array.
     *
     * @param storableResource contains the type of the resource to be decremented and by how much to decrement it
     * @throws NotContainedResourceException thrown if the provided resource is not contained in this container.
     */
    @Override
    public void remove(StorableResource storableResource) throws NegativeResourceAmountException, NotContainedResourceException {
        super.remove (storableResource);
        notifyUpdate (generateUpdate ());
    }

    private Sendable generateUpdate(){
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.TEMP_CONTAINER_UPDATE);
        writer.addProperty ("storableResources", this.getAllResources());
        writer.addProperty ("emptyResourcesAmount", this.emptyResources.size());
        return writer.write ();
    }

    /**
     * @throws NoEmptyResourceException
     */
    public void transformEmptyResources(Player player, int resourceIndex) throws NoEmptyResourceException,
            NotHaveThisEffectException {
        StorableResource resource;
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
            throw new NoEmptyResourceException(player.getUsername());
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

    @Override
    public Iterable<Observer> getObservers() {
        return this.observers;
    }

    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        this.observers.add (observer);
        this.notifyUpdate (generateUpdate ());
    }

    public void setAsContainerForProduction(boolean isContainerForProduction) {
        this.isContainerForProduction = isContainerForProduction;
    }

    public boolean isContainerForProduction() {
        return this.isContainerForProduction;
    }
}
