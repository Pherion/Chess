package chess.game;


import chess.game.board.Board;
import chess.game.board.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game of chess.
 *
 * @author CarrollFD
 */
public class Game {
    // the game board
    private Board board;

    // the list of moves executed during this game
    private List<Move> moveList = new ArrayList<>();

    /**
     * Constructs a new game.
     */
    public Game() {
        GameInfoWrapper wrapper = new GameInfoWrapper(this);
        board = new Board(wrapper);
    }

    /**
     * Deep Copy Constructor
     *
     * @param toCopy Game to Copy
     */
    public Game(Game toCopy) {
        // create a new wrapper
        GameInfoWrapper wrapper = new GameInfoWrapper(this);

        // make a deep copy of the board
        board = new Board(toCopy.getBoard(), wrapper);

        // make a deep copy of the move list
        for(Move move : toCopy.getMoveList()) {
            moveList.add(new Move(move));
        }
    }

    /**
     * @return the game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return the move list for this game
     */
    public List<Move> getMoveList() {
        return moveList;
    }

    /**
     * Forces the given move.  This should only be used when testing to see
     * if the given move produces an invalid game state.
     *
     * @param startPosition The starting position.
     * @param endPosition The ending position.
     */
    protected void forceMove(Position startPosition, Position endPosition) {
        board.forceMove(startPosition, endPosition);
    }

}
