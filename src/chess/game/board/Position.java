package chess.game.board;

/**
 * Represents the position of a chess piece on the board.
 *
 * @author CarrollFD
 */
public class Position {
    // the stored position on the chess board
    private int x;
    private int y;

    /**
     * Creates a new position at coordinates x, y
     * 
     * @param x
     * @param y 
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor
     * 
     * @param position object to copy.
     */
    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }
    
    /**
     * @return x position
     */
    public int getX() {
        return x;
    }

    /**
     * @return y position
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Position) {
            Position toCompre = (Position)o;

            return x == toCompre.x && y == toCompre.y;
        }

        return false;
    }
}
