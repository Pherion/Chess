/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.game;

import chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CarrollFD
 */
public class Game {
    private Board board = new Board();
    private List<Move> moveList = new ArrayList<>();

    public Board getBoard() {
        return board;
    }

    public List<Move> getMoveList() {
        return moveList;
    }
    
}
