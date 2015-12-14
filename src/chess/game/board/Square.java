package chess.game.board;

import chess.game.GameInfoWrapper;
import chess.pieces.*;

/**
 * Represents a square on the chess board
 *
 * Created by fraca_000 on 12/13/2015.
 */
public class Square {
    // the piece on the square
    private Piece pieceOnSquare;

    // flags indicating if the square is protected by each color
    private boolean protectedByWhite;
    private boolean protectedByBlack;

    /**
     * Constructs a default square, protected by no one, and without a piece on it
     */
    public Square() {
        protectedByBlack = false;
        protectedByWhite = false;
        pieceOnSquare = null;
    }

    /**
     * Constructs square with the provided piece on it, and protected by no one.
     *
     * @param piece The piece on this square
     */
    public Square(Piece piece) {
        pieceOnSquare = piece;
    }

    /**
     * Performs a deep copy of Square
     *
     * @param toCopy Square to copy
     * @param wrapper new game info wrapper
     */
    public Square(Square toCopy, GameInfoWrapper wrapper) {
        this.protectedByWhite = toCopy.protectedByWhite;
        this.protectedByBlack = toCopy.protectedByBlack;

        switch(toCopy.getPieceOnSquare().getType()) {
            case pawn:
                pieceOnSquare = new Pawn((Pawn)toCopy.getPieceOnSquare(), wrapper);
                break;
            case rook:
                pieceOnSquare = new Rook((Rook)toCopy.getPieceOnSquare(), wrapper);
                break;
            case knight:
                pieceOnSquare = new Knight((Knight)toCopy.getPieceOnSquare(), wrapper);
                break;
            case bishop:
                pieceOnSquare = new Bishop((Bishop)toCopy.getPieceOnSquare(), wrapper);
                break;
            case king:
                pieceOnSquare = new King((King)toCopy.getPieceOnSquare(), wrapper);
                break;
            case queen:
                pieceOnSquare = new Queen((Queen)toCopy.getPieceOnSquare(), wrapper);
                break;
        }
    }

    /**
     * @return The piece on the square
     */
    public Piece getPieceOnSquare() {
        return pieceOnSquare;
    }

    /**
     * @param pieceOnSquare Sets the piece on the square
     */
    public void setPieceOnSquare(Piece pieceOnSquare) {
        this.pieceOnSquare = pieceOnSquare;
    }

    /**
     * @return Sets the space as protected by white
     */
    public boolean isProtectedByWhite() {
        return protectedByWhite;
    }

    /**
     * @param protectedByWhite Indicates if the space is protected by white
     */
    public void setProtectedByWhite(boolean protectedByWhite) {
        this.protectedByWhite = protectedByWhite;
    }

    /**
     * @return Sets the space as protected by black
     */
    public boolean isProtectedByBlack() {
        return protectedByBlack;
    }

    /**
     * @param protectedByBlack Indicates if the space is protected by black
     */
    public void setProtectedByBlack(boolean protectedByBlack) {
        this.protectedByBlack = protectedByBlack;
    }
}
