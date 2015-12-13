/**
 * A chess game
 * 
 * Assumptions:
 * TRUE = white
 * FALSE = black
 * 
 * Board notation is as follows:
 * Rows are inversely numbered form standard notation for simplification in
 * programming
 * 
 * 0 R  Kn B  K  Q  B  Kn R
 * 1 P  P  P  P  P  P  P  P
 * 2
 * 3
 * 4
 * 5
 * 6 P  P  P  P  P  P  P  P
 * 7 R  Kn B  Q  K  B  Kn R
 *   0  1  2  3  4  5  6  7
 * 
 **/
package chess;

import chess.game.Game;
import chess.game.board.Position;
import chess.renderers.StandardOutRenderer;

/**
 *
 * @author CarrollFD
 */
public class Chess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();

        StandardOutRenderer renderer = new StandardOutRenderer(game.getBoard());

        System.out.println(game.getBoard().requestMove(new Position(1, 7), new Position(0, 5)));
        System.out.println(game.getBoard().getMoveError());

        renderer.render();
    }
    
}
