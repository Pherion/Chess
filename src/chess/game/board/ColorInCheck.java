package chess.game.board;

/**
 * Represents the color in check.
 * We use an enum here (instead of boolean like the rest of the applications) because we need
 * 3-state logic, and the usage of a null Boolean is clunky and error prone.
 *
 * The Boolean is used for translation purposes.
 *
 * Created by fraca_000 on 12/14/2015.
 */
public enum ColorInCheck {
    black(false),
    white(true),
    none(null);

    private Boolean colorBoolean;

    ColorInCheck(Boolean color) {
        colorBoolean = color;
    }

    public Boolean toBoolean() {
        return colorBoolean;
    }
}
