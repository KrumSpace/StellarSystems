package baryModel;

import org.jetbrains.annotations.NotNull;

import ThreadAbstraction.AbstractUpdater;

import utils.TimeUtils;

//
public final class ModelUpdater extends AbstractUpdater {
    private static final long DELAY = 30;
    private final @NotNull BaryModel model;

    //
    public ModelUpdater(@NotNull BaryModel model) {
        super(DELAY);
        this.model = model;
        this.start();
    }

    //
    @Override
    public void update() {
        long timeInMillis = delayCalculator.getOptions().getPreferredMS();
        double timeInSeconds = TimeUtils.convertMillisToSeconds(timeInMillis);

        model.precalculate(timeInSeconds);
        //needs to get normalized here
        model.update();
    }
}