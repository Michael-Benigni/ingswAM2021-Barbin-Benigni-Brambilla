package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.*;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
class GameTest {

    private Game game;
    private int countSequence = 0;
    private HashMap<ArrayList<Action>, Result> currentMap;

    @BeforeEach
    private void init() throws IllegalNumberOfPlayersException, NegativeResourceAmountException, EmptyDeckException, TooManyPlayersException, WrongBoardException, CellNotFoundInFaithTrackException {
        game = new MultiplayerGame(4);
        ArrayList<PersonalBoard> personalBoards = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            personalBoards.add(new PersonalBoard(new WarehouseDepots(0, new ArrayList<>()), 4, 3, 2, 2));
        GameBoard gameBoard = GameBoardTest.initGameBoard();
        ArrayList<InitialParams> listOfParams = new ArrayList<>();
        listOfParams.add(new InitialParams(0, 0));
        listOfParams.add(new InitialParams(0, 0));
        listOfParams.add(new InitialParams(0, 0));
        listOfParams.add(new InitialParams(0, 0));
        game.createPlayer();
        game.createPlayer();
        game.createPlayer();
        game.createPlayer();
        game.setup(personalBoards, gameBoard, listOfParams);
    }

    /**
     * Method used to skip the first turn.
     */
    private void skipInitialTurn() throws Exception {
        for(Player p : game.getAllPlayers())
            game.performActionOf(p, new MultiplayerGame.EndTurnMultiplayerAction ());
    }

    @Test
    void isGameOver() {
    }

    @Test
    void setup() {
    }

    @Test
    void attachToGameBoard() {
    }

    /**
     * Test on "createPlayer" method of this class.
     * It tests if the method successfully throws an exception when trying to overflow the number of players.
     */
    @Test
    void createPlayer() {
        try {
            game.createPlayer();
            fail();
        } catch (TooManyPlayersException e) {
            assertEquals(game.getAllPlayers().size(), 4);
        }
    }

    /**
     * Test on "performActionOf" method of this class.
     * It tests if the method successfully throws an exception when the provided player isn't the current player.
     */
    @Test
    void performActionOfWhenDifferentPlayer() throws Exception {
        ArrayList<Player> players = game.getAllPlayers();
        Player p = players.get(players.indexOf(game.getCurrentPlayer()) + 1);
        try {
            game.performActionOf(p, new StrongboxAction("store", new StorableResource(ResourceType.COIN, 6)));
            fail();
        } catch (IsNotCurrentPlayerException e) {
            assertNotEquals(p, game.getCurrentPlayer());
        }
    }

    private void initPerformActionTest() throws Exception {
        skipInitialTurn();
        ArrayList<HashMap<ArrayList<Action>, Result>> listOfMaps = getListsOfAction();
        currentMap = listOfMaps.get(countSequence);
        countSequence++;
    }

    @RepeatedTest(value = 3, name = "performActionOf - Sequence #{currentRepetition}") //remember to update the value for each added sequence
    void performActionOf() throws Exception {
        initPerformActionTest();
        ArrayList<ArrayList<Action>> listOfLists = new ArrayList<>((currentMap.keySet()));
        ArrayList<Action> actions = listOfLists.get(0);
        Player player = game.getCurrentPlayer();
        boolean isCorrect = true;
        for(Action action : actions) {
            if(isCorrect) {
                try {
                    game.performActionOf(player, action);
                } catch (NoValidActionException e) {
                    assertEquals(currentMap.get(actions), Result.ERR_VALIDITY);
                    isCorrect = false;
                } catch (IllegalTurnState i) {
                    assertEquals(currentMap.get(actions), Result.ERR_TURN_STATE);
                    isCorrect = false;
                }
            }
        }
        if(isCorrect)
            assertEquals(currentMap.get(actions), Result.CORRECT);
        if(!(actions.get(actions.size() - 1).equals(new MultiplayerGame.EndTurnMultiplayerAction ())))
            game.setNextPlayer();
    }

    private enum Result {
        CORRECT("correct"), ERR_VALIDITY("errValidity"), ERR_TURN_STATE("errTurnState");
        private final String result;

        Result(String result) {
            this.result = result;
        }
    }

    private ArrayList<HashMap<ArrayList<Action>, Result>> getListsOfAction() throws NegativeResourceAmountException {
        HashMap<String, Action> possibleActions = ActionConstructor.getPossibleAction();
        ArrayList<HashMap<ArrayList<Action>, Result>> returningList = new ArrayList<>();
        HashMap<ArrayList<Action>, Result> map = new HashMap<>();
        ArrayList<Action> actions = new ArrayList<>();

        //copy the following lines to add now test cases. --> correct
        //Test 1: 3 times always valid.
        actions.add(possibleActions.get("ALWAYS_VALID"));
        actions.add(possibleActions.get("ALWAYS_VALID"));
        actions.add(possibleActions.get("ALWAYS_VALID"));
        actions.add(possibleActions.get("END_TURN"));
        map.put(new ArrayList<>(actions), Result.CORRECT);
        returningList.add(new HashMap<>(map));
        map.clear();
        actions.clear();

        //Test 2: 1 time mutual exclusive. --> correct
        actions.add(possibleActions.get("ALWAYS_VALID"));
        actions.add(possibleActions.get("UNIQUE"));
        actions.add(possibleActions.get("ALWAYS_VALID"));
        actions.add(possibleActions.get("END_TURN"));
        map.put(new ArrayList<>(actions), Result.CORRECT);
        returningList.add(new HashMap<>(map));
        map.clear();
        actions.clear();

        //Test 3: production started and ended. --> correct
        actions.add(possibleActions.get("ALWAYS_VALID"));
        actions.add(possibleActions.get("ALWAYS_VALID"));
        actions.add(possibleActions.get("ALWAYS_VALID"));
        actions.add(possibleActions.get("END_TURN"));
        map.put(new ArrayList<>(actions), Result.CORRECT);
        returningList.add(new HashMap<>(map));
        map.clear();
        actions.clear();

        return returningList;
    }

    @Test
    void notifyLastRoundUpdate() {
    }

    @Test
    void setNextPlayer() {
    }

    @Test
    void getCurrentTurn() {
    }

    @Test
    void getAllPlayers() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void getGameBoard() {
    }

    @Test
    void computeWinners() {
    }

    @Test
    void getParams() {
    }

    @Test
    void setLastRound() {
    }

    @Test
    void getObservers() {
    }

    @Test
    void attach() {
    }
}