/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.game.board;

import chess.Pieces.Piece;

import java.util.ArrayList;

/**
 *
 * @author CarrollFD
 */
public class Board {
    public static final int BOARD_SIZE_X = 8;
    public static final int BOARD_SIZE_Y = 8;
    
    private ArrayList<Piece> whitePieces = new ArrayList<>();
    private ArrayList<Piece> blackPieces = new ArrayList<>();

    public void Board() {
        initializeBoard();
    }

    public void initializeBoard() {
        // TODO: Initialize the white and black pieces and put them in the starting positions.
    }
    
    public boolean threatenedBy(Position position, boolean color) {
        // TODO: indicate if the given position is threatedned by the provided color
        return false;
    }
    
    public boolean isInterveningPiece(Position position1, Position position2) {
        // TODO: calculate if there is an intervening peice between the two positions.
        // throw an exception if the move is not horizontal, verticle, or diagonal
        // allow for a peice on each end-points.
        return false;
    }
    
    public Piece getPieceAt(Position position) {
        // TODO: determine if there is a peice at the given positiion, return
        // it, otherwise null
        return null;
    }
}
