/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.Pieces;

import chess.game.GameInfoWrapper;
import chess.game.board.Position;
import chess.game.StartingPositions;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the King.
 * 
 * @author CarrollFD
 */
public class King extends Piece {    

    /**
     *
     * @param position
     * @param color
     * @param gameInfo
     */
    public King(Position position, boolean color, GameInfoWrapper gameInfo) {
        super(position, PieceType.king, color, gameInfo);
    }

    public King(King toCopy, GameInfoWrapper gameInfo) {
        super(toCopy, gameInfo);
    }
    
    @Override
    public List<Position> getValidMoves() {
        List<Position> validMoves = new ArrayList<>();
        
        // check the castles
        if(!isMoved()) {
            // check the first possible castle
            Position castlePosition = new Position(getPosition().getX() - 2, getPosition().getY());
            
            if(validateMove(castlePosition)) {
                validMoves.add(castlePosition);
            }
            
            // check the second possible castle
            castlePosition = new Position(getPosition().getX() + 2, getPosition().getY());
            
            if(validateMove(castlePosition)) {
                validMoves.add(castlePosition);
            }
        }
        
        // check for the eight possible moves around the king
        Position checkPosition;
        for(int direction = 0; direction < 8; direction++) {
            switch(direction) {
                case 0:
                    // north
                    checkPosition = new Position(getPosition().getX(), getPosition().getY() - 1);
                    break;
                case 1:
                    // south
                    checkPosition = new Position(getPosition().getX(), getPosition().getY() + 1);
                    break;
                case 2:
                    // west 
                    checkPosition = new Position(getPosition().getX() - 1, getPosition().getY());
                    break;
                case 3:
                    // east 
                    checkPosition = new Position(getPosition().getX() + 1, getPosition().getY());
                    break;
                case 4:
                    // north west
                    checkPosition = new Position(getPosition().getX() - 1, getPosition().getY() - 1);
                    break;
                case 5:
                    // north east 
                    checkPosition = new Position(getPosition().getX() + 1, getPosition().getY() - 1);
                    break;
                case 6:
                    // south west
                    checkPosition = new Position(getPosition().getX() - 1, getPosition().getY() + 1);
                    break;
                default:
                    // south east
                    checkPosition = new Position(getPosition().getX() + 1, getPosition().getY() + 1);
            }
            
            // validate the move, and add it if its good
            if(validateMove(checkPosition)) {
                validMoves.add(checkPosition);
            }
        }
        
        return validMoves;
    }
    
    @Override
    public boolean validateMove(Position position) {
        // change in position
        int deltaX = Math.abs(getPosition().getX() - position.getX());
        int deltaY = Math.abs(getPosition().getY() - position.getY());
        
        // check to make sure the target position is not threatened
        if(gameInfo.threatenedBy(position, !getColor())) {
            return false;
        }
        
        // check for a possible castle (the king moves exactly 2 spaces)
        if(deltaX == 2 && deltaY == 0) {
            return validateCastle();
        }
        
        // if not castling:
        // validate we are moving a maximum of one space in any direction        
        if(deltaX > 1 || deltaY > 1) {
            return false;
        }
        
        return super.validateMove(position);
    }

    private boolean validateCastle() {
        // verrify not in check
        if(gameInfo.threatenedBy(getPosition(), !getColor())) {
            return false;
        }

        // check in which direction the castle is being attempted
        // true = castle left
        // false = casetle right
        boolean direction = getPosition().getX() - getPosition().getX() > 0;
        Position rookPosition;
        Position interveningCheckPosition;

        // determine the apropriate rooks necessary position
        if(getColor() && direction) {
            // white castling to the left
            rookPosition = new Position(StartingPositions.ROOK_1, StartingPositions.WHITE_NON_PAWN_Y);
            interveningCheckPosition = new Position(StartingPositions.ROOK_1 + 1, StartingPositions.WHITE_NON_PAWN_Y);
        } else if(getColor() && !direction) {
            // white castling to the right
            rookPosition = new Position(StartingPositions.ROOK_2, StartingPositions.WHITE_NON_PAWN_Y);
            interveningCheckPosition = new Position(StartingPositions.ROOK_2 - 1, StartingPositions.WHITE_NON_PAWN_Y);
        } else if(!getColor() && direction) {
            // black castling to the left
            rookPosition = new Position(StartingPositions.ROOK_1, StartingPositions.BLACK_NON_PAWN_Y);
            interveningCheckPosition = new Position(StartingPositions.ROOK_1 + 1, StartingPositions.BLACK_NON_PAWN_Y);
        } else {
            // black castling to the right
            rookPosition = new Position(StartingPositions.ROOK_1, StartingPositions.BLACK_NON_PAWN_Y);
            interveningCheckPosition = new Position(StartingPositions.ROOK_1 - 1, StartingPositions.BLACK_NON_PAWN_Y);
        }

        // make sure the piece at the rooks position is in fact a rook and
        // has not moved
        if(!gameInfo.validateRookForCastle(rookPosition)) {
            return false;
        }

        // make sure there's nothing between the king and the rook
        if(gameInfo.isInterveningPeice(getPosition(), interveningCheckPosition)) {
            return false;
        }

        // must be a valid castle
        return true;
    }
}
