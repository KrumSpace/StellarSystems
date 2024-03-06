package commonGraphics;

import java.awt.Point;
import java.awt.Dimension;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import delayCalculator.delayOptions.DelayType;
import delayCalculator.delayOptions.DelayOptions;

//TODO: add graceful exit
@SuppressWarnings("unused")
public abstract class UpdatingWindow extends AbstractWindow {
    private final @NotNull WindowUpdater windowUpdater;

    //with default frame rate
    public UpdatingWindow(@Nullable Dimension size, @Nullable Point location, @Nullable String title) {
        super(size, location, title);
        windowUpdater = new WindowUpdater(this);
    }

    //with custom frame rate
    public UpdatingWindow(@Nullable Dimension size, @Nullable Point location, @Nullable String title,
                          long frameRate) {
        super(size, location, title);
        windowUpdater = new WindowUpdater(this, frameRate);
    }

    //
    public void startUpdating() {
        windowUpdater.start();
    }

    //
    public long getPreferredFrameRate() {
        return getDelayOptions().getPreferredFPS();
    }

    //
    public void setFrameRate(long frameRate) {
        getDelayOptions().setPreferences(DelayType.FPS, frameRate);
    }

    private @NotNull DelayOptions getDelayOptions() {
        return windowUpdater.delayCalculator.getOptions();
    }
}