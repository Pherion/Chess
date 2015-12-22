package chess.game;

import chess.pieces.Piece;
import chess.pieces.PieceType;
import chess.game.board.Board;
import chess.game.board.Position;
import chess.pieces.ImmutablePiece;

import java.util.List;

/**
 * Wrapper for the Game object to be provided to pieces so that they can access info about the game
 * while the game remains immutable to them.
 *
 * @author CarrollFD
 */
public class GameInfoWrapper {
    private final Game game;

    /**
     * Constructs a new wrapper
     *
     * @param game the game to wrap
     */
    public GameInfoWrapper(Game game) {
        this.game = game;
    }

    /**
     * Indicates if the position is threatened by the provided color
     *
     * @param position position to check
     * @param color color to check
     * @return TRUE if the given color threatens the given position
     */
    public boolean threatenedBy(Position position, boolean color) {
        return game.getBoard().threatenedBy(position, color);
    }

    /**
     * Gets the previous move from the game.
     *
     * @return most recent move
     */
    public Move getPreviousMove() {
        List<Move> moveList = game.getMoveList();

        if(moveList.size() > 0) {
            return moveList.get(moveList.size() - 1);
        }

        return null;
    }

    /**
     * Determines if there is a piece blocking the path between the two
     * positions.
     *
     * @param position1 starting position
     * @param position2 ending position
     *
     * @return TRUE if there an intervening piece.
     */
    public boolean isInterveningPeice(Position position1, Position position2) {
        return game.getBoard().isInterveningPiece(position1, position2);
    }

    /**
     * Confirms that the piece on the given position is a rook, and is
     * capable of castling.
     *
     * @param position The position to check.
     *
     * @return TRUE if there is a piece on the given position, it is a 
     *         rook, and is able to castle (has not yet moved).
     */
    public boolean validateRookForCastle(Position position) {
        Piece rook = game.getBoard().getPieceAt(position);

        // verrify the peice is a rook, and that it has not moved
        return (rook.getType() == PieceType.rook) && !rook.isMoved();
    }

    /**
     * Indicates if the position is captruable by the given color.
     * Returns true if there is a piece there of the opposing color,
     * or if there is no piece there.
     *
     * @param color The color of the piece attempting the capture.
     * @param position The position of the attempted capture.
     *
     * @return TRUE if the piece at the given position is capturable, or if 
     *         there is no piece there.
     */
    public boolean isCapturable(boolean color, Position position) {
        Piece piece = game.getBoard().getPieceAt(position);

        return piece == null || piece.getColor() != color;
    }

    /**
     * Determines if the provided position is on the game board.
     *
     * @param position the position to check
     *
     * @return TRUE if the position is on the board.
     */
    public static boolean isOnBoard(Position position) {
        return position.getX() >= 0 && position.getX() < Board.BOARD_SIZE_X &&
                position.getY() >= 0 && position.getY() < Board.BOARD_SIZE_Y;
    }

    /**
     * Creates a copy of the current game, and  forces a move so that the game 
     * can be tested for various outcomes.
     *
     * Assumptions: This move has already been validated to a point.
     *
     * @param startPosition The position of the piece to move
     * @param endPosition The end position of the piece
     *
     * @return A test Game object resulting from the forced move.
     */
    public Game forceMove(Position startPosition, Position endPosition) {
        // create a new game that is a copy of the current game
        Game newGame = new Game(game);

        // force the move on the new game
        newGame.forceMove(startPosition, endPosition);

        // return the new game
        return newGame;
    }
    
    /**
     * Provides a copy of the piece at the given position
     * 
     * @param position
     * @return 
     */
    public ImmutablePiece getPieceAt(Position position) {
        Piece pieceAtPosition = game.getBoard().getPieceAt(position);
        
        if(pieceAtPosition == null) {
            return null;
        }
        
        return new ImmutablePiece(pieceAtPosition);
    }
}
