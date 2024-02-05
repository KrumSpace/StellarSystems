package demo.graphics;

import java.awt.event.KeyEvent;

import org.jetbrains.annotations.NotNull;

import commonGraphics.AbstractKeyListener;
import baryGraphics.Observer;

//
public final class DemoKeyListener extends AbstractKeyListener {
    private static final boolean
            SHIFT_ZOOMED = true;
    private static final double
            SHIFT_RATE = 10,
            ZOOM_RATE = 0.05;
    private final @NotNull Observer observer;

    //
    public DemoKeyListener(@NotNull Observer observer) {
        super();
        this.observer = observer;
    }

    //
    @Override
    public void keyActionSwitch_byChar(@NotNull KeyEvent e) throws UndefinedKeyActionException {
        switch (e.getKeyChar()) {
            case 'a', 'A' -> shiftObserver(-SHIFT_RATE, 0); // x-, shift left
            case 'd', 'D' -> shiftObserver(SHIFT_RATE, 0);  // x+, shift right
            case 'w', 'W' -> shiftObserver(0, -SHIFT_RATE); // y-, shift up
            case 's', 'S' -> shiftObserver(0, SHIFT_RATE);  // y+, shift down
            // add more keys here
            default -> throw new UndefinedKeyActionException();
        }
    }

    //
    @Override
    public void keyActionSwitch_byCode(int keyCode) throws UndefinedKeyActionException {
        switch (keyCode) {
            case 16 -> { // Shift
                try {
                    observer.zoomIn(ZOOM_RATE); // zoom in
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            }
            case 17 -> { // Ctrl
                try {
                    observer.zoomOut(ZOOM_RATE); //zoom out
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            }
            // add more keys here
            default -> throw new UndefinedKeyActionException(keyCode);
        }
    }

    private void shiftObserver(double dx, double dy) {
        if (SHIFT_ZOOMED) {
            double scale = observer.getScale();
            observer.shiftLocation(dx * scale, dy * scale);
        } else {
            observer.shiftLocation(dx, dy);
        }
    }
}