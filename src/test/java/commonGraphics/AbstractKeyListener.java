package commonGraphics;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.jetbrains.annotations.NotNull;

import static consoleUtils.SimplePrinting.printLine;
import ThreadAbstraction.AbstractUpdater;

//
public abstract class AbstractKeyListener implements KeyListener {
    private final @NotNull List<@NotNull Integer> pressedKeys;
    @SuppressWarnings("FieldCanBeLocal")
    private final @NotNull KeyActionUpdater keyActionUpdater;

    //
    public AbstractKeyListener() {
        pressedKeys = new ArrayList<>();
        keyActionUpdater = new KeyActionUpdater(this);
        keyActionUpdater.start();
    }

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
        addKey(e.getKeyCode());
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(@NotNull KeyEvent e) {
        removeKey(e.getKeyCode());
    }

    //
    final @NotNull List<@NotNull Integer> getKeys() {
        return Collections.unmodifiableList(pressedKeys);
    }

    //
    @SuppressWarnings("unused")
    final void clearKeys() {
        pressedKeys.clear();
    }

    private void addKey(int keyCode) {
        if (!pressedKeys.contains(keyCode)) {
            pressedKeys.add(keyCode);
        }
    }

    private void removeKey(int keyCode) {
        if (pressedKeys.contains(keyCode)) {
            pressedKeys.remove((Integer) keyCode);
        }
    }

    //
    public abstract void keyActionSwitch_byCode(int keyCode) throws UndefinedKeyActionException;

    //
    public abstract void keyActionSwitch_byText(@NotNull String keyText) throws UndefinedKeyActionException;

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

    //
    private static final class KeyActionUpdater extends AbstractUpdater {
        private static final long UPDATE_DELAY = 20; // ms
        private final @NotNull AbstractKeyListener listener;

        //
        KeyActionUpdater(@NotNull AbstractKeyListener listener) {
            super(UPDATE_DELAY);
            this.listener = listener;
        }

        @Override
        public void update() {
            @NotNull List<@NotNull Integer> keys = listener.getKeys();
            for (int keyCode : keys) {
                try {
                    listener.keyActionSwitch_byCode(keyCode);
                } catch (@NotNull UndefinedKeyActionException ignored) {
                    try {
                        listener.keyActionSwitch_byText(KeyEvent.getKeyText(keyCode));
                    } catch (@NotNull UndefinedKeyActionException exception) {
                        @NotNull String exceptionMessage = (new UndefinedKeyActionException(keyCode)).getMessage();
                        printLine(exceptionMessage);
                    }
                }
            }
        }
    }
}