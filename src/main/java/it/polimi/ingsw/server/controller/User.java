package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.view.VirtualView;
import it.polimi.ingsw.utils.network.ValidMoveMessage;

import java.util.Objects;

public class User {
    private final VirtualView view;
    private String username;

    public User(VirtualView view) {
        this.view = view;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean sameUsername(User user) {
        return this.getUsername().equals(user.getUsername ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals (getUsername (), user.getUsername ());
    }

    @Override
    public int hashCode() {
        return Objects.hash (getUsername ());
    }

    VirtualView getView() {
        return view;
    }
}
