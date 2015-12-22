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
import chess.game.GameInfoWrapper;
import chess.game.board.Position;
import chess.renderers.MoveRequestAction;
import chess.renderers.swingRenderer.GameBoardPanel;
import chess.renderers.StandardOutRenderer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

        JFrame frame = new JFrame("Test Game");
        GameBoardPanel gamePanel = new GameBoardPanel(new GameInfoWrapper(game));
        
        gamePanel.setMoveRequestAction(new MoveRequestAction() {
            @Override
            public void moveRequest(Position startPosition, Position endPosition) {
                game.getBoard().requestMove(startPosition, endPosition);
            }
        });
        
        gamePanel.render();
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
