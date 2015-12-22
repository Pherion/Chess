package chess.renderers.swingRenderer;

import chess.game.board.Position;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author CarrollFD
 */
public class PieceButton extends JButton {
    private final Position position;
    
    public PieceButton(String text, Color bgColor, Position position) {
        super(text);
        this.position = position;
        
        format(bgColor);
    }
    
    public Position getPosition() {
        return position;
    }

    private void format(Color bgColor) {
        this.setBackground(bgColor);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}
