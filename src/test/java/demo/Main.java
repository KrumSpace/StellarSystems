package demo;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;
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
        //return new TestUniverse1();
        //return new TestUniverse2();
        return new TestUniverse3();
    }

    private static void startGraphics(@NotNull BaryUniverse universe) {
        new WindowUpdater(new TestWindow(universe));
    }
}