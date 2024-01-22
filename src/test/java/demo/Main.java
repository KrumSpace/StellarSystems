package demo;

import org.jetbrains.annotations.NotNull;

import ThreadAbstraction.AbstractUpdater;

import baryModel.BaryModel;

import testGraphics.TestWindow;
import testGraphics.WindowUpdater;

//
public class Main {
    //
    public static void main(String[] args) {
        @NotNull BaryModel model = getNewBaryModel();
        new ModelUpdater(model);
        startGraphics(model);
    }

    private static @NotNull BaryModel getNewBaryModel() {
        return new TestModel();
    }

    private static void startGraphics(@NotNull BaryModel model) {
        new WindowUpdater(new TestWindow(model));
    }

    private static class ModelUpdater extends AbstractUpdater {
        private static final long DELAY = 30;
        private final @NotNull BaryModel model;

        private ModelUpdater(@NotNull BaryModel model) {
            super(DELAY);
            this.model = model;
            this.start();
        }

        @Override
        public void update() {
            double totalElapsedTimeInSeconds = (delayCalculator.getElapsedTime() + delayCalculator.getDelay()) / 1000.0;
            model.precalculate(totalElapsedTimeInSeconds);
            model.update();
        }
    }
}