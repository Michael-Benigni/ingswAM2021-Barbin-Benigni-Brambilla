package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.view.VirtualView;
import java.util.Objects;

/**
 * Class that is the view of the player into the Controller.
 */
public class User {
    private final VirtualView view;
    private String username;

    /**
     * Constructor method of this class that receives in input the VirtualView of this user.
     */
    public User(VirtualView view) {
        this.view = view;
    }

    /**
     * Method that updates the username of this user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for the username of this user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Method that returns a boolean -> true if the username of the provided user is equal to the username of this user.
     */
    public boolean sameUsername(User user) {
        return this.getUsername().equals(user.getUsername ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(view, user.view) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash (getUsername ());
    }

    /**
     * Getter method for the VirtualView of this user.
     */
    VirtualView getView() {
        return view;
    }
}
