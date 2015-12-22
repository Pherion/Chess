package chess.renderers;

import chess.game.board.Position;

/**
 * Defines the actions to be taken when a move request is made.  For graphical
 * displays, the GUI will receive the request for a move, and that will need
 * to be forwarded back to the controlling game elements.  The controller is
 * responsible for implementing this interface, and providing it to the
 * renderer.
 * 
 * @author CarrollFD
 */
public interface MoveRequestAction {
    /**
     * Informs the controller that a move request has been made.
     * 
     * @param startPosition The starting position of the move
     * @param endPosition The ending position of the move
     */
    public void moveRequest(Position startPosition, Position endPosition);
}
