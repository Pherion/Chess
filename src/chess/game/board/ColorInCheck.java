package chess.game.board;

/**
 * Represents the color in check.
 * We use an enum here (instead of boolean like the rest of the applications) because we need
 * 3-state logic, and the usage of a null Boolean is clunky and error prone.
 * Created by fraca_000 on 12/14/2015.
 */
public enum ColorInCheck {
    black,
    white,
    none;
}
