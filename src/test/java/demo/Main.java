package demo;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryModel;
import baryModel.ModelUpdater;

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
}