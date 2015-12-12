/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.Pieces;

import chess.game.GameInfoWrapper;
import chess.game.board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CarrollFD
 */
public class Bishop extends Piece {

    public Bishop(Position position, boolean color, GameInfoWrapper gameInfo) {
        super(position, PieceType.bishop, color, gameInfo);
    }

    @Override
    public boolean validateMove(Position position) {
        // calculate the changes
        int deltaX = Math.abs(getPosition().getX() - position.getX());
        int deltaY = Math.abs(getPosition().getY() - position.getY());
        
        // diagonals only
        if(deltaX != deltaY) {
            return false;
        }
        
        // perform global validation
        return super.validateMove(position);
    }
    
    @Override
    public List<Position> getValidMoves() {
        List<Position> validMoves = new ArrayList<>();
        Position checkPosition;
        boolean validMove = true;
        
        // loop through the eight directions the queen can move
        for(int direction = 0; direction < 4; direction++) {
            // re-set check position
            checkPosition = new Position(getPosition());
            
            // keep moving in each direction until an invalid move id
            // detected
            while(validMove) {                
                // move one space in the apropriate direction
                switch(direction) {
                    case 0:
                        // north west
                        checkPosition = new Position(checkPosition.getX() - 1, checkPosition.getY() - 1);
                        break;
                    case 1:
                        // north east 
                        checkPosition = new Position(checkPosition.getX() + 1, checkPosition.getY() - 1);
                        break;
                    case 2:
                        // south west
                        checkPosition = new Position(checkPosition.getX() - 1, checkPosition.getY() + 1);
                        break;
                    default:
                        // south east
                        checkPosition = new Position(checkPosition.getX() + 1, checkPosition.getY() + 1);
                }
                
                // validate the move
                validMove = validateMove(checkPosition);
                
                // if it's good, add it to the list
                if(validMove) {
                    validMoves.add(checkPosition);
                }
            }
        }
        
        // return the list of valid moves
        return validMoves;
    }
    
}
