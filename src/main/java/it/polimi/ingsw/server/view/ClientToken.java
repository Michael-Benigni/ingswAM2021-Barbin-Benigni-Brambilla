package it.polimi.ingsw.server.view;

public class ClientToken {
    private final int token;
    private static int tokenEmitted = 0;

    public ClientToken() {
        this.tokenEmitted++;
        this.token = this.tokenEmitted;
    }


    @Override
    public String toString() {
        return String.valueOf (token);
    }
}
