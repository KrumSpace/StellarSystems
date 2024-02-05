package commonGraphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.jetbrains.annotations.NotNull;

import static consoleUtils.SimplePrinting.printLine;

//
public abstract class AbstractKeyListener implements KeyListener {
    //
    public AbstractKeyListener() {}

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(@NotNull KeyEvent e) {}

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(@NotNull KeyEvent e) {
        try {
            keyActionSwitch_byChar(e);
        } catch (@NotNull UndefinedKeyActionException ignored) {
            try {
                keyActionSwitch_byCode(e.getKeyCode());
            } catch (@NotNull UndefinedKeyActionException exception) {
                printLine(exception.getMessage());
            }
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(@NotNull KeyEvent e) {}

    //
    public abstract void keyActionSwitch_byChar(@NotNull KeyEvent e) throws UndefinedKeyActionException;

    //
    public abstract void keyActionSwitch_byCode(int keyCode) throws UndefinedKeyActionException;

    //
    public static final class UndefinedKeyActionException extends Exception {
        //
        public UndefinedKeyActionException() {
            super();
        }

        //
        public UndefinedKeyActionException(int keyCode) {
            super("Key action not defined for key " + keyCode + " (" + KeyEvent.getKeyText(keyCode) + ")");
        }
    }
}