/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.game;


import chess.game.board.Board;
import chess.game.board.Position;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CarrollFD
 */
public class Game {
    private Board board;
    private List<Move> moveList = new ArrayList<>();

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

    public Board getBoard() {
        return board;
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    /**
     * Forces the given move.  This should only be used when testing to see
     * if the given move produces an invalid game state.
     *
     * @param startPosition
     * @param endPosition
     */
    protected void forceMove(Position startPosition, Position endPosition) {
        board.forceMove(startPosition, endPosition);
    }

}
