package testGraphics;

import org.jetbrains.annotations.NotNull;

import delayCalculator.delayOptions.DelayOptions;
import delayCalculator.delayOptions.DelayType;

import ThreadAbstraction.AbstractUpdater;

//
public class WindowUpdater extends AbstractUpdater {
    private static final int DEFAULT_FRAME_RATE = 60;
    private final @NotNull TestWindow window;

    //with custom frame rate
    public WindowUpdater(@NotNull TestWindow window, int frameRate) {
        super(new DelayOptions(DelayType.FPS, frameRate));
        this.window = window;
        this.start();
    }

    //with default frame rate
    public WindowUpdater(@NotNull TestWindow window) {
        this(window, DEFAULT_FRAME_RATE);
    }

    //
    @Override
    public void update() {
        window.repaint();
    }
}