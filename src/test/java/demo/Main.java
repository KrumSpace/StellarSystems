package demo;

import baryModel.BaryUniverse;
import org.jetbrains.annotations.NotNull;

import baryModel.UniverseUpdater;

import testGraphics.TestWindow;
import testGraphics.WindowUpdater;

//
public class Main {
    //
    public static void main(String[] args) {
        @NotNull BaryUniverse universe = getNewUniverse();
        new UniverseUpdater(universe);
        startGraphics(universe);
    }

    private static @NotNull BaryUniverse getNewUniverse() {
        //return new TestModel();
        return new TestUniverse2();
    }

    private static void startGraphics(@NotNull BaryUniverse universe) {
        new WindowUpdater(new TestWindow(universe));
    }
}