package demo.graphics;

import org.jetbrains.annotations.NotNull;

import commonGraphics.AbstractKeyListener;
import baryGraphics.Observer;

//
public final class DemoKeyListener extends AbstractKeyListener {
    private static final boolean
            SHIFT_ZOOMED = true;
    private static final double
            SHIFT_RATE = 7,
            ZOOM_RATE = 0.03;
    private final @NotNull Observer observer;

    //
    public DemoKeyListener(@NotNull Observer observer) {
        super();
        this.observer = observer;
    }

    //
    @Override
    public void keyActionSwitch_byCode(int keyCode) throws UndefinedKeyActionException {
        switch (keyCode) {
            case 16, 33 -> { // Shift, Page Up
                try {
                    observer.zoomIn(ZOOM_RATE); // zoom in
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            }
            case 17, 34 -> { // Ctrl, Page Down
                try {
                    observer.zoomOut(ZOOM_RATE); //zoom out
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            }
            // add more keys here
            default -> throw new UndefinedKeyActionException();
        }
    }

    //
    @Override
    public void keyActionSwitch_byText(@NotNull String keyText) throws UndefinedKeyActionException {
        switch (keyText) {
            case "A", "a", "Left" -> shiftObserver(-SHIFT_RATE, 0); // x-, shift left
            case "D", "d", "Right" -> shiftObserver(SHIFT_RATE, 0); // x+, shift right
            case "W", "w", "Up" -> shiftObserver(0, -SHIFT_RATE);   // y-, shift up
            case "S", "s", "Down" -> shiftObserver(0, SHIFT_RATE);  // y+, shift down
            // add more keys here
            default -> throw new UndefinedKeyActionException();
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