package chess.renderers;

import chess.game.board.Board;
import chess.game.board.Position;
import chess.pieces.Piece;
import chess.renderers.renderStyles.RendererStyle;

import java.util.List;

/**
 * Board Style:
 * |---------------------------------------|
 * | BR | BN | BB | BK | BQ | BB | BN | BR |
 * |---------------------------------------|
 * | B  | B  | B  | B  | B  | B  | B  | B  |
 * |---------------------------------------|
 * |    |    |    |    |    |    |    |    |
 * |---------------------------------------|
 * |    |    |    |    |    |    |    |    |
 * |---------------------------------------|
 * |    |    |    |    |    |    |    |    |
 * |---------------------------------------|
 * |    |    |    |    |    |    |    |    |
 * |---------------------------------------|
 * | W  | W  | W  | W  | W  | W  | W  | W  |
 * |---------------------------------------|
 * | WR | WN | WB | WQ | WK | WB | WN | WR |
 * |---------------------------------------|
 *
 * Created by fraca_000 on 12/12/2015.
 */
public class StandardOutRenderer implements BoardRenderer {
    public static final String ROOK_TEXT = "R";
    public static final String KNIGHT_TEXT = "N";
    public static final String BISHOP_TEXT = "B";
    public static final String KING_TEXT = "K";
    public static final String QUEEN_TEXT = "Q";
    public static final String PAWN_TEXT = " ";

    private RendererStyle style;
    private Board board;

    public StandardOutRenderer(Board board) {
        this.board = board;
    }

    @Override
    public void render() {
        if(board == null) {
            throw new IllegalStateException("Renderer.render() invoked for null board.");
        }



        // Loop over the rows
        for(int y = 0; y < Board.BOARD_SIZE_Y; y++) {
            System.out.println("|---------------------------------------|");
            System.out.print("| ");

            // Loop over the columns
            for(int x = 0; x < Board.BOARD_SIZE_X; x++) {
                // obtain he piece at the current position
                Piece piece = board.getPieceAt(new Position(x,y));

                // if there's no piece, print 2 spaces
                if(piece == null) {
                    System.out.print("  ");
                } else {
                    // otherwise print the piece
                    // print color
                    if(piece.getColor()) {
                        System.out.print("W");
                    } else {
                        System.out.print("B");
                    }

                    // print piece
                    switch(piece.getType()) {
                        case pawn:
                            System.out.print(PAWN_TEXT);
                            break;
                        case rook:
                            System.out.print(ROOK_TEXT);
                            break;
                        case knight:
                            System.out.print(KNIGHT_TEXT);
                            break;
                        case bishop:
                            System.out.print(BISHOP_TEXT);
                            break;
                        case queen:
                            System.out.print(QUEEN_TEXT);
                            break;
                        case king:
                            System.out.print(KING_TEXT);
                            break;
                    }
                }



                // if its the last square in the row, print the wall, and end the line
                if(x == 7) {
                    System.out.println(" |");
                } else {
                    // print the space after the piece
                    System.out.print(" | ");
                }
            }

            // if its the last row, print the bottom of the board
            if(y == 7) {
                System.out.println("|---------------------------------------|");
            }
        }

    }

    @Override
    public void setRendererStyle(RendererStyle style) {
        this.style = style;
    }

    @Override
    public void setMoveRequestAction(MoveRequestAction action) {
        // no move request action is provided for this implementation
    }
}
