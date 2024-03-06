package commonGraphics;

import org.jetbrains.annotations.NotNull;

import delayCalculator.delayOptions.DelayType;
import delayCalculator.delayOptions.DelayOptions;
import ThreadAbstraction.AbstractUpdater;

//
public class WindowUpdater extends AbstractUpdater {
    private static final long DEFAULT_FRAME_RATE = 60;
    private final @NotNull AbstractWindow window;

    //with custom frame rate
    public WindowUpdater(@NotNull AbstractWindow window, long frameRate) {
        super(new DelayOptions(DelayType.FPS, frameRate));
        this.window = window;
    }

    //with default frame rate
    public WindowUpdater(@NotNull AbstractWindow window) {
        this(window, DEFAULT_FRAME_RATE);
    }

    //
    @Override
    public void update() {
        window.repaint();
    }
}