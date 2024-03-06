package demo;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;
import baryModel.UniverseUpdater;

import commonGraphics.AbstractWindow;
import commonGraphics.WindowUpdater;
import demo.graphics.DemoWindow;

//
public class Demo {
    //
    public static void main(String[] args) {
        new GraphicalBaryTest(getNewUniverse());
    }

    private static @NotNull BaryUniverse getNewUniverse() {
        return new TestUniverse();
    }

    //TODO: finish this, add graceful close/exit
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private static class GraphicalBaryTest {
        private final @NotNull BaryUniverse universe;
        private final @NotNull UniverseUpdater universeUpdater;
        private final @NotNull AbstractWindow window;
        private final @NotNull WindowUpdater windowUpdater;

        //
        GraphicalBaryTest(@NotNull BaryUniverse universe) {
            this.universe = universe;
            universeUpdater = new UniverseUpdater(universe);
            window = new DemoWindow(universe);
            windowUpdater = new WindowUpdater(window);
        }
    }
}