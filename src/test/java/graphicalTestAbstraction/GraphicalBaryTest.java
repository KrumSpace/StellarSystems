package graphicalTestAbstraction;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;
import baryModel.UniverseUpdater;

import commonGraphics.AbstractWindow;

//TODO: finish this, add graceful close/exit
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class GraphicalBaryTest {
    private final @NotNull BaryUniverse universe;
    private final @NotNull UniverseUpdater universeUpdater;
    private final @NotNull AbstractWindow window;

    //
    public GraphicalBaryTest(@NotNull BaryUniverse universe,
                             @NotNull AbstractWindow window) {
        this.universe = universe;
        universeUpdater = new UniverseUpdater(universe);
        this.window = window;
    }

    //
    public final @NotNull BaryUniverse getUniverse() {
        return universe;
    }

    //
    public final @NotNull UniverseUpdater getUniverseUpdater() {
        return universeUpdater;
    }

    //
    public final @NotNull AbstractWindow getWindow() {
        return window;
    }
}