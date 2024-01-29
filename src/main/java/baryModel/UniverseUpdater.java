package baryModel;

import org.jetbrains.annotations.NotNull;

import ThreadAbstraction.AbstractUpdater;

import utils.TimeUtils;

//
public final class UniverseUpdater extends AbstractUpdater {
    private static final long DELAY = 100;
    private final @NotNull BaryUniverse universe;

    //
    public UniverseUpdater(@NotNull BaryUniverse universe) {
        super(DELAY);
        this.universe = universe;
        this.start();
    }

    //
    @Override
    public void update() {
        long timeInMillis = delayCalculator.getOptions().getPreferredMS();
        double timeInSeconds = TimeUtils.convertMillisToSeconds(timeInMillis);
        universe.completeCycle(timeInSeconds);
    }
}