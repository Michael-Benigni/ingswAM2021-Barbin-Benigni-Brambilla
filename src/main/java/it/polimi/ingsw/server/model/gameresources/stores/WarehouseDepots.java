package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.Sendable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents a collection of depots.
 */
public class WarehouseDepots implements GameComponent {
    private Integer numberOfDepots;
    private ArrayList<Integer> capacities;
    private ArrayList<Depot> listDepot;
    private Observer observer;

    /**
     * Constructor method of this class. It reads from
     * the database how many depots are contained and the capacity of each one.
     */
    public WarehouseDepots(int numberOfDepots, ArrayList<Integer> capacities) {
        this.numberOfDepots = numberOfDepots;
        this.capacities = capacities;
        setListDepot();
        notifyUpdate(generateUpdate ());
    }


    /**
     * @return getter of the capacities ArrayList
     */
    private ArrayList<Integer> getCapacities() {
        return capacities;
    }


    /**
     * method used to create the depots
     */
    private void setListDepot() {
        listDepot = new ArrayList<>();
        for (int i = 0; i < numberOfDepots; i++) {
            Depot depot = new Depot(capacities.get(i));
            listDepot.add(depot);
        }
    }

    /**
     * method used to add an extra depot to the list of all depots.
     * this depot is provided by the power of a leader card
     * @param depotCapacity is the capacity of the extra depot
     * @param resourceType is the unique resourceType storable in this depot
     */
    public void addExtraDepot(int depotCapacity, ResourceType resourceType) throws NegativeResourceAmountException,
            NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(depotCapacity, resourceType);
        this.listDepot.add(extraDepot);
        notifyUpdate(generateUpdate ());
    }

    /**
     * Method that checks if the provided resource is already contained into another depot.
     * @param resourceToCompare is the resource provided in input.
     * @param depotIndex is an integer that represents the number of the depot in which you want to store the resource.
     * @return a boolean value: false if every other depot contains a different type of resource.
     */
    private boolean ifAlreadyContainedInOtherDepots(StorableResource resourceToCompare, int depotIndex) {
        for (int i = 0; i < this.numberOfDepots; i++)
            if(i != depotIndex && listDepot.get(i).alreadyContained(resourceToCompare))
                return true;
        return false;
    }

    /**
     * Method that returns if the index is correct.
     * @param depotIndex the integer we want to verify.
     * @return a boolean value: true if the depot index is correct.
     * @throws WrongDepotIndexException thrown if the integer is less than zero or exceeds the number of depots in
     * this warehouse.
     */
    private boolean ifDepotIndexIsCorrect(int depotIndex) throws WrongDepotIndexException {
        if(depotIndex < 0 || depotIndex > listDepot.size())
            throw new WrongDepotIndexException(depotIndex);
        else
            return true;
    }

    /**
     * Method that finds the right depot in the arraylist and puts the provided resource into it.
     * @param resourceToStore resource to be inserted in the right depot.
     * @param depotIndex integer useful to recognize the right depot.
     * @throws Exception thrown by "ifDepotIndexIsCorrect" and "IfNotSameTypeInOtherDepots" methods.
     */
    public void store(StorableResource resourceToStore, int depotIndex) throws NotEqualResourceTypeException, ResourceOverflowInDepotException, WrongDepotIndexException, SameResourceTypeInDifferentDepotsException {
        if (ifDepotIndexIsCorrect (depotIndex)) {
            if (!(ifAlreadyContainedInOtherDepots(resourceToStore, depotIndex)) || depotIndex >= this.numberOfDepots) {
                try {
                    listDepot.get(depotIndex).storeResourceInDepot(resourceToStore);
                } finally {
                    notifyUpdate(generateUpdate ());
                }
            } else
                throw new SameResourceTypeInDifferentDepotsException();
        } else
            throw new WrongDepotIndexException(depotIndex);
    }


    /**
     * Method that finds the right depot in the arraylist and removes the provided resource from it.
     * @param resourceToRemove resource to be removed from the right depot.
     * @param depotIndex integer useful to recognize the right depot.
     * @throws Exception can be thrown by "removeResourceFromDepot" method of "Depot" class.
     */
    public void remove(StorableResource resourceToRemove, int depotIndex) throws WrongDepotIndexException, NegativeResourceAmountException,
            NotEqualResourceTypeException, EmptyDepotException {
        Depot currentDepot;
        if(ifDepotIndexIsCorrect(depotIndex)) {
            currentDepot = listDepot.get(depotIndex);
            currentDepot.removeResourceFromDepot(resourceToRemove);
            notifyUpdate(generateUpdate ());
        }
    }

    /**
     * this method generates the update
     * message to send it to the clients
     * @return the created message
     */
    private Sendable generateUpdate(){
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.WAREHOUSE_UPDATE);
        writer.addProperty ("depots", this.listDepot);
        return writer.write ();
    }

    /**
     * Method that swaps two depots in the array with each other.
     * @param depotIndex1 index of the first depot
     * @param depotIndex2 index of the second depot.
     */
    public StorableResource swapDepots(int depotIndex1, int depotIndex2) throws WrongDepotIndexException, EmptyDepotException,
            NegativeResourceAmountException, NotEqualResourceTypeException, SameResourceTypeInDifferentDepotsException {
        StorableResource depotOverflow = null;
        if (ifDepotIndexIsCorrect(depotIndex1) && ifDepotIndexIsCorrect(depotIndex2)) {
            Depot depot1 = listDepot.get (depotIndex1);
            Depot depot2 = listDepot.get (depotIndex2);
            StorableResource tempResource1 = depot1.getStoredResource ();
            StorableResource tempResource2 = depot2.getStoredResource ();
            if (ifAlreadyContainedInOtherDepots (tempResource1, depotIndex1) && ifAlreadyContainedInOtherDepots (tempResource2, depotIndex2)) {
                StorableResource resource1 = getResourceAndRemove (depot1);
                StorableResource resource2 = getResourceAndRemove (depot2);
                try {
                    try {
                        depot1.storeResourceInDepot (resource2);
                    } catch (ResourceOverflowInDepotException e) {
                        depotOverflow = e.getResource ();
                    }
                    try {
                        depot2.storeResourceInDepot (resource1);
                    } catch (ResourceOverflowInDepotException e) {
                        depotOverflow = e.getResource ();
                    }
                } catch (NotEqualResourceTypeException e) {
                    try {
                        depot1.clear ();
                        depot2.clear ();
                        depot1.storeResourceInDepot (resource1);
                        depot2.storeResourceInDepot (resource2);
                    } catch (ResourceOverflowInDepotException ignored) {
                        ignored.printStackTrace ();
                    }
                } finally {
                    notifyUpdate (generateUpdate ());
                }
            }
        } else
            throw new SameResourceTypeInDifferentDepotsException ();
        return depotOverflow;
    }

    private StorableResource getResourceAndRemove(Depot depot) throws NotEqualResourceTypeException, NegativeResourceAmountException {
        StorableResource resource;
        try {
            resource = depot.getStoredResource ();
            depot.removeResourceFromDepot(resource);
        } catch (EmptyDepotException e) {
            resource = null;
        }
        return resource;
    }

    /**
     * Method that returns a copy of the resource stored in the depot represented by the provided depot index.
     * @param depotIndex integer that points to the right depot.
     * @return a copy of the resource contained inside the specified depot.
     */
    private StorableResource getResourceFromDepot(int depotIndex) {
        StorableResource temporaryResource;
        try {
            temporaryResource = listDepot.get(depotIndex).getStoredResource();
        } catch(EmptyDepotException e) {
            temporaryResource = null;
        }
        return temporaryResource;
    }

    /**
     * method that provides an ArrayList
     * of all the storable resources
     * contained into the warehouse
     * @return list of StorableResource
     */
    public ArrayList <StorableResource> getAllResources() {
        ArrayList <StorableResource> listOfAllResources = new ArrayList<>(0);
        for(int i = 0; i < this.listDepot.size(); i++) {
            if(this.getResourceFromDepot(i) != null)
                listOfAllResources.add(this.getResourceFromDepot(i));
        }
        return listOfAllResources;
    }

    /**
     * override of the method equals
     * @param o the object we want to compare
     * @return boolean value: true if the object "o" is equal to the caller
     *                           false if the object "o" isn't equal to the caller
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDepots that = (WarehouseDepots) o;
        return Objects.equals(numberOfDepots, that.numberOfDepots)
                && Objects.equals(capacities, that.capacities)
                && Objects.equals(listDepot, that.listDepot);
    }


    /**
     * override of the method hashCode
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(numberOfDepots, capacities, listDepot);
    }

    @Override
    public Iterable<Observer> getObservers() {
        ArrayList<Observer> observers = new ArrayList<> ();
        if (observer != null)
            observers.add (observer);
        return observers;
    }

    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        this.observer = observer;
        this.notifyUpdate (generateUpdate ());
    }
}